package in.dsingh.domaindata.domaindetails.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidator {

  private static Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

  public static Integer titleLength = 40;

  public static Integer bodyLength = 160;

  public static String getWordsAsString(String text, Integer trimLength) {
    Matcher matcher = pattern.matcher(text);
    List<String> words = new ArrayList<>();
    while (matcher.find()) {
      words.add(matcher.group());
    }
    StringBuilder sb = new StringBuilder();

    if(words.size() == 0) {
      return "";
    }

    for(String word : words) {
      sb.append(" " + word);
    }

    String finalStr = sb.toString().trim();
    return finalStr.substring(0, Math.min(finalStr.length(), trimLength));
  }

}
