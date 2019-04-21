package in.dsingh.domaindata.domaindetails.cron;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.dsingh.domaindata.domaindetails.cron.response.DnpediaResponse;
import java.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DomainListFetchHelper {

  static HttpHeaders headers = new HttpHeaders();

  static final String dnPediaUrl = "https://dnpedia.com/tlds/ajax.php";

  private RestTemplate restTemplate;

  private ObjectMapper mapper;

  DomainListFetchHelper() {
    restTemplate = new RestTemplate();
    mapper = new ObjectMapper();
  }

  static {
    headers.add("User-Agent",
        "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:65.0) Gecko/20100101 Firefox/65.0");
    headers.add("Accept", "application/json, text/javascript, */*; q=0.01");
    headers.add("Accept-Language", "en-US,en;q=0.5");
    headers.add("Referer", "https://dnpedia.com/tlds/ndaily.php");
    headers.add("X-Requested-With", "XMLHttpRequest");
    headers.add("DNT", "1");
    headers.add("Connection", "keep-alive");
  }

  /*
  pageNumber - to fetch and
  date in yyyy-mm-dd format
   */
  public DnpediaResponse fetchDomainList(String pageNumber, String date, String zone)
      throws IOException {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(dnPediaUrl)
        .queryParam("cmd", "added")
        .queryParam("columns", "id,name,length,idn,thedate,")
        .queryParam("ecf", "zoneid,thedate")
        .queryParam("ecv", "1," + date)
        .queryParam("zone", zone)
        .queryParam("_search", "false")
        .queryParam("rows", "2000")
        .queryParam("page", pageNumber)
        .queryParam("sidx", "length")
        .queryParam("sord", "asc");

    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<String> responseStr = restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET,
            entity, String.class);

    DnpediaResponse response = mapper.readValue(responseStr.getBody(), DnpediaResponse.class);
    return response;
  }
}
