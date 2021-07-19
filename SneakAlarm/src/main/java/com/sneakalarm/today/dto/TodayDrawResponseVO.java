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
import java.util.Date;
import lombok.Data;
import lombok.SneakyThrows;

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
    String startWeek = DateUtil.getWeek(raffleVO.getStartDate(), "yyyy-MM-dd");
    String endWeek = DateUtil.getWeek(raffleVO.getEndDate(), "yyyy-MM-dd");
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
