package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.cron.DomainHealth;
import org.junit.Before;
import org.junit.Test;

public class DomainHealthCheckerTest {

  private DomainHealthChecker domainHealthChecker;

  @Before
  public void setup() {
    domainHealthChecker = new DomainHealthChecker();
  }

  @Test
  public void isDomainAlive() {
    DomainHealth domainHealth = domainHealthChecker.isDomainAlive("dsingh.in");
    System.out.println(domainHealth);

    System.out.println(domainHealthChecker.isDomainAlive("ajdjadjajk.com"));
  }
}