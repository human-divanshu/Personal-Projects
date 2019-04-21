package in.dsingh.domaindata.domaindetails.data.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
public class DnpediaCronEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date domainRegDate;

//  @Column(columnDefinition = "bigint not null default 0")
//  private Long totalPages;
//
//  @Column(columnDefinition = "bigint not null default 0")
//  private Long lastPageFetched;
//
//  @Column(columnDefinition = "boolean default false")
//  private boolean picked;
//
//  @Column(columnDefinition = "boolean default false")
//  private boolean done;

//  @Version
  private Long version;

  @UpdateTimestamp
  private Date updatedAt;

  @CreationTimestamp
  private Date createdAt;

//  private String zone;

  public DnpediaCronEntity(Date date) {
    this.domainRegDate = date;
//    this.zone = zone;
//    this.lastPageFetched = 0l;
//    this.totalPages = 0l;
    this.version = 0l;
//    this.picked = false;
//    this.done = false;
  }
}
