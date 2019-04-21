package in.dsingh.domaindata.domaindetails.cron.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class DnpediaResponse {

  @NotBlank
  private String total;

  @NotBlank
  private String records;

  @NotBlank
  private String start;

  @NotBlank
  private String limit;

  @NotBlank
  private String page;

  @JsonProperty("rows")
  @NotEmpty
  private ArrayList<DnpediaEntry> rows;
}
