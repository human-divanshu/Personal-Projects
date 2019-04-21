package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.cron.DomainHealth;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DomainHealthChecker {

  private static Logger LOGGER = LoggerFactory
      .getLogger(DomainHealthChecker.class);

  private String makeHttpUrl(String domainName) {
    return "http://" + domainName;
  }

  private String makeHttpsUrl(String domainName) {
    return "https://" + domainName;
  }

  public DomainHealth isDomainAlive(String domainName) {

    URL httpUrl = null;
    URL httpsUrl = null;
    boolean isAlive = false;
    String protocol = null;
    String domainStatusCode = null;
    boolean checked = false;

    try {
      httpUrl = new URL(makeHttpUrl(domainName));
      httpsUrl = new URL(makeHttpsUrl(domainName));
    } catch (MalformedURLException e) {
      LOGGER.error("Could not create URL for domain name {}", domainName);
    }

    if(httpsUrl != null) {
      try {
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("HEAD");
        Integer statusCode = conn.getResponseCode();
        if(200 == statusCode) {
          isAlive = true;
          protocol = "HTTP";
        }
        domainStatusCode = Integer.toString(statusCode);
        checked = true;
      } catch (IOException e) {
        LOGGER.error("Could not check domain name {} for HTTP with exception {}", domainName, e.getMessage());
      }
    }

    if(httpsUrl != null) {
      try {
        HttpURLConnection conn = (HttpURLConnection) httpsUrl.openConnection();
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("HEAD");
        Integer statusCode = conn.getResponseCode();
        if(200 == statusCode) {
          isAlive = true;
          protocol = "HTTPS";
        }
        domainStatusCode = Integer.toString(statusCode);
        checked = true;
      } catch (IOException e) {
        LOGGER.error("Could not check domain name {} for HTTPS with exception {}", domainName, e.getMessage());
      }
    }

    return new DomainHealth(domainName, isAlive, protocol, domainStatusCode, checked);
  }
}
