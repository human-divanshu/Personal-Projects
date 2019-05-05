package in.dsingh.domaindata.domaindetails.data.repositories;

import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainEntityRepository extends CrudRepository<DomainEntity, Long> {

  DomainEntity findFirstByDomainNameIs(String domainName);

  @Query(value = "select * from domain_entity where is_visited = 0 and retry_count < 2 order by updated_at asc, domain_score desc limit 20", nativeQuery = true)
  List<DomainEntity> findRecordToParse();

  @Query(value = "select * from domain_entity where is_visited = 1 and is_parsed = 1 and is_uploaded = 0 order by domain_score desc limit 200", nativeQuery = true)
  List<DomainEntity> getDomainToSendEmail();
}
