package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.controllers.request.SendEmailRequest;
import in.dsingh.domaindata.domaindetails.data.entities.EmailEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.EmailEntityRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmailsService {

  @Autowired
  private EmailEntityRepository emailEntityRepository;

  private RestTemplate restTemplate;

  private final String sendEmailUrl = "http://www.websitechamp.tech/emailer/sender.php";

  @PostConstruct
  public void initService() {
    restTemplate = new RestTemplate();
  }

  public String getEmailCount() {
    List<EmailEntity> emailEntityList = emailEntityRepository.getGoodEmails();
    if(!CollectionUtils.isEmpty(emailEntityList)) {
      return String.valueOf(emailEntityList.size());
    }
    return "0";
  }

  public Boolean sendEmail(SendEmailRequest sendEmailRequest) {
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate
          .postForEntity(sendEmailUrl, sendEmailRequest, String.class);
      if (responseEntity.getBody().indexOf("TRUE") != -1
          && responseEntity.getStatusCode() == HttpStatus.OK) {
        return true;
      }
    } catch (Exception e) {
      log.error("Error occured while sending email");
    }
    return false;
  }
}
