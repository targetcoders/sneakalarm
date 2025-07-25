package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductCardVO {
  private long id;
  private String model_kr;
  private String model_en;
  private String code;
  private String retailPrice;
  private String releaseStartDate;
  private String releaseEndDate;
  private String timeLeft;
  private String imgSrc_home;
  private String releaseStartMonthAndDay;
  private String releaseEndMonthAndDay;
  private String status;
  private String draw;
  private String releaseDate;
  private Integer drawNumKorea;
  private Integer drawNumOverseas;
  private Integer drawNumFirstcome;

  public ProductCardVO() {}
}
