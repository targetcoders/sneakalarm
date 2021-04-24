package com.sneakalarm.raffle.dto;

import com.sneakalarm.raffle.RaffleConst;
import lombok.Data;

@Data
public class RaffleVO {
  private String id;
  private String productId;
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

  public RaffleVO(RaffleInsertVO raffleInsertVO) {
    specialCase = raffleInsertVO.getSpecialCase();
    url = raffleInsertVO.getUrl();
    productId = raffleInsertVO.getProductId();
    raffleType = raffleInsertVO.getRaffleType();
    releasePrice = raffleInsertVO.getReleasePrice();
    storeName = raffleInsertVO.getStoreName();
    country = raffleInsertVO.getCountry();
    delivery = raffleInsertVO.getDelivery();
    payType = raffleInsertVO.getPayType();
    startDate = raffleInsertVO.getStartDate();
    startTime = raffleInsertVO.getStartTime();
    endDate = raffleInsertVO.getEndDate();
    endTime = raffleInsertVO.getEndTime();
    content = raffleInsertVO.getContent();
  }

  public RaffleVO() {

  }

}
