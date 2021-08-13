package com.sneakalarm.raffle.dto;

public interface ParsedElement {
  String parseCountry();
  String parseDelivery();
  String parseRaffleUrl() throws Exception;
  String parseStartDateTime();
  String parseEndDateTime();
  String parseRaffleType();
}