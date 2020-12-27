package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductVO {
  private String name;
  private String code;
  private String price;
  private String brand;
  private String content;
  private String imgSrc;
  private String filterCode;
  private String startDate;
  private String endDate;
  private String popularity;
  private String insertDate;
  private Boolean flag_show;
  private Boolean flag_del;

  public ProductVO() {}

  public ProductVO(ProductInsertVO productInsertVO) {
    this.name = productInsertVO.getName();
    this.code = productInsertVO.getCode();
    this.price = productInsertVO.getPrice();
    this.brand = productInsertVO.getBrand();
    this.content = productInsertVO.getContent();
  }
}
