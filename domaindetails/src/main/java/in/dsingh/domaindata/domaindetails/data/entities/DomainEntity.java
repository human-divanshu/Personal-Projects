package in.dsingh.domaindata.domaindetails.data.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
public class DomainEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String domainName;

  private String httpProtocol = "HTTP";

  @Column(columnDefinition = "boolean default false")
  private boolean isAlive;

  @Column(columnDefinition = "boolean default false")
  private boolean isVisited;

  @Column(columnDefinition = "boolean default false")
  private boolean isParsed;

  @Column(columnDefinition = "boolean default false")
  private boolean isUploaded;

  private String httpStatusCode;

  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  private Long version;

  private Long retryCount;

  @ManyToOne(fetch = FetchType.EAGER)
  private DnpediaCronEntity dnpediaCronEntity;

  private String verificationSubstring;

  private Long domainScore;

  public DomainEntity(String name, DnpediaCronEntity record, String verificationSubstring, Long domainScore) {
    this.domainName = name;
    this.dnpediaCronEntity = record;
    this.retryCount = 0L;
    this.version = 0L;
    this.verificationSubstring = verificationSubstring;
    this.domainScore = domainScore;
  }
}
