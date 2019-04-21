package in.dsingh.domaindata.domaindetails.cron;

import in.dsingh.domaindata.domaindetails.DomaindetailsApplication;
import in.dsingh.domaindata.domaindetails.cron.response.DnpediaResponse;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = {DomaindetailsApplication.class} )
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "local")
public class DomainListFetchHelperTest {

  @Autowired
  private DomainListFetchHelper domainListFetchHelper;

  @Test
  public void fetchDomainList() {
    String date = "2019-03-25";
    String page = "47";
    String zone = "com";

    try {
      DnpediaResponse response = domainListFetchHelper.fetchDomainList(page, date, zone);
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("Exception in fetching data");
    }
  }
}