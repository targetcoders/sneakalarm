package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductCardVO {
  private long id;
  private String model;
  private String code;
  private String retailPrice;
  private String releaseEndDate;
  private String timeLeft;
  private String imgSrc_home;

  public ProductCardVO() {}
}
