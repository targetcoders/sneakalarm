package com.sneakalarm.raffle.domain;

import java.io.IOException;
import org.jsoup.nodes.Document;

public class JsoupImplForShoeprize implements Jsoup {

  @Override
  public Document getDocument(String url) throws IOException {
    String[] userAgentList = new String[]{
        "Mozilla/5.0 (iPhone; CPU iPhone OS 14_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 142.0.0.22.109 (iPhone12,5; iOS 14_1; en_US; en-US; scale=3.00; 1242x2688; 214888322) NW/1"
    };
    return org.jsoup.Jsoup.connect(url)
        .userAgent(userAgentList[0])
        .referrer("https://www.shoeprize.com/")
        .get();
  }

  public Document productDetail(String dataUUID) throws IOException {
    String[] userAgentList = new String[]{
        "Mozilla/5.0 (iPhone; CPU iPhone OS 14_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 Instagram 142.0.0.22.109 (iPhone12,5; iOS 14_1; en_US; en-US; scale=3.00; 1242x2688; 214888322) NW/1"
    };
    return org.jsoup.Jsoup.connect("https://www.shoeprize.com/api/v2/product_release/"+dataUUID)
        .userAgent(userAgentList[0])
        .referrer("https://www.shoeprize.com/")
        .get();
  }
}
