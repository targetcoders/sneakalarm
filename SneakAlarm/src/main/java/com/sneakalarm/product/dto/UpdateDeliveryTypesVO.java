package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class UpdateDeliveryTypesVO {
  private String ProductId;
  private String deliveryTypes;

  public UpdateDeliveryTypesVO(String productId, String deliveryTypes) {
    this.ProductId = productId;
    this.deliveryTypes = deliveryTypes;
  }
}
