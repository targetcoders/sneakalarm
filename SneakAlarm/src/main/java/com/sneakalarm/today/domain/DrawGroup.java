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
  private ProductVO productVO;
  private List<RaffleVO> targetRaffleVOList;
  private Date lastEndDateTime;

  public DrawGroup(String productId, String[] deliveryTypes, ProductMapper productMapper, RaffleMapper raffleMapper)
      throws Exception {
    this.productVO = productMapper.getProductList(productId).get(0);
    this.targetRaffleVOList = targetRaffleList(deliveryTypes, raffleMapper);
    Collections.sort(this.targetRaffleVOList);
    this.lastEndDateTime = lastEndDateTime(targetRaffleVOList);
  }

  private List<RaffleVO> targetRaffleList(String[] deliveryTypes, RaffleMapper raffleMapper)
      throws Exception {
    List<RaffleVO> result = new ArrayList<>();
    for(RaffleVO raffle : activeRaffleVOList(productVO.getId(), raffleMapper)) {
      for (String delivery : deliveryTypes) {
        if(raffle.getDelivery().equals(delivery)){
          raffle.setStartWeek(new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getStartDate())).get());
          raffle.setEndWeek(new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffle.getEndDate())).get());
          raffle.setStartDate(raffle.getStartDate().replaceAll("-","/").substring(5));
          raffle.setEndDate(raffle.getEndDate().replaceAll("-","/").substring(5));
          result.add(raffle);
        }
      }
    }
    return result;
  }

  private Date lastEndDateTime(List<RaffleVO> raffleVOList) throws ParseException {
    RaffleVO lastRaffle = raffleVOList.get(raffleVOList.size()-1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String nowYear = sdf.format(new Date()).substring(0,4);
    return sdf.parse(nowYear+"/"+lastRaffle.getEndDate()+" "+lastRaffle.getEndTime());
  }

  private List<RaffleVO> activeRaffleVOList(String productId, RaffleMapper raffleMapper) {
    List<RaffleVO> result = new ArrayList<>();
    result.addAll(raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "active")));
    result.addAll(raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "ready")));
    return result;
  }

  @Override
  public int compareTo(DrawGroup o) {
    return this.getLastEndDateTime().compareTo(o.getLastEndDateTime());
  }

}
