package in.dsingh.domaindata.domaindetails.cron;

import in.dsingh.domaindata.domaindetails.data.dbhelpers.DomainEntityDbHelper;
import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.DomainEntityRepository;
import in.dsingh.domaindata.domaindetails.data.repositories.EmailEntityRepository;
import in.dsingh.domaindata.domaindetails.service.DomainHealthChecker;
import in.dsingh.domaindata.domaindetails.service.WebPageParser;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class DomainParseCron {

  @Autowired
  private DomainEntityDbHelper domainEntityDbHelper;

  @Autowired
  private DomainHealthChecker domainHealthChecker;

  @Autowired
  private WebPageParser webPageParser;

  @Autowired
  private DomainEntityRepository domainEntityRepository;

  @Autowired
  private EmailEntityRepository emailEntityRepository;

  private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(40);

  @Scheduled(fixedDelayString = "30000")
  public void fetchRecordAndParse() {

    log.info("Starting new cron job");

    Integer activeThreads = DomainParseTask.getThreadCount();

    if(activeThreads > 10) {
      log.info("Exiting cron job because no threads available. Current active threads {} ", activeThreads);
      return;
    }

    List<DomainEntity> domainEntityList = domainEntityDbHelper.getRecordsToParse();

    if (!CollectionUtils.isEmpty(domainEntityList)) {
      for(DomainEntity domain : domainEntityList) {
        domainEntityDbHelper.incRetryCount(domain.getDomainName());
        DomainParseTask task = new DomainParseTask(domain.getDomainName(), domainHealthChecker,
            domainEntityDbHelper, webPageParser, domainEntityRepository, emailEntityRepository);
        executor.execute(task);
      }
    } else {
      log.info("No record to fetch email form. DONE for now! Please feed more data");
    }

  }

}
