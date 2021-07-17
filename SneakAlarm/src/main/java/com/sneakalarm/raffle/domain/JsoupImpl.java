package com.sneakalarm.raffle.domain;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupImpl implements Jsoup {

  @Override
  public Document getDocument(String url) throws IOException {
    return org.jsoup.Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
        .referrer("http://www.google.com")
        .get();
  }
}
