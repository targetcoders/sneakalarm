package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class InsertDrawVO {
	String drawType;
	Integer productId;
	
	public InsertDrawVO(String drawType, Integer productId){
		this.drawType = drawType;
		this.productId = productId;
	}
	
	public InsertDrawVO(){
		
	}
}
