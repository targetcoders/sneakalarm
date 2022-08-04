package com.sneakalarm.raffle.domain;

import java.io.IOException;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Data
public abstract class RaffleElementsParser {
  private Document doc;
  private Jsoup jsoup;

  public RaffleElementsParser(String url, Jsoup jsoup) throws IOException {
    checkArguments(url, jsoup);
    this.jsoup = jsoup;
    this.doc = jsoup.getDocument(url);
  }

  private void checkArguments(String url, Jsoup jsoup) {
    if(url == null || url.equals("")){
      throw new IllegalArgumentException("url은 빈 문자열 또는 null이 될 수 없습니다.");
    }
    if(jsoup == null){
      throw new IllegalArgumentException("jsoup은 null이 될 수 없습니다.");
    }
  }

  abstract public Elements getTargetStoreElements(String targetSiteUrl, String storeName);

}
