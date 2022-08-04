package com.sneakalarm.raffle.dto;

public class RaffleSearchCondition {
  private String productId;
  private String raffleSettingId;

  public RaffleSearchCondition(String productId, String raffleSettingId) {
    this.productId = productId;
    this.raffleSettingId = raffleSettingId;
  }

}
