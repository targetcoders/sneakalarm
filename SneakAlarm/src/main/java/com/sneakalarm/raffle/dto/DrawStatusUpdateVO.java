package com.sneakalarm.raffle.dto;

import lombok.Data;

@Data
public class DrawStatusUpdateVO {
    private String id;
    private String status;

    public DrawStatusUpdateVO(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
