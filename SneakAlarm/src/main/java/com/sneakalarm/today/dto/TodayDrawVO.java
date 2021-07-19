package com.sneakalarm.today.dto;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class TodayDrawVO implements Comparable<TodayDrawVO> {
  private String id;
  private String url;
  private String storeName;
  private String raffleType;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String delivery;
  private String status;
  private String startWeek;
  private String endWeek;
  private String specialCase;
  private String model_kr;
  private String releasePrice;

  public TodayDrawVO(String id, String url, String storeName, String raffleType, String startDate,
      String startTime, String endDate, String endTime, String delivery, String status,
      String startWeek, String endWeek, String specialCase, String model_kr, String releasePrice) {
    if(raffleType.equals("선착순"))
      raffleType = "선착";
    this.id = id;
    this.url = url;
    this.storeName = storeName;
    this.raffleType = raffleType;
    this.startDate = startDate.replaceAll("-","/").substring(5);
    this.startTime = startTime.substring(0,5);
    this.endDate = endDate.replaceAll("-","/").substring(5);
    this.endTime = endTime.substring(0,5);
    this.delivery = delivery;
    this.status = status;
    this.startWeek = startWeek;
    this.endWeek = endWeek;
    this.specialCase = specialCase;
    this.model_kr = model_kr;
    this.releasePrice = releasePrice;
  }

  @SneakyThrows
  @Override
  public int compareTo(TodayDrawVO o) {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm");
    Date endDateTime1 = sdf.parse(this.getEndDate() + " " + this.getEndTime());
    Date endDateTime2 = sdf.parse(o.getEndDate() + " " + o.getEndTime());

    return endDateTime2.compareTo(endDateTime1);
  }
}
