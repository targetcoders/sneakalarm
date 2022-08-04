package com.sneakalarm.raffle.dto;

import lombok.Data;

@Data
public class RaffleUpdateStatusVO {
    private String id;
    private String status;

    public RaffleUpdateStatusVO(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
