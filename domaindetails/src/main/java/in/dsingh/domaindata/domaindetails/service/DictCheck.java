package in.dsingh.domaindata.domaindetails.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DictCheck {

  private Set<String> dict;

  @PostConstruct
  public void loadDict() {
    dict = new HashSet<>();
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("20k.txt").getFile());
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while((line = reader.readLine()) != null) {
        if(line.length() > 2) {
          dict.add(line.toLowerCase());
        }
      }
    } catch (IOException e) {
      log.error("Unable to load dictionary {}", e);
    }
  }

  private static Set<String> getAllSubStrings(String str) {
      Set<String> subStrs = new HashSet<>();
      for (int i = 0; i < str.length(); i++) {
        for (int j = i + 1; j <= str.length(); j++) {
          subStrs.add(str.substring(i, j));
        }
      }
      return subStrs.stream().filter(word -> word.length() > 2).collect(Collectors.toSet());
  }

  public Optional<String> hasWord(String domainName) {
    Set<String> words = getAllSubStrings(domainName);
    String foundWord = null;
    for(String word : words) {
      if(dict.contains(word.toLowerCase())) {
        if(foundWord == null) {
          foundWord = word;
        } else {
          if(word.length() > foundWord.length()) {
            foundWord = word;
          }
        }
      }
    }
    return (foundWord == null) ? Optional.empty() : Optional.of(foundWord);
  }
}
