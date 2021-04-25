package com.sneakalarm.today.dto;

import com.sneakalarm.raffle.dto.RaffleVO;
import java.util.ArrayList;
import lombok.Data;

@Data
public class TodayDrawResponseVO {

  private String productId;
  private ArrayList<TodayDrawVO> todayDraws;

  public void setTodayDraws(ArrayList<RaffleVO> raffleList) {
    for (RaffleVO raffleVO : raffleList) {
      TodayDrawVO todayDraw = getTodayDraw(raffleVO);
      todayDraws.add(todayDraw);
    }
  }

  private TodayDrawVO getTodayDraw(RaffleVO raffleVO) {
    return new TodayDrawVO(raffleVO.getUrl(), raffleVO.getStoreName(), raffleVO.getRaffleType(),
        raffleVO.getStartDate(),
        raffleVO.getStartTime(), raffleVO.getEndDate(), raffleVO.getEndTime());
  }

  public TodayDrawResponseVO() {
    this.productId = "";
    this.todayDraws = new ArrayList<>();
  }
}
