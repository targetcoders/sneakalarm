package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.Week;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DrawGroup implements Comparable<DrawGroup>{
  public final static String STATUS_ACTIVE = "active";
  public final static String STATUS_READY = "ready";

  private ProductVO productVO;
  private List<RaffleVO> targetRaffleVOList;
  private Date firstEndDateTime;

  public DrawGroup(String productId, String[] deliveryTypes, String status, ProductMapper productMapper, RaffleMapper raffleMapper)
      throws Exception {
    this.productVO = productMapper.getProductList(productId).get(0);
    this.targetRaffleVOList =
        status.equals(STATUS_ACTIVE) ? activeRaffleList(deliveryTypes, raffleMapper)
            : readyRaffleList(deliveryTypes, raffleMapper);
    Collections.sort(this.targetRaffleVOList);
    this.firstEndDateTime = firstEndDateTime(targetRaffleVOList);
  }

  private List<RaffleVO> activeRaffleList(String[] deliveryTypes, RaffleMapper raffleMapper) throws Exception {
    return formattedTargetRaffleList(deliveryTypes, activeRaffleVOList(productVO.getId(),raffleMapper));
  }

  private List<RaffleVO> readyRaffleList(String[] deliveryTypes, RaffleMapper raffleMapper) throws Exception {
    return formattedTargetRaffleList(deliveryTypes, readyRaffleVOList(productVO.getId(),raffleMapper));
  }

  private List<RaffleVO> formattedTargetRaffleList(String[] deliveryTypes, List<RaffleVO> activeRaffleList)
      throws Exception {
    List<RaffleVO> result = new ArrayList<>();
    for (RaffleVO raffle : activeRaffleList) {
      for (String delivery : deliveryTypes) {
        if (raffle.getDelivery().equals(delivery)) {
          convertToTodayFormat(raffle);
          result.add(raffle);
        }
      }
    }
    return result;
  }

  private void convertToTodayFormat(RaffleVO raffle) throws Exception {
    raffle.setStartWeek(
        new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getStartDate())).get());
    raffle.setEndWeek(
        new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getEndDate())).get());
    raffle.setStartDate(raffle.getStartDate().replaceAll("-", "/").substring(5));
    raffle.setEndDate(raffle.getEndDate().replaceAll("-", "/").substring(5));
    raffle.setStartTime(raffle.getStartTime().substring(0, 5));
    raffle.setEndTime(raffle.getEndTime().substring(0, 5));
    raffle.setContent(null);
  }

  private Date firstEndDateTime(List<RaffleVO> raffleVOList) throws ParseException {
    if(raffleVOList.isEmpty()){
      return new Date();
    }
    RaffleVO firstRaffle = raffleVOList.get(0);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String nowYear = sdf.format(new Date()).substring(0, 4);
    return sdf.parse(nowYear + "/" + firstRaffle.getEndDate() + " " + firstRaffle.getEndTime());
  }

  private List<RaffleVO> activeRaffleVOList(String productId, RaffleMapper raffleMapper) {
    return raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "active"));
  }

  private List<RaffleVO> readyRaffleVOList(String productId, RaffleMapper raffleMapper) {
    return raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "ready"));
  }

  @Override
  public int compareTo(DrawGroup o) {
    return this.getFirstEndDateTime().compareTo(o.getFirstEndDateTime());
  }

}
