package com.sneakalarm.raffle.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class RaffleInsertVO {
  private String id;
  private String productId;
  private String url;
  private String raffleType;
  private String releasePrice;
  private String releasePrice_nz;
  private String storeName;
  private String country;
  private String delivery;
  private String payType;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String content;
  private String specialCase;
  private String specialCase_nz;
  private String imgSrc;
  private MultipartFile file;
  private String model_kr;
}
