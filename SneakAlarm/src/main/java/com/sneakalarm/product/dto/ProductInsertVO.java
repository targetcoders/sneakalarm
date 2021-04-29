package com.sneakalarm.product.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class ProductInsertVO {
  private String id;
  private String model_kr;
  private String model_en;
  private String code;
  private String retailPrice;
  private String brand;
  private String content;
  private String releaseStartDate;
  private String releaseEndDate;
  private String premiumPrice;
  private String averageSalePrice;
  private String size;
  private String lowestSoldPrice;
  private String highestSoldPrice;
  private List<MultipartFile> fileList_home;
  private List<MultipartFile> fileList_detail;
  private String releaseDate;
  private String isChanged;
  private String country;
  private String draw;
  private String deliveryType;

  public ProductInsertVO() {}
}
