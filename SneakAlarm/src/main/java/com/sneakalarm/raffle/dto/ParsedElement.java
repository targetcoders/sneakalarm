package com.sneakalarm.raffle.dto;

import org.jsoup.nodes.Element;

public interface ParsedElement {
  String parseCountry();
  String parseDelivery();
  String parseRaffleUrl();
  String parseEndDateTime();
}