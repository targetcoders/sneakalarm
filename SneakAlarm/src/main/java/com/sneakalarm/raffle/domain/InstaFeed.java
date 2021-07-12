package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InstaFeed {
  private DateTime datetime;
  private RaffleVO raffleVO;
  private String text;

  public InstaFeed(RaffleVO raffleVO, DateTime datetime){
    this.raffleVO = raffleVO;
    this.datetime = datetime;
  }

  public String getText() throws ParseException {
    if(this.text != null)
      return text;
    return text = generateFeedText();
  }

  private String generateFeedText() throws ParseException {
    return "⏰"+raffleVO.getStoreName()+" "+raffleVO.getRaffleType()+": ~ "
        + textForTodayOrTomorrow(raffleVO.getEndDate())+" "+raffleVO.getEndTime()+"\n"+raffleVO.getModel_kr()+commonText();
  }

  private String commonText() {
    return "\n\n@sneakalarm.com⬅응모 링크"
        + "\n\n"
        + "\" 게시물 \uD83D\uDECE알람 설정하고 한정판 신발 겟하세요! \"\n"
        + "\uD83D\uDCC8시세:　@sneakalarm_justdidit\n"
        + "\uD83D\uDEA8뉴스:　@sneakalarm_news\n"
        + "\n"
        + "스알님들의 좋아요+댓글은 큰 도움이 됩니다\uD83D\uDC4D\uD83C\uDFFB\n"
        + "\n"
        + ".\n"
        + ".\n"
        + ".\n"
        + ".\n"
        + "#응모하기 #스니커 #나이키매니아 #덩크로우 #조던 #나이키조던 #나이키조던1 #덩크하이 #나이키덩크하이 #조던1 #응모알림 #스알 #스니커응모";
  }

  private String textForTodayOrTomorrow(String endDateString) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date endDate = sdf.parse(endDateString);
    Date todayDate = sdf.parse(getTodayDateString(sdf));
    if(computeDiffDay(endDate, todayDate) == 0){
      return "오늘";
    } else if(computeDiffDay(endDate, todayDate) == 1) {
      return "내일";
    }
    return endDateString.substring(5).replace('-','/');
  }

  private String getTodayDateString(SimpleDateFormat sdf) {
    return sdf.format(datetime.getDate());
  }

  private long computeDiffDay(Date endDate, Date todayDate) {
    return Math.abs((todayDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000));
  }
}
