//package in.dsingh.domaindata.domaindetails.cron;
//
//import in.dsingh.domaindata.domaindetails.cron.response.DnpediaResponse;
//import in.dsingh.domaindata.domaindetails.data.dbhelpers.DnpediaCronDbHelper;
//import in.dsingh.domaindata.domaindetails.data.dbhelpers.DomainEntityDbHelper;
//import in.dsingh.domaindata.domaindetails.data.entities.DnpediaCronEntity;
//import java.text.SimpleDateFormat;
//import java.util.Optional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//@Component
//@Slf4j
//public class DomainListFetchCron {
//
//  @Autowired
//  private DnpediaCronDbHelper dnpediaCronDbHelper;
//
//  @Autowired
//  private DomainListFetchHelper domainListFetchHelper;
//
//  @Autowired
//  private DomainEntityDbHelper domainEntityDbHelper;
//
////  @Scheduled(fixedDelayString = "${domain.list.fetch.cron.fixed.delay}")
//  public void fetchDomainList() {
//
//    Optional<DnpediaCronEntity> entry = dnpediaCronDbHelper.pickNextRecord();
//
//    if (!entry.isPresent()) {
//      log.info("Got no record to run in the domain list fetch cron");
//      return;
//    }
//
//    DnpediaCronEntity record = entry.get();
//
//    if (record.getDomainRegDate() == null || record.getLastPageFetched() == null
//        || record.getTotalPages() == null) {
//      log.error("Null values in cron record {} for record id {}", record, record.getId());
//      return;
//    }
//
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    String pageToFetch = String.valueOf(record.getLastPageFetched() + 1);
//    String date = format.format(record.getDomainRegDate());
//    String zone = record.getZone();
//
//    DnpediaResponse response = null;
//    try {
//      log.info("Fetching domain list for page {} date {} and zone {}", pageToFetch, date, zone);
//      response = domainListFetchHelper.fetchDomainList(pageToFetch, date, zone);
//    } catch (Exception e) {
//      log.error("Error occured while fetching response from dnpedia {}", e);
//    }
//
//    if (response == null || (response != null && response.getTotal().equalsIgnoreCase("0")
//        && response.getPage().equalsIgnoreCase("0") && CollectionUtils
//        .isEmpty(response.getRows()))) {
//      log.error("Invalid response from dnpedia for page {} date {} and zone {}", pageToFetch, date,
//          zone);
//      return;
//    }
//
//    domainEntityDbHelper.insertDomainRecords(response, record);
//    dnpediaCronDbHelper.updateCronStatus(response, record);
//    dnpediaCronDbHelper.markUnpicked(record);
//  }
//}
