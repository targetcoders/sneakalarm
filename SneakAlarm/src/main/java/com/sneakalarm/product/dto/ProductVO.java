package com.sneakalarm.product.dto;

import com.sneakalarm.product.vo.ProductStatus;
import lombok.Data;

@Data
public class ProductVO {
  private ProductStatus status;
  private String id;
  private String model_kr;
  private String model_en;
  private String code;
  private String signatureColor;
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
  private String lowestSoldPrice;
  private String highestSoldPrice;
  private String country;
  private String draw;
  private int isDeleted;
  private String deliveryTypes;
  private String drawNumKorea;
  private String drawNumOverseas;
  private String drawNumFirstcome;
  private String drawNumReady;
  private String detailImagesSize;

  public ProductVO() {}

  public ProductVO(ProductInsertVO productInsertVO) {
    this.model_kr = productInsertVO.getModel_kr();
    this.model_en = productInsertVO.getModel_en();
    this.code = productInsertVO.getCode();
    this.signatureColor = productInsertVO.getSignatureColor();
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
    this.releaseDate = productInsertVO.getReleaseDate();
    this.country = productInsertVO.getCountry();
    this.draw=productInsertVO.getDraw();
    this.deliveryTypes = productInsertVO.getDeliveryType();
    this.detailImagesSize = productInsertVO.getDetailImagesSize();
  }

}
