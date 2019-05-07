package in.dsingh.domaindata.domaindetails.service;

import in.dsingh.domaindata.domaindetails.cron.DomainHealth;
import in.dsingh.domaindata.domaindetails.cron.response.WebsiteMetaInfoResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WebPageParser {

  private static Logger LOGGER = LoggerFactory.getLogger(WebPageParser.class);

  private Boolean hasSameParent(String domainName, String newUrl) {
    return newUrl.indexOf(domainName) == 0;
  }

  private Document getDocument(String url) {
    Document document = null;
    try {
      document = Jsoup.connect(url)
          .userAgent(
              "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
          .referrer("https://www.google.com")
          .timeout(40000)
          .get();
    } catch (IOException e) {
      LOGGER.error("Could not get webpage {}", url);
      return null;
    }
    return document;
  }

  public Optional<WebsiteMetaInfoResponse> getUrlList(DomainHealth domainHealth) {

    Set<String> urlList = new HashSet<>();

    if (domainHealth != null && domainHealth.getProtocol() != null && domainHealth.isAlive() &&
        domainHealth.getDomainName() != null) {

      String url = domainHealth.getProtocol().toLowerCase() + "://" + domainHealth.getDomainName();

      urlList.add(url);

      LOGGER.info("Connecting to URL {}", url);

      Document document = getDocument(url);
      WebsiteMetaInfoResponse websiteMetaInfoResponse = new WebsiteMetaInfoResponse();

      if (document != null) {
        Elements linkElements = document.select("a");
        for (Element e : linkElements) {
          String newUrl = e.attr("abs:href");
          if (hasSameParent(url, newUrl) && isContactPage(newUrl)) {
            urlList.add(newUrl);
          }
        }
        websiteMetaInfoResponse.setUrls(urlList);
        websiteMetaInfoResponse.setBodyText(document.text());
        websiteMetaInfoResponse.setTitleText(document.title());

        return Optional.of(websiteMetaInfoResponse);
      }
    }

    return Optional.empty();
  }

  private boolean isContactPage(String newUrl) {
    List<String> contactWords = new ArrayList<>();

    contactWords.add("contact");
    contactWords.add("message");
    contactWords.add("mail");
    contactWords.add("msg");
    contactWords.add("touch");

    for (String word : contactWords) {
      if (newUrl.indexOf(word) != -1) {
        return true;
      }
    }
    return false;
  }


  public Set<String> getEmails(Set<String> urlList) {

    Set<String> allEmails = new HashSet<>();
    Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");

    for(String page : urlList) {
      Document document = getDocument(page);

      if (document != null) {
        Matcher matcher = p.matcher(document.text());
        while (matcher.find()) {
          allEmails.add(matcher.group());
        }
      }

    }
    return allEmails;
  }
}
