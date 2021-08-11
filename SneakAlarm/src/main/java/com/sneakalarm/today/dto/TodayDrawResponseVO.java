package com.sneakalarm.today.dto;

import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.Week;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;

@Data
public class TodayDrawResponseVO {

  private String productId;
  private ArrayList<TodayDrawVO> todayDraws;

  public void setTodayDraws(ArrayList<RaffleVO> raffleList) throws Exception {
    for (RaffleVO raffleVO : raffleList) {
      TodayDrawVO todayDraw = getTodayDraw(raffleVO);
      todayDraws.add(todayDraw);
    }
  }

  private TodayDrawVO getTodayDraw(RaffleVO raffleVO) throws Exception {
    String startWeek = new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffleVO.getStartDate())).get();
    String endWeek = new Week(new SimpleDateFormat("yyyy-MM-dd").parse(raffleVO.getEndDate())).get();
    String specialCase = raffleVO.getSpecialCase();
    String model_kr = raffleVO.getModel_kr();
    String releasePrice = raffleVO.getReleasePrice();

    return new TodayDrawVO(raffleVO.getId(), raffleVO.getUrl(), raffleVO.getStoreName(),
        raffleVO.getRaffleType(), raffleVO.getStartDate(), raffleVO.getStartTime(),
        raffleVO.getEndDate(), raffleVO.getEndTime(), raffleVO.getDelivery(), raffleVO.getStatus(),startWeek,endWeek,specialCase,model_kr,releasePrice);
  }

  public TodayDrawResponseVO() {
    this.productId = "";
    this.todayDraws = new ArrayList<>();
  }
}
