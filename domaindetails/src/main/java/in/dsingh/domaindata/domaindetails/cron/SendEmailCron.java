package in.dsingh.domaindata.domaindetails.cron;

import static in.dsingh.domaindata.domaindetails.service.RequestValidator.bodyLength;
import static in.dsingh.domaindata.domaindetails.service.RequestValidator.getWordsAsString;
import static in.dsingh.domaindata.domaindetails.service.RequestValidator.titleLength;

import in.dsingh.domaindata.domaindetails.controllers.request.SendEmailRequest;
import in.dsingh.domaindata.domaindetails.data.dbhelpers.DomainEntityDbHelper;
import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import in.dsingh.domaindata.domaindetails.data.entities.EmailEntity;
import in.dsingh.domaindata.domaindetails.service.EmailsService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SendEmailCron {

  @Autowired
  private DomainEntityDbHelper domainEntityDbHelper;

  @Autowired
  private EmailsService emailsService;

  private static List<String> emailSubStrings = new ArrayList<>();

  private static List<String> fromEmailList = new ArrayList<>();

  private static Integer currentFromEmailIndex;

  @Value("${run.email.send.cron}")
  private Boolean sendEmail;

  static {
    emailSubStrings.add("@gmail.com");
    emailSubStrings.add("@yahoo.com");
    emailSubStrings.add("@aol.com");
    emailSubStrings.add("@hotmail.com");
    emailSubStrings.add("@outlook.com");
    emailSubStrings.add("@msn.com");
    emailSubStrings.add("@live.com");
    emailSubStrings.add("@yahoo.co.uk");
  }

  static {
    fromEmailList.add("ken.garcia@websitechamp.tech");
    fromEmailList.add("harold.scott@websitechamp.tech");
    fromEmailList.add("kathryne.watson@websitechamp.tech");
    fromEmailList.add("maria.young@websitechamp.tech");
    fromEmailList.add("annie.owens@websitechamp.tech");
    fromEmailList.add("jessica.miller@websitechamp.tech");
    fromEmailList.add("keffy.grace@websitechamp.tech");
    currentFromEmailIndex = 0;
  }

  // run every minute
  @Scheduled(fixedDelayString = "60000")
  public void sendEmail() {

    if(!sendEmail) {
      return;
    }

    List<DomainEntity> domainEntityList = domainEntityDbHelper.getDomainToSendEmail();

    if(CollectionUtils.isEmpty(domainEntityList)) {
      log.info("No domains to send emails to");
      return;
    }

    for(DomainEntity entity : domainEntityList) {
      List<EmailEntity> emailEntityList = entity.getEmailEntityList();

      if(!CollectionUtils.isEmpty(emailEntityList)) {
        String emailId = getValidEmail(emailEntityList);

        if(emailId == null) {
          log.info("No emails found for entity {} hence marking done");
          domainEntityDbHelper.markRecordUploaded(entity, false, null);
          continue;
        }

        try {
          String fromEmailId = getNextFromEmailId();

          SendEmailRequest sendEmailRequest = new SendEmailRequest(String.valueOf(entity.getId()),
              entity.getDomainName(), emailId, fromEmailId,
              getWordsAsString(entity.getTitleText(), titleLength),
              getWordsAsString(entity.getBodyText(), bodyLength), "kuchbhi");

          Boolean successResponse = emailsService.sendEmail(sendEmailRequest);

          if (successResponse) {
            domainEntityDbHelper.markRecordUploaded(entity, false, fromEmailId);
          } else {
            domainEntityDbHelper.markRecordUploaded(entity, true, fromEmailId);
          }
        } catch (Exception e) {
          log.error("Error while sending email due to exception {}", e);
        }

        break;
      } else {
        domainEntityDbHelper.markRecordUploaded(entity, false, null);
      }
    }
  }

  private String getNextFromEmailId() {
    currentFromEmailIndex++;
    if(currentFromEmailIndex >= fromEmailList.size()) {
      currentFromEmailIndex = 0;
    }
    return fromEmailList.get(currentFromEmailIndex);
  }

  private String getValidEmail(List<EmailEntity> emailEntityList) {
    if(CollectionUtils.isEmpty(emailEntityList)) {
      return null;
    }

    for(EmailEntity emailEntity : emailEntityList) {
      for(String goodSub : emailSubStrings) {
        if(emailEntity.getEmailId().endsWith(goodSub)) {
          return emailEntity.getEmailId();
        }
      }
    }

    return null;
  }

  public Boolean getEmailSendingStatus() {
    return sendEmail;
  }
}
