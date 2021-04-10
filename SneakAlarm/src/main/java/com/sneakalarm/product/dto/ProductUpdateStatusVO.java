package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductUpdateStatusVO {
  Integer productId;
  String status;
  
  public ProductUpdateStatusVO(Integer productId, String status) {
    this.productId = productId;
    this.status = status;
  }
  public ProductUpdateStatusVO() {
    
  }
}
