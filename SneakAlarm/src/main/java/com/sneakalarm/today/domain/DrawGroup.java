package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DrawGroup {
  private ProductVO productVO;
  private List<RaffleVO> raffleVOList;
  private Date lastEndDateTime;

  public DrawGroup(String productId, ProductMapper productMapper, RaffleMapper raffleMapper)
      throws ParseException {
    this.productVO = productMapper.getProductList(productId).get(0);
    this.raffleVOList = activeRaffleVOList(productId, raffleMapper);
    this.lastEndDateTime = lastEndDateTime(raffleVOList);
  }

  private Date lastEndDateTime(List<RaffleVO> raffleVOList) throws ParseException {
    Collections.sort(raffleVOList);
    RaffleVO lastRaffle = raffleVOList.get(raffleVOList.size()-1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return sdf.parse(lastRaffle.getEndDate()+" "+lastRaffle.getEndTime());
  }

  private List<RaffleVO> activeRaffleVOList(String productId, RaffleMapper raffleMapper){
    List<RaffleVO> result = new ArrayList<>();
    result.addAll(raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "active")));
    result.addAll(raffleMapper.getRaffleListByStatus(new RaffleListByStatusVO(productId, "ready")));
    return result;
  }
}
