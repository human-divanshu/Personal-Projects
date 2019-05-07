package in.dsingh.domaindata.domaindetails.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
public class SendEmailRequest {

  @NotBlank
  private String domainId;

  @NotBlank
  private String domainName;

  @NotBlank
  private String to;

  @NotBlank
  private String from;

  private String titleText;

  private String bodyText;

  private String sendPass = "something";
}
