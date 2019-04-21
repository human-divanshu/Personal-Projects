package in.dsingh.domaindata.domaindetails.data.dbhelpers;

import in.dsingh.domaindata.domaindetails.DomaindetailsApplication;
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
public class DomainEntityDbHelperTest {

  @Autowired
  private DomainEntityDbHelper domainEntityDbHelper;


  @Test
  public void testDomainForDictWord() {
    if(domainEntityDbHelper.domainContainsDictWord("00np.com").isPresent()) {
      System.out.println("TRUE");
    } else {
      System.out.println("FALSE");
    }
  }

}