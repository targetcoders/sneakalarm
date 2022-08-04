package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.parameters.P;

public interface Group {

  final class Fake implements Group {
    public final static String STATUS_ACTIVE = "active";
    public final static String STATUS_READY = "ready";

    private ProductVO productVO;
    private List<RaffleVO> targetRaffleVOList;
    private Date firstEndDateTime;

    public Fake(String productId, String[] deliveryTypes, String status, ProductMapper productMapper, RaffleMapper raffleMapper)
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
      raffleVO1.setEndDate("2021-09-01");
      raffleVO1.setEndTime("10:30");
      RaffleVO raffleVO2 = new RaffleVO();
      raffleVO2.setProductId("200");
      raffleVO2.setId("2");
      raffleVO2.setDelivery("택배배송");
      raffleVO2.setStatus(STATUS_ACTIVE);
      raffleVO2.setEndDate("2021-09-01");
      raffleVO2.setEndTime("09:30");
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
      this.firstEndDateTime = sdf.parse("2021/09/01 09:30");
    }

    public Fake(RaffleVO raffleVO, ProductMapper productMapper) throws ParseException {
      ProductVO productVO = new ProductVO();
      productVO.setId(raffleVO.getProductId());
      this.productVO = productVO;
      this.targetRaffleVOList = new ArrayList<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      this.firstEndDateTime = sdf.parse("2021/09/01 09:30");
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
    public void addRaffle(RaffleVO raffleVO) {
      this.targetRaffleVOList.add(raffleVO);
    }

    @Override
    public void sortRaffleList() {
      Collections.sort(this.targetRaffleVOList);
    }
  }

  void addRaffle(RaffleVO raffleVO) throws Exception;
  void sortRaffleList();
}
