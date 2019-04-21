package in.dsingh.domaindata.domaindetails.data.repositories;

import in.dsingh.domaindata.domaindetails.data.entities.DomainEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainEntityRepository extends CrudRepository<DomainEntity, Long> {

  DomainEntity findFirstByDomainNameIs(String domainName);

  @Query(value = "select * from domain_entity where is_visited = 0 and retry_count < 2 order by updated_at asc, domain_score desc limit 50", nativeQuery = true)
  List<DomainEntity> findRecordToParse();
}
