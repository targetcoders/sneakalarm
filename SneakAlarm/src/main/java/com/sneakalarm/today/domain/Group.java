package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface Group {

  final class Fake implements Group{
    public final static String STATUS_ACTIVE = "active";
    public final static String STATUS_READY = "ready";

    private ProductVO productVO;
    private List<RaffleVO> targetRaffleVOList;
    private Date firstEndDateTime;

    public Fake(String productId, String[] deliveryTypes, String status)
        throws ParseException {
      ProductVO productVO = new ProductVO();
      productVO.setId(productId);
      this.productVO = productVO;

      ArrayList<RaffleVO> raffleVOArrayList = new ArrayList<>();
      RaffleVO raffleVO1 = new RaffleVO();
      raffleVO1.setId("1");
      raffleVO1.setProductId("100");
      raffleVO1.setDelivery("방문수령");
      raffleVO1.setStatus(STATUS_ACTIVE);
      RaffleVO raffleVO2 = new RaffleVO();
      raffleVO2.setProductId("200");
      raffleVO2.setId("2");
      raffleVO2.setDelivery("택배배송");
      raffleVO2.setStatus(STATUS_ACTIVE);
      raffleVOArrayList.add(raffleVO1);
      raffleVOArrayList.add(raffleVO2);

      this.targetRaffleVOList = new ArrayList<>();
      for (RaffleVO raffleVO : raffleVOArrayList) {
        for(String delivery : deliveryTypes) {
          if (raffleVO.getDelivery().equals(delivery) && raffleVO.getProductId()
              .equals(productId) && raffleVO.getStatus().equals(status)) {
            targetRaffleVOList.add(raffleVO);
          }
        }
      }

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      this.firstEndDateTime = sdf.parse("2021/09/01 09:00");
    }

    public ProductVO getProductVO(){
      return this.productVO;
    }
    public List<RaffleVO> getTargetRaffleVOList() {
      return this.targetRaffleVOList;
    }
    public Date getFirstEndDateTime() {
      return this.firstEndDateTime;
    }
  }
}
