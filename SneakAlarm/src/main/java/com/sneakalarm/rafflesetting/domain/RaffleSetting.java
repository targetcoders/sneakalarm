package com.sneakalarm.rafflesetting.domain;

import com.google.gson.Gson;
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
  private String content;
  private String releasePrice;
  private String imgSrc;
  @DateTimeFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
  private LocalDateTime insertDateTime;

  public RaffleSetting() {
  }

  @Builder
  public RaffleSetting(String id, String raffleSettingName, String raffleType, String storeName, String country,
      String delivery, String payType, String specialCase, String content, String releasePrice, String imgSrc, LocalDateTime insertDateTime) {
    this.id = id;
    this.raffleSettingName = raffleSettingName;
    this.raffleType = raffleType;
    this.storeName = storeName;
    this.country = country;
    this.delivery = delivery;
    this.payType = payType;
    this.specialCase = specialCase;
    this.content = content;
    this.releasePrice = releasePrice;
    this.imgSrc = imgSrc;
    this.insertDateTime = insertDateTime;
  }

  public String getJsonString() {
    return new Gson().toJson(this);
  }

}
