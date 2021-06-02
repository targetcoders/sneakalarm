package com.sneakalarm.rafflesetting.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
public class RaffleSetting {

  private String id;
  private String raffleSettingName;
  private String raffleType;
  private String storeName;
  private String country;
  private String delivery;
  private String payType;
  private String specialCase;
  private String productPrice;
  private String imgSrc;
  @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
  private LocalDateTime startDateTime;
  @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
  private LocalDateTime endDateTime;

  public RaffleSetting() {
  }

  @Builder
  public RaffleSetting(String id, String raffleSettingName, String raffleType, String storeName, String country,
      String delivery, String payType, String specialCase, String productPrice, String imgSrc, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.id = id;
    this.raffleSettingName = raffleSettingName;
    this.raffleType = raffleType;
    this.storeName = storeName;
    this.country = country;
    this.delivery = delivery;
    this.payType = payType;
    this.specialCase = specialCase;
    this.productPrice = productPrice;
    this.imgSrc = imgSrc;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

}
