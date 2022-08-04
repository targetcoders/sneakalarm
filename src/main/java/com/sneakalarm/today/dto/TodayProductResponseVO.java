package com.sneakalarm.today.dto;

import lombok.Data;

@Data
public class TodayProductResponseVO {
  private String id;
  private String model_kr;
  private String deliveryTypes;
  private String imgSrc_home;

  public TodayProductResponseVO(String id, String deliveryTypes, String imgSrc_home, String model_kr) {
    this.id = id;
    this.deliveryTypes = deliveryTypes;
    this.imgSrc_home = imgSrc_home;
    this.model_kr = model_kr;
  }
}
