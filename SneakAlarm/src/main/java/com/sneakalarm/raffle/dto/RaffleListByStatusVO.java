package com.sneakalarm.raffle.dto;

import lombok.Data;

@Data
public class RaffleListByStatusVO {
  private String productId;
  private String status;

  public RaffleListByStatusVO(String productId, String status) {
    this.productId = productId;
    this.status = status;
  }
}
