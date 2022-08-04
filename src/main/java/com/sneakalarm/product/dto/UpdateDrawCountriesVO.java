package com.sneakalarm.product.dto;

public class UpdateDrawCountriesVO {
  private String productId;
  private String countries;

  public UpdateDrawCountriesVO(String productId, String countries) {
    this.productId = productId;
    this.countries = countries;
  }
}
