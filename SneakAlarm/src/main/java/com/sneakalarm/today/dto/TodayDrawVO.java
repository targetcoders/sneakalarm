package com.sneakalarm.today.dto;

import lombok.Data;

@Data
public class TodayDrawVO {
  private String url;
  private String storeName;
  private String raffleType;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;

  public TodayDrawVO(String url, String storeName, String raffleType, String startDate, String startTime, String endDate, String endTime) {
    if(raffleType.equals("선착순"))
      raffleType = "선착";
    this.url = url;
    this.storeName = storeName;
    this.raffleType = raffleType;
    this.startDate = startDate.replaceAll("-","/").substring(5);
    this.startTime = startTime;
    this.endDate = endDate.replaceAll("-","/").substring(5);
    this.endTime = endTime;
  }
}
