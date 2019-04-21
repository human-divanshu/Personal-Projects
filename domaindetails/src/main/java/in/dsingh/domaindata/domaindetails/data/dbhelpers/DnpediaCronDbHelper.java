package in.dsingh.domaindata.domaindetails.data.dbhelpers;

import in.dsingh.domaindata.domaindetails.cron.response.DnpediaResponse;
import in.dsingh.domaindata.domaindetails.data.entities.DnpediaCronEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.DnpediaCronRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DnpediaCronDbHelper {

  private Logger LOGGER = LoggerFactory.getLogger(DnpediaCronEntity.class);

  @Autowired
  private DnpediaCronRepository dnpediaCronRepository;

//  @Transactional
//  public Optional<DnpediaCronEntity> pickNextRecord() {
//    try {
//      DnpediaCronEntity record = dnpediaCronRepository
//          .findFirstByDoneIsFalseAndPickedIsFalseOrderByUpdatedAtAsc();
//      if(record != null) {
//        record.setPicked(true);
//        dnpediaCronRepository.save(record);
//        return Optional.of(record);
//      }
//    } catch (Exception e) {
//      LOGGER.error("Could not fetch record from db {}", e);
//    }
//    return Optional.empty();
//  }

//  public void addNewRecordForEveryZone(Date date) {
//    List<DnpediaCronEntity> dnpediaCronEntityList = new ArrayList<>();
//    for(String zone : ZoneListConfig.getZoneList()) {
//      DnpediaCronEntity entity = new DnpediaCronEntity(date, zone);
//      dnpediaCronEntityList.add(entity);
//    }
//    dnpediaCronRepository.save(dnpediaCronEntityList);
//  }

//  public void updateCronStatus(DnpediaResponse response,
//      DnpediaCronEntity record) {
//    DnpediaCronEntity cron = dnpediaCronRepository.findOne(record.getId());
//
//    if(cron.getTotalPages() == 0) {
//      cron.setTotalPages(Long.valueOf(response.getTotal()));
//    }
//    cron.setPicked(false);
//    cron.setLastPageFetched(Long.valueOf(response.getPage()));
//
//    if(lastPageFetched(response)) {
//      cron.setDone(true);
//    }
//
//    dnpediaCronRepository.save(cron);
//  }

  private boolean lastPageFetched(DnpediaResponse response) {
    if(response.getPage().equalsIgnoreCase(response.getTotal())) {
      return true;
    }
    return false;
  }

//  public void markUnpicked(DnpediaCronEntity record) {
//    DnpediaCronEntity entity = dnpediaCronRepository.findOne(record.getId());
//    entity.setPicked(false);
//    dnpediaCronRepository.save(entity);
//  }

  public DnpediaCronEntity addNewDate(String inputDate) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = format.parse(inputDate);
      DnpediaCronEntity dnpediaCronEntity = new DnpediaCronEntity(date);
      return dnpediaCronRepository.save(dnpediaCronEntity);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
