package in.dsingh.domaindata.domaindetails.cron.response;

import java.util.Set;
import lombok.Data;

@Data
public class WebsiteMetaInfoResponse {

  private Set<String> urls;

  private String titleText;

  private String bodyText;
}
