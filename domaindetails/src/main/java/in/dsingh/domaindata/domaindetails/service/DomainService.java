package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.controllers.request.DomainListRequest;
import in.dsingh.domaindata.domaindetails.controllers.response.BaseResponse;
import in.dsingh.domaindata.domaindetails.controllers.response.SuccessResponse;
import in.dsingh.domaindata.domaindetails.data.dbhelpers.DnpediaCronDbHelper;
import in.dsingh.domaindata.domaindetails.data.dbhelpers.DomainEntityDbHelper;
import in.dsingh.domaindata.domaindetails.data.entities.DnpediaCronEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

  private static Logger LOGGER = LoggerFactory.getLogger(DomainService.class);

  @Autowired
  private DnpediaCronDbHelper dnpediaCronDbHelper;

  @Autowired
  private DomainEntityDbHelper domainEntityDbHelper;

//  public BaseResponse addDateToDomainListFetchCron(String date) {
//    SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
//    try {
//      Date newDate = format.parse(date);
//      dnpediaCronDbHelper.addNewRecordForEveryZone(newDate);
//    } catch (ParseException e) {
//      return new FailureResponse(ErrorMapping.INVALID_DATE_FORMAT);
//    }
//    return new SuccessResponse();
//  }

  public BaseResponse addDomainList(DomainListRequest data) {
    DnpediaCronEntity dnpediaCronEntity = dnpediaCronDbHelper.addNewDate(data.getDate());
    domainEntityDbHelper.insertDomainNames(data, dnpediaCronEntity);
    return new SuccessResponse();
  }

}
