package com.sneakalarm.raffle.dto;

import lombok.Data;
import org.jsoup.nodes.Element;

@Data
public class ParsedElementForLuckD implements ParsedElement {
  private Element parsedElement;

  public ParsedElementForLuckD(Element e) {
    this.parsedElement = e;
  }

  public String parseCountry(){
    String pText = parsedElement.getElementsByTag("p").get(0).text();
    String[] pTextArray = pText.split("/");
    return pTextArray[0].trim();
  }

  public String parseDelivery() {
    String pText = parsedElement.getElementsByTag("p").get(0).text();
    String delivery = null;
    if(pText.contains("/")){
      String[] pTextArray = pText.split("/");
      delivery = pTextArray[1].trim();
    }
    return delivery;
  }

  public String parseRaffleUrl() {
    String raffleUrl = parsedElement.getElementsByTag("a").get(0).attr("href");
    if(raffleUrl.contains("utm_source=luck-d")){
      int startIdx = raffleUrl.indexOf("utm_source=luck-d");
      raffleUrl = raffleUrl.substring(0,startIdx) + "utm_source=sneakalarm";
    }
    return raffleUrl;
  }

  public String parseEndDateTime() {
    String release_date_time = parsedElement.getElementsByClass("release_date_time").get(0).text();
    String[] splitEndDateTime = release_date_time.split(" ");
    String dateTime = splitEndDateTime[0].substring(0, 2) + "-" + splitEndDateTime[1].substring(0, 2);
    if(splitEndDateTime[2].equals("까지")) {
      return dateTime + " 23:59";
    }
    return dateTime + " " + splitEndDateTime[2];
  }
}
