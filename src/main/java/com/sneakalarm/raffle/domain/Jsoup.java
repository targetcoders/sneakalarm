package com.sneakalarm.raffle.domain;

import java.io.IOException;
import org.jsoup.nodes.Document;

public interface Jsoup {
  Document getDocument(String url) throws IOException;
}

