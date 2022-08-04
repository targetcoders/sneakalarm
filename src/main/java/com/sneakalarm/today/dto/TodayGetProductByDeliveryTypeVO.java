package com.sneakalarm.today.dto;

public class TodayGetProductByDeliveryTypeVO {

  private String delivery;
  private String status;

  public TodayGetProductByDeliveryTypeVO(String delivery, String status) {

    this.delivery = delivery;
    this.status = status;
  }
}
