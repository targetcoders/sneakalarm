package com.sneakalarm.raffle.dto;

import lombok.Data;

@Data
public class RaffleCardVO {
  private String id;
  private Integer productId;
  private String url;
  private String raffleType;
  private String releasePrice;
  private String imgSrc;
  private String storeName;
  private String country;
  private String delivery;
  private String payType;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String specialCase;
  private String content;
  private String status;

  public RaffleCardVO() {

  }

  public RaffleCardVO(RaffleVO raffleVO) {
    id = raffleVO.getId();
    productId = raffleVO.getProductId();
    url = raffleVO.getUrl();
    raffleType = raffleVO.getRaffleType();
    releasePrice = raffleVO.getReleasePrice();
    imgSrc = raffleVO.getImgSrc();
    storeName = raffleVO.getStoreName();
    country = raffleVO.getCountry();
    delivery = raffleVO.getDelivery();
    payType = raffleVO.getPayType();
    startDate = raffleVO.getStartDate();
    endDate = raffleVO.getEndDate();
    startTime = raffleVO.getStartTime();
    endTime = raffleVO.getEndTime();
    specialCase = raffleVO.getSpecialCase();
    content = raffleVO.getContent();
  }
}
