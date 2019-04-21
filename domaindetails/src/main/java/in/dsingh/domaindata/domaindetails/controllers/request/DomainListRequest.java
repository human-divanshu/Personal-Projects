package in.dsingh.domaindata.domaindetails.controllers.request;

import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class DomainListRequest {

  @NotBlank
  private String date;

  @NotEmpty
  private List<String> domainList;
}
