package com.sneakalarm.raffle.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor @Builder
public class RaffleVO implements Comparable<RaffleVO> {
  private String id;
  private String raffleSettingId;
  private String productId;
  private String url;
  private String raffleType;
  private String releasePrice;
  private String releasePrice_nz;
  private String imgSrc;
  private String storeName;
  private String country;
  private String delivery;
  private String payType;
  private String startDate;
  private String startTime;
  private String endDate;
  private String endTime;
  private String specialCase;
  private String specialCase_nz;
  private String content;
  private String status;
  private String model_kr;
  private String startWeek;
  private String endWeek;

  public RaffleVO(RaffleInsertVO raffleInsertVO) {
    specialCase = raffleInsertVO.getSpecialCase();
    specialCase_nz = raffleInsertVO.getSpecialCase_nz();
    url = raffleInsertVO.getUrl();
    productId = raffleInsertVO.getProductId();
    raffleType = raffleInsertVO.getRaffleType();
    releasePrice = raffleInsertVO.getReleasePrice();
    releasePrice_nz = raffleInsertVO.getReleasePrice_nz();
    storeName = raffleInsertVO.getStoreName();
    country = raffleInsertVO.getCountry();
    delivery = raffleInsertVO.getDelivery();
    payType = raffleInsertVO.getPayType();
    startDate = raffleInsertVO.getStartDate();
    startTime = raffleInsertVO.getStartTime();
    endDate = raffleInsertVO.getEndDate();
    endTime = raffleInsertVO.getEndTime();
    content = raffleInsertVO.getContent();
    imgSrc = raffleInsertVO.getImgSrc();
    model_kr = raffleInsertVO.getModel_kr();
  }

  public RaffleVO() {

  }

  @SneakyThrows
  @Override
  public int compareTo(RaffleVO o) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String nowYear = sdf.format(new Date()).substring(0,4);
    Date dateTime1 = sdf.parse(nowYear+"/"+this.getEndDate()+" "+this.getEndTime());
    Date dateTime2 = sdf.parse(nowYear+"/"+o.getEndDate()+" "+o.getEndTime());
    return dateTime1.compareTo(dateTime2);
  }
}
