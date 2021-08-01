package com.sneakalarm.raffle.domain;

import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RaffleElementsParserForShoepize extends RaffleElementsParser {

  public RaffleElementsParserForShoepize(String targetUrl, Jsoup jsoup) throws IOException {
    super(targetUrl, jsoup);
  }

  @Override
  public Elements getTargetStoreElements(String targetSiteUrl, String storeName) {
    Elements result = new Elements();
    if(!targetSiteUrl.contains("shoeprize.com")){
      return result;
    }
    Elements raffleElements = getDoc().select("div.info_area");
    for(Element e : raffleElements){
      Elements brands = e.select(".brand");
      if (brands.isEmpty() || !brands.get(0).text().trim().equals(storeName)) {
        continue;
      }

      Elements btns = e.select(".btn_area button");
      if(btns.isEmpty()){
        continue;
      }

      if(!btns.get(0).text().contains("종료")) {
        result.add(e);
      }
    }

    return result;
  }
}
