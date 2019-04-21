package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.data.entities.EmailEntity;
import in.dsingh.domaindata.domaindetails.data.repositories.EmailEntityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EmailsService {

  @Autowired
  private EmailEntityRepository emailEntityRepository;

  public String getEmailCount() {
    List<EmailEntity> emailEntityList = emailEntityRepository.getGoodEmails();
    if(!CollectionUtils.isEmpty(emailEntityList)) {
      return String.valueOf(emailEntityList.size());
    }
    return "0";
  }
}
