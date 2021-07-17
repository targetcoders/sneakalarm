package com.sneakalarm.raffle.domain;

import java.io.IOException;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Data
public class SiteCardParser {
  private Document luckyDrawDoc;
  private Jsoup jsoup;


  public SiteCardParser(String url, Jsoup jsoup) throws IOException {
    checkArguments(url, jsoup);
    this.jsoup = jsoup;
    this.luckyDrawDoc = getDocument(url);
  }

  public Elements getActiveSiteCards(String storeName) {
    Elements siteCardList = luckyDrawDoc.select("div.site_card");
    Elements targetStoreElementList = getTargetStoreElements(storeName, siteCardList);
    return targetStoreElementList;
  }

  private Elements getTargetStoreElements(String storeName, Elements siteCardList) {
    Elements result = new Elements();
    for (Element e : siteCardList) {
      Elements releaseDateTimeList = e.getElementsByClass("release_date_time");
      if (!releaseDateTimeList.get(0).text().equals("종료")) {
        String tempStoreName = e.getElementsByClass("agent_site_title").get(0).text();
        addTargetStoreElement(result, e, tempStoreName, storeName);
      }
    }
    return result;
  }

  private void addTargetStoreElement(Elements result, Element e, String tempStoreName, String storeName) {
    if(tempStoreName.equals(storeName)) {
      result.add(e);
    }
  }

  private void checkArguments(String url, Jsoup jsoup) {
    if(url.equals("") || url ==null){
      throw new IllegalArgumentException("url은 빈 문자열 또는 null이 될 수 없습니다.");
    }
    if(jsoup == null){
      throw new IllegalArgumentException("jsoup은 null이 될 수 없습니다.");
    }
  }

  private Document getDocument(String url) throws IOException {
    return jsoup.getDocument(url);
  }

  public String getCountry(Element e){
    String pText = e.getElementsByTag("p").get(0).text();
    String[] pTextArray = pText.split("/");
    return pTextArray[0].trim();
  }

  public String getDelivery(Element e) {
    String pText = e.getElementsByTag("p").get(0).text();
    String delivery = null;
    if(pText.contains("/")){
      String[] pTextArray = pText.split("/");
      delivery = pTextArray[1].trim();
    }
    return delivery;
  }
}
