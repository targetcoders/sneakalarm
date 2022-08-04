package com.sneakalarm.rafflesetting.domain;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "storeName")
public class RaffleSetting {

  private String id;
  private String raffleSettingName;
  private String raffleType;
  private String storeName;
  private String country;
  private String delivery;
  private String payType;
  private String specialCase;
  private String specialCase_nz;
  private String content;
  private String releasePrice;
  private String releasePrice_nz;
  private String imgSrc;
  @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
  private LocalDateTime insertDateTime;

  public RaffleSetting() {
  }

  @Builder
  public RaffleSetting(String id, String raffleSettingName, String raffleType, String storeName, String country,
      String delivery, String payType, String specialCase, String specialCase_nz, String content, String releasePrice, String releasePrice_nz, String imgSrc, LocalDateTime insertDateTime) {
    this.id = id;
    this.raffleSettingName = raffleSettingName;
    this.raffleType = raffleType;
    this.storeName = storeName;
    this.country = country;
    this.delivery = delivery;
    this.payType = payType;
    this.specialCase = specialCase;
    this.specialCase_nz = specialCase_nz;
    this.content = content;
    this.releasePrice = releasePrice;
    this.releasePrice_nz = releasePrice_nz;
    this.imgSrc = imgSrc;
    this.insertDateTime = insertDateTime;
  }

  public String getJsonString() {
    return new Gson().toJson(this);
  }

}
