package in.dsingh.domaindata.domaindetails.data.repositories;

import in.dsingh.domaindata.domaindetails.data.entities.EmailEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailEntityRepository extends CrudRepository<EmailEntity, String> {

  @Query(value = "select * from emails where email_id like '%@gmail.com%' or email_id like '%@yahoo.com%' or email_id like '%@hotmail.com%'", nativeQuery = true)
  List<EmailEntity> getGoodEmails();
}

