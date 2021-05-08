package com.sneakalarm.product.dto;

import lombok.Data;

@Data
public class ProductUpdateDrawNumVO {
	Integer drawNumKorea;
	Integer drawNumOverseas;
	Integer drawNumFirstcome;
	Integer drawNumReady;
	Integer productId;
	
	public ProductUpdateDrawNumVO(Integer drawNumKorea, Integer drawNumOverseas, Integer drawNumFirstcome, Integer drawNumReady, Integer productId){
		this.drawNumKorea = drawNumKorea;
		this.drawNumOverseas = drawNumOverseas;
		this.drawNumFirstcome = drawNumFirstcome;
		this.drawNumReady = drawNumReady;
		this.productId = productId;
	}
	
	public ProductUpdateDrawNumVO(){
		
	}

	public void initProductUpdateDrawNumVO(Integer drawNumKorea, Integer drawNumOverseas, Integer drawNumFirstcome, Integer drawNumReady, Integer productId){
		this.drawNumKorea = drawNumKorea;
		this.drawNumOverseas = drawNumOverseas;
		this.drawNumFirstcome = drawNumFirstcome;
		this.drawNumReady = drawNumReady;
		this.productId = productId;
	}
}
