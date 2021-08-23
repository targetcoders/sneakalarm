package com.sneakalarm.raffle.dto;

public class ActiveRafflesVO {
  private String productId;
  private String storeName;

  public ActiveRafflesVO(String productId, String storeName){
    this.productId = productId;
    this.storeName = storeName;
  }

}
