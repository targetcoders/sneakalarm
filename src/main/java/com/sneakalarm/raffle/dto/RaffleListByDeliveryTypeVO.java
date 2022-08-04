package com.sneakalarm.raffle.dto;

import lombok.Data;

@Data
public class RaffleListByDeliveryTypeVO {

  private String productId;
  private String deliveryType;

  public RaffleListByDeliveryTypeVO(String productId, String deliveryType) {
    this.productId = productId;
    this.deliveryType = deliveryType;
  }
}
