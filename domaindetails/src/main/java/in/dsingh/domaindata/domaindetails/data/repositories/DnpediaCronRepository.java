package in.dsingh.domaindata.domaindetails.data.repositories;

import in.dsingh.domaindata.domaindetails.data.entities.DnpediaCronEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnpediaCronRepository extends CrudRepository<DnpediaCronEntity, Long> {

//  DnpediaCronEntity findFirstByDoneIsFalseAndPickedIsFalseOrderByUpdatedAtAsc();
}
