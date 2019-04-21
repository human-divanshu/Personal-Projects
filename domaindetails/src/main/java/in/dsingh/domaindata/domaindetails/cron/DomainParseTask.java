package in.dsingh.domaindata.domaindetails.cron;

import in.dsingh.domaindata.domaindetails.data.dbhelpers.DomainEntityDbHelper;
import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import in.dsingh.domaindata.domaindetails.data.entities.EmailEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.DomainEntityRepository;
import in.dsingh.domaindata.domaindetails.data.repositories.EmailEntityRepository;
import in.dsingh.domaindata.domaindetails.service.DomainHealthChecker;
import in.dsingh.domaindata.domaindetails.service.WebPageParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
public class DomainParseTask implements Runnable {

  private String domainName;

  private DomainHealthChecker domainHealthChecker;

  private DomainEntityRepository domainEntityRepository;

  private WebPageParser webPageParser;

  private DomainEntityDbHelper domainEntityDbHelper;

  private EmailEntityRepository emailEntityRepository;

  private static AtomicInteger threadCount = new AtomicInteger(0);

  public DomainParseTask(String domainName, DomainHealthChecker domainHealthChecker,
      DomainEntityDbHelper domainEntityDbHelper,
      WebPageParser webPageParser,
      DomainEntityRepository domainEntityRepository,
      EmailEntityRepository emailEntityRepository) {
    this.domainName = domainName;
    this.domainHealthChecker = domainHealthChecker;
    this.domainEntityDbHelper = domainEntityDbHelper;
    this.webPageParser = webPageParser;
    this.domainEntityRepository = domainEntityRepository;
    this.emailEntityRepository = emailEntityRepository;
  }

  @Override
  public void run() {
    threadCount.incrementAndGet();

    log.info("I am running for domain name {}", domainName);

    DomainHealth response = domainHealthChecker.isDomainAlive(domainName);

    if(response == null) {
      log.error("Got null response for domain health for domain name {}", domainName);
      threadCount.decrementAndGet();
      return;
    }

    if(!response.isCheckedSuccessfully()) {
      domainEntityDbHelper.incRetryCount(domainName);
      threadCount.decrementAndGet();
      return;
    }

    if(!response.isAlive()) {
      domainEntityDbHelper.markParsedVisited(domainName);
      threadCount.decrementAndGet();
      return;
    }

    doParse(response);
    threadCount.decrementAndGet();
  }

  private void doParse(DomainHealth domainHealth) {

    try {
      Set<String> urlList = webPageParser.getUrlList(domainHealth);
      Set<String> emails = webPageParser.getEmails(urlList);
      if (!CollectionUtils.isEmpty(emails)) {
        DomainEntity domainEntity = domainEntityRepository
            .findFirstByDomainNameIs(domainHealth.getDomainName());
        List<EmailEntity> emailEntityList = new ArrayList<>();
        for (String email : emails) {
          EmailEntity emailEntity = new EmailEntity(email, domainEntity);
          emailEntityList.add(emailEntity);
        }
        emailEntityRepository.save(emailEntityList);
        domainEntityDbHelper.markParsedVisited(domainHealth.getDomainName());
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      log.error("Exception occured while parsing domain name {}", domainHealth);
      domainEntityDbHelper.incRetryCount(domainHealth.getDomainName());
    }
  }

  public static Integer getThreadCount() {
    return threadCount.get();
  }
}
