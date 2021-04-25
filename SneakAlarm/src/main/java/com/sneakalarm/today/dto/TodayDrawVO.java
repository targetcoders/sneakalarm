package com.sneakalarm.today.dto;

import lombok.Data;

@Data
public class TodayDrawVO {

  private String storeName;
  private String raffleType;
  private String startDate;
  private String startTime;
  private String endData;
  private String endTime;

  public TodayDrawVO(String storeName, String raffleType, String startDate, String startTime, String endData, String endTime) {
    this.storeName = storeName;
    this.raffleType = raffleType;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endData = endData;
    this.endTime = endTime;
  }
}
