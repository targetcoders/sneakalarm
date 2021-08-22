package com.sneakalarm.raffle.domain;

import java.io.IOException;
import java.util.Locale;
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
      if(brands.isEmpty()){
        continue;
      }

      String brand = brands.get(0).text().trim();
      if(brand.isEmpty()) {
        brand = brands.get(0).select(".btn_brand_info_enable").attr("data-name");
      }
      brand = brand.toUpperCase().replaceAll(" ","");
      storeName = storeName.toUpperCase().replaceAll(" ","");

      if (brand.equals(storeName)) {
        Elements btns = e.select(".btn_area button");
        if (btns.isEmpty()) {
          continue;
        }

        if (btns.get(0).text().contains("응모") || btns.get(0).text().contains("선착순")) {
          result.add(e);
        }
      }

    }

    return result;
  }
}
