package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.cron.DomainHealth;
import in.dsingh.domaindata.domaindetails.cron.response.WebsiteMetaInfoResponse;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class WebPageParserTest {

  private WebPageParser webPageParser;
  private DomainHealth domainHealth;
  private String domainName = "bestinncr.com";

  @Before
  public void setup() {
    webPageParser = new WebPageParser();
    domainHealth = new DomainHealth(domainName, true, "HTTP", "200", true);
  }

  @Test
  public void parseWorks() {
    Optional<WebsiteMetaInfoResponse> response = webPageParser.getUrlList(domainHealth);
    for(String url: response.get().getUrls()) {
      System.out.println(url);
    }

    Set<String> emails = webPageParser.getEmails(response.get().getUrls());
    for(String email: emails) {
      System.out.println(email);
    }
  }
}