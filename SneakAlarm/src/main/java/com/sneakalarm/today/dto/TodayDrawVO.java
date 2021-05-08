package com.sneakalarm.today.dto;

import lombok.Data;

@Data
public class TodayDrawVO {
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

  public TodayDrawVO(String id, String url, String storeName, String raffleType, String startDate, String startTime, String endDate, String endTime, String delivery, String status) {
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
  }
}
