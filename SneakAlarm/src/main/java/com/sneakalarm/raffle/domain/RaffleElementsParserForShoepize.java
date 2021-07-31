package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.domain.JsoupImpl;
import com.sneakalarm.raffle.domain.RaffleElementsParser;
import java.io.IOException;
import org.jsoup.select.Elements;

public class RaffleElementsParserForShoepize extends RaffleElementsParser {

  public RaffleElementsParserForShoepize(String targetUrl, JsoupImpl jsoup) throws IOException {
    super(targetUrl, jsoup);
  }

  @Override
  public Elements getTargetStoreElements(String targetSiteUrl, String storeName) {
    if(!targetSiteUrl.contains("shoeprize.com")){
      return null;
    }
    return null;
  }
}
