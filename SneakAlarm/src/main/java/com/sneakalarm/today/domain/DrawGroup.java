package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.DateTranslator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DrawGroup implements Comparable<DrawGroup>, Group {
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
    targetRaffleVOList = formattedRaffleList(targetRaffleVOList);
  }

  public DrawGroup(RaffleVO raffleVO, ProductMapper productMapper) throws Exception {
    this.productVO = productMapper.getProductList(raffleVO.getProductId()).get(0);
    this.targetRaffleVOList = new ArrayList<>();
    formatted(raffleVO);
    this.targetRaffleVOList.add(raffleVO);
    this.firstEndDateTime = firstEndDateTime(targetRaffleVOList);
  }

  private List<RaffleVO> filterKr(List<RaffleVO> targetRaffleList) {
    List<RaffleVO> result = new ArrayList<>();
    for(RaffleVO raffle : targetRaffleList) {
      String country = raffle.getCountry().toUpperCase();
      if(country.equals("한국")) {
        result.add(raffle);
      }
    }
    return result;
  }

  private List<RaffleVO> activeRaffleList(String[] deliveryTypes, RaffleMapper raffleMapper) {
    List<RaffleVO> raffleList = targetRaffleList(deliveryTypes, activeRaffleVOList(productVO.getId(),raffleMapper));
    return filterKr(raffleList);
  }

  private List<RaffleVO> readyRaffleList(String[] deliveryTypes, RaffleMapper raffleMapper) {
    List<RaffleVO> raffleList = targetRaffleList(deliveryTypes, readyRaffleVOList(productVO.getId(),raffleMapper));
    return filterKr(raffleList);
  }

  private List<RaffleVO> targetRaffleList(String[] deliveryTypes, List<RaffleVO> activeRaffleList) {
    List<RaffleVO> result = new ArrayList<>();
    for (RaffleVO raffle : activeRaffleList) {
      for (String delivery : deliveryTypes) {
        if (raffle.getDelivery().equals(delivery)) {
          result.add(raffle);
        }
      }
    }
    return result;
  }
  private List<RaffleVO> formattedRaffleList(List<RaffleVO> activeRaffleList)
      throws Exception {
    List<RaffleVO> result = new ArrayList<>();
    for (RaffleVO raffle : activeRaffleList) {
      result.add(formatted(raffle));
    }
    return result;
  }

  private RaffleVO formatted(RaffleVO raffle) throws Exception {
    raffle.setStartWeek(
        new DateTranslator(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getStartDate())).krWeek());
    raffle.setEndWeek(
        new DateTranslator(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getEndDate())).krWeek());
    raffle.setStartDate(raffle.getStartDate().replaceAll("-", "/").substring(5));
    raffle.setEndDate(raffle.getEndDate().replaceAll("-", "/").substring(5));
    raffle.setStartTime(raffle.getStartTime().substring(0, 5));
    raffle.setEndTime(raffle.getEndTime().substring(0, 5));
    raffle.setContent(null);
    return raffle;
  }

  private Date firstEndDateTime(List<RaffleVO> raffleVOList) throws ParseException {
    if(raffleVOList.isEmpty()){
      return new Date();
    }
    RaffleVO firstRaffle = raffleVOList.get(0);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return sdf.parse(firstRaffle.getEndDate() + " " + firstRaffle.getEndTime());
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

  @Override
  public void addRaffle(RaffleVO raffleVO) throws Exception {
    formatted(raffleVO);
    this.targetRaffleVOList.add(raffleVO);
  }

  @Override
  public void sortRaffleList() {
    Collections.sort(this.targetRaffleVOList);
  }

}
