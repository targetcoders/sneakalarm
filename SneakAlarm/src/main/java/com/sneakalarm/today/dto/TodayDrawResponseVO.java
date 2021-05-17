package com.sneakalarm.today.dto;

import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.DateUtil;
import com.sneakalarm.util.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import lombok.Data;

@Data
public class TodayDrawResponseVO {

  private String productId;
  private ArrayList<TodayDrawVO> todayDraws;
  private SortingTodayDraws sortingTodayDRaws = new SortingTodayDraws();

  public void setTodayDraws(ArrayList<RaffleVO> raffleList) throws Exception {
    for (RaffleVO raffleVO : raffleList) {
      TodayDrawVO todayDraw = getTodayDraw(raffleVO);
      todayDraws.add(todayDraw);
    }
  }

  public void sortTodayDraws() {
    todayDraws.sort(sortingTodayDRaws);
  }

  class SortingTodayDraws implements Comparator<TodayDrawVO> {
    @Override
    public int compare(TodayDrawVO o1, TodayDrawVO o2) {
      String endDateTime1 = o1.getEndDate() + " " + o1.getEndTime();
      String endDateTime2 = o2.getEndDate() + " " + o2.getEndTime();

      if (o1.getRaffleType().equals(o2.getRaffleType())) {
        return endDateTime1.compareTo(endDateTime2);
      }
      return o1.getRaffleType().compareTo(o2.getRaffleType());
    }
  }

  private TodayDrawVO getTodayDraw(RaffleVO raffleVO) throws Exception {
    String startWeek = DateUtil.getWeek(raffleVO.getStartDate(), "yyyy-MM-dd");
    String endWeek = DateUtil.getWeek(raffleVO.getEndDate(), "yyyy-MM-dd");
    String specialCase = raffleVO.getSpecialCase();

    return new TodayDrawVO(raffleVO.getId(), raffleVO.getUrl(), raffleVO.getStoreName(),
        raffleVO.getRaffleType(), raffleVO.getStartDate(), raffleVO.getStartTime(),
        raffleVO.getEndDate(), raffleVO.getEndTime(), raffleVO.getDelivery(), raffleVO.getStatus(),startWeek,endWeek,specialCase);
  }

  public TodayDrawResponseVO() {
    this.productId = "";
    this.todayDraws = new ArrayList<>();
  }
}
