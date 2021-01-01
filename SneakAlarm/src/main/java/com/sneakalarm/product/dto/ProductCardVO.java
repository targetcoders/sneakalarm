package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductCardVO {
  private String imgSrc_home;
  private String name;
  private String startDate;
  private String endDate;
  private String timeLeft;
  private String price;
  private String code;

  public ProductCardVO() {}
}
