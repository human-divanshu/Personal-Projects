package in.dsingh.domaindata.domaindetails.data.dbhelpers;

import com.google.common.base.CharMatcher;
import in.dsingh.domaindata.domaindetails.controllers.request.DomainListRequest;
import in.dsingh.domaindata.domaindetails.controllers.response.BaseResponse;
import in.dsingh.domaindata.domaindetails.controllers.response.SuccessResponse;
import in.dsingh.domaindata.domaindetails.cron.response.DnpediaEntry;
import in.dsingh.domaindata.domaindetails.cron.response.DnpediaResponse;
import in.dsingh.domaindata.domaindetails.data.entities.DnpediaCronEntity;
import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.DnpediaCronRepository;
import in.dsingh.domaindata.domaindetails.data.repositories.DomainEntityRepository;
import in.dsingh.domaindata.domaindetails.service.DictCheck;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class DomainEntityDbHelper {

  @Autowired
  private DomainEntityRepository domainEntityRepository;

  @Autowired
  private DnpediaCronRepository dnpediaCronRepository;

  @Autowired
  private DictCheck dictCheck;

  @Transactional
  public void insertDomainRecords(DnpediaResponse dnpediaResponse,
      DnpediaCronEntity record) {

    List<DnpediaEntry> domainList = dnpediaResponse.getRows();

    if(!CollectionUtils.isEmpty(domainList)) {
      log.info("Inserting domain name in database");
      for(DnpediaEntry r : domainList) {
        try {
          String subDomain = r.getName().substring(0, r.getName().indexOf('.')).toLowerCase();
          Optional<String> verificationSubstring = domainContainsDictWord(subDomain);
          if(CharMatcher.inRange('a', 'z').matchesAllOf(subDomain) &&
              verificationSubstring.isPresent()) {
            DomainEntity entity = new DomainEntity(r.getName(), record, verificationSubstring.get(),
                Long.valueOf(verificationSubstring.get().length()));
            domainEntityRepository.save(entity);
          }
        } catch (Exception e) {
          log.error("Error in domain record {}", r);
        }
      }
    }
  }

  public Optional<String> domainContainsDictWord(String domainName) {
    return dictCheck.hasWord(domainName);
  }

  public void markParsedVisited(String domainName) {
    DomainEntity domainEntity1 = domainEntityRepository.findFirstByDomainNameIs(domainName);
    domainEntity1.setVisited(true);
    domainEntity1.setParsed(true);
    domainEntityRepository.save(domainEntity1);
  }

  public List<DomainEntity> getRecordsToParse() {
    return domainEntityRepository.findRecordToParse();
  }

  public void incRetryCount(String domainName) {
    DomainEntity domainEntity = domainEntityRepository.findFirstByDomainNameIs(domainName);
    domainEntity.setRetryCount(domainEntity.getRetryCount() + 1);
    domainEntityRepository.save(domainEntity);
  }

  public BaseResponse insertDomainNames(DomainListRequest data,
      DnpediaCronEntity dnpediaCronEntity) {
    log.info("Got data to insert");
    List<DomainEntity> domainEntityList = new ArrayList<>();
    for(String domain : data.getDomainList()) {
      Optional<String> subStr = dictCheck.hasWord(domain);
      String verificationStr = subStr.isPresent() ? subStr.get() : "";
      DomainEntity domainEntity = new DomainEntity(domain, dnpediaCronEntity, verificationStr,
          Long.valueOf(verificationStr.length()));
//      domainEntityList.add(domainEntity);
      createDomainNameEntry(domainEntity, dnpediaCronEntity);
    }
//    domainEntityRepository.save(domainEntityList);
    return new SuccessResponse();
  }

  private void createDomainNameEntry(DomainEntity domainEntity, DnpediaCronEntity dnpediaCronEntity) {
    try {
      DnpediaCronEntity cronEntity = dnpediaCronRepository.findOne(dnpediaCronEntity.getId());
      domainEntity.setDnpediaCronEntity(cronEntity);
      domainEntityRepository.save(domainEntity);
    } catch (Exception e) {
      log.error("Error while creating domain name entry {}", e);
    }
  }

  public List<DomainEntity> getDomainToSendEmail() {
    return domainEntityRepository.getDomainToSendEmail();
  }

  @Transactional
  public void markRecordUploaded(DomainEntity entity, Boolean error, String fromEmailId) {
    DomainEntity domainEntity = domainEntityRepository.findOne(entity.getId());
    domainEntity.setUploaded(true);
    domainEntity.setErrorSendingEmail(error);
    domainEntity.setFromEmailId(fromEmailId);
    domainEntityRepository.save(domainEntity);
  }
}
