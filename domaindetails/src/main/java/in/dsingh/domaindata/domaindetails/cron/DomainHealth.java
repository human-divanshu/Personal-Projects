package in.dsingh.domaindata.domaindetails.cron;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DomainHealth {
  private String domainName;

  private boolean isAlive;

  private String protocol;

  private String statusCode;

  private boolean checkedSuccessfully;
}
