package in.dsingh.domaindata.domaindetails.controllers;

import in.dsingh.domaindata.domaindetails.controllers.request.DomainListRequest;
import in.dsingh.domaindata.domaindetails.controllers.request.SendEmailRequest;
import in.dsingh.domaindata.domaindetails.controllers.response.BaseResponse;
import in.dsingh.domaindata.domaindetails.cron.SendEmailCron;
import in.dsingh.domaindata.domaindetails.service.DomainService;
import in.dsingh.domaindata.domaindetails.service.EmailsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainController {

  private static Logger LOGGER = LoggerFactory.getLogger(DomainController.class);

  @Autowired
  private DomainService domainService;

  @Autowired
  private EmailsService emailsService;

  @Autowired
  private SendEmailCron sendEmailCron;

  @PutMapping("/domain/add")
  public BaseResponse addDomains(@RequestBody DomainListRequest data) {
    return domainService.addDomainList(data);
  }

  @GetMapping("/email/good/count")
  @ApiOperation(value = "Get count of emails that are from google, yahoo or hotmail")
  public String getEmailCount() {
    return emailsService.getEmailCount();
  }

  @PostMapping("/email/test/send")
  public Boolean sendTestEmail(@RequestBody SendEmailRequest sendEmailRequest) {
    return emailsService.sendEmail(sendEmailRequest);
  }

  @GetMapping("/email/sending/status")
  public Boolean getEmailSendingStatus() {
    return sendEmailCron.getEmailSendingStatus();
  }
}