package com.sneakalarm.raffle.domain;

import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Setter @Getter @ToString
public class RaffleElementsParserForLuckD extends RaffleElementsParser {

  public RaffleElementsParserForLuckD(String targetSiteUrl, Jsoup jsoup) throws IOException {
    super(targetSiteUrl, jsoup);
  }

  @Override
  public Elements getTargetStoreElements(String targetSiteUrl, String storeName) {
    Elements result = new Elements();
    if(!targetSiteUrl.contains("luck-d.com")){
      return result;
    }

    Elements raffleElements = getDoc().select("div.site_card");
    for (Element e : raffleElements) {
      Elements releaseDateTimeList = e.getElementsByClass("release_date_time");
      if (!releaseDateTimeList.get(0).text().equals("종료")) {
        String tempStoreName = e.getElementsByClass("agent_site_title").get(0).text();
        if(tempStoreName.equals(storeName)) {
          result.add(e);
        }
      }
    }
    return result;
  }
}
