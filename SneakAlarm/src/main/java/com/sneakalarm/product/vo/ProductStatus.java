package com.sneakalarm.product.vo;

import lombok.Data;

@Data
public class ProductStatus {
    private String country;
    private String draw;
    private int goingRaffleNum;
    private int goingFirstcomeNum;

    public ProductStatus(String country, String draw, int goingRaffleNum, int goingFirstcomeNum) {
        this.country = country;
        this.draw = draw;
        this.goingRaffleNum = goingRaffleNum;
        this.goingFirstcomeNum = goingFirstcomeNum;
    }
}
