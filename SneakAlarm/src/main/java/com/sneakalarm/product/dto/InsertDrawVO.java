package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class InsertDrawVO {
	Integer drawNumKorea;
	Integer drawNumOverseas;
	Integer productId;
	
	public InsertDrawVO(Integer drawNumKorea, Integer drawNumOverseas, Integer productId){
		this.drawNumKorea = drawNumKorea;
		this.drawNumOverseas = drawNumOverseas;
		this.productId = productId;
	}
	
	public InsertDrawVO(){
		
	}
}
