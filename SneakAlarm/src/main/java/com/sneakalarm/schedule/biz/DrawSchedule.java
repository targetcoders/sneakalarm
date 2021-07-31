package com.sneakalarm.schedule.biz;

import java.text.ParseException;
import java.util.ArrayList;

import com.sneakalarm.raffle.dto.RaffleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sneakalarm.product.ProductConst;
import com.sneakalarm.raffle.service.RaffleService;

@Component
public class DrawSchedule {

  @Autowired
  RaffleService raffleService;

  public void updateDrawStatus() throws ParseException {
    ArrayList<RaffleVO> list = raffleService.getRaffleListAll();
    for (RaffleVO raffleVO : list) {
      String startDate = raffleVO.getStartDate();
      String endDate = raffleVO.getEndDate();
      String startTime = raffleVO.getStartTime();
      String endTime = raffleVO.getEndTime();
      String status = raffleService.calcRaffleStatus(startDate, startTime, endDate, endTime);
      if ((raffleVO.getRaffleType().equals("선착순") && status.equals(ProductConst.STATUS_READY))
          || status.equals(ProductConst.STATUS_GOING)) {
        status = ProductConst.STATUS_ACTIVE;
      }
      raffleService.updateDrawStatus(raffleVO.getId(), status);
    }
  }

}
