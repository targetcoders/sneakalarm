package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductVO {
  private String id;
  private String model_kr;
  private String model_en;
  private String code;
  private String retailPrice;
  private String brand;
  private String content;
  private String imgSrc_home;
  private String imgSrc_detail;
  private String releaseStartDate;
  private String releaseEndDate;
  private String popularity;
  private String insertDate;
  private String releaseDate;
  private String premiumPrice;
  private String averageSalePrice;
  private String lastUpdateDate;
  private String size;
  private String country;
  private String draw;
  private String lowestSoldPrice;
  private String highestSoldPrice;
  private int isDeleted;
  private int goingRaffleNum;
  private int goingFirstcomeNum;

  public ProductVO() {}

  public ProductVO(ProductInsertVO productInsertVO) {
    this.model_kr = productInsertVO.getModel_kr();
    this.model_en = productInsertVO.getModel_en();
    this.code = productInsertVO.getCode();
    this.retailPrice = productInsertVO.getRetailPrice();
    this.brand = productInsertVO.getBrand();
    this.content = productInsertVO.getContent();
    this.releaseStartDate = productInsertVO.getReleaseStartDate();
    this.releaseEndDate = productInsertVO.getReleaseEndDate();
    this.premiumPrice = productInsertVO.getPremiumPrice();
    this.averageSalePrice = productInsertVO.getAverageSalePrice();
    this.size = productInsertVO.getSize();
    this.lowestSoldPrice = productInsertVO.getLowestSoldPrice();
    this.highestSoldPrice = productInsertVO.getHighestSoldPrice();
    this.id = productInsertVO.getId();
  }


}
