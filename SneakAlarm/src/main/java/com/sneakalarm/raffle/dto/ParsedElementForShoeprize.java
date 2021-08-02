package com.sneakalarm.raffle.dto;

import com.sneakalarm.raffle.domain.DateTimeImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Data
public class ParsedElementForShoeprize implements ParsedElement {
  private Element parsedElement;

  public ParsedElementForShoeprize(Element e) {
    this.parsedElement = e;
  }

  @Override
  public String parseCountry(){
    return "";
  }

  @Override
  public String parseDelivery() {
    return parsedElement.select(".delivery").get(0).text().split("/")[1].trim();
  }

  @Override
  public String parseRaffleUrl() throws Exception {
    String raffleUrl = parsedElement.select(".btn_area button").get(0).attr("data-url");
    if(raffleUrl.contains("shoeprize.com")){
      throw new Exception();
    }

    if(raffleUrl.contains("utm_source=shoeprize")){
      int startIdx = raffleUrl.indexOf("utm_source=shoeprize");
      raffleUrl = raffleUrl.substring(0,startIdx) + "utm_source=sneakalarm";
    }
    return raffleUrl;
  }

  @Override
  public String parseStartDateTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String dateStartTime = parsedElement.select(".time_count").get(0).attr("data-start-time");
    return oClockDateTime(sdf, dateStartTime);
  }

  @Override
  public String parseEndDateTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String dataEndTime = parsedElement.select(".time_count").get(0).attr("data-end-time");
    return oClockDateTime(sdf, dataEndTime);
  }

  private String oClockDateTime(SimpleDateFormat sdf, String dateTime) {
    if(dateTime.equals("0")) {
      return sdf.format(new DateTimeImpl().getDate()).split(" ")[0] + " 00:00";
    }

    String result = sdf.format(new Date((long)Float.parseFloat(dateTime)*1000));
    String[] splitResult = result.split(" ");
    String[] time = splitResult[1].split(":");
    if(time[1].equals("58") || time[1].equals("59")){
      int min = 60 - Integer.parseInt(time[1]);
      result = sdf.format(new Date((long)Float.parseFloat(dateTime)*1000 + (1000L *60*min)));
    }
    return result;
  }

  @Override
  public String parseRaffleType() {
    return parsedElement.select(".btn_area button").get(0).text();
  }
}
