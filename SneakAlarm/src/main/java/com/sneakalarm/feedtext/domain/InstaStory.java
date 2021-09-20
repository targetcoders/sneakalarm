package com.sneakalarm.feedtext.domain;

import com.sneakalarm.raffle.dto.RaffleVO;

public class InstaStory {
  private RaffleVO raffleVO;

  public InstaStory(RaffleVO raffleVO) {
    this.raffleVO = raffleVO;
  }

  public String makeText(){
    String specialCase = specialCaseText();
    if(raffleVO.getRaffleType().equals("응모")) {
      return raffleStoryText(specialCase);
    }
    return firstcomeStoryText(specialCase);
  }

  private String specialCaseText() {
    String result = "온라인 구매";
    if (raffleVO.getCountry().equals("한국") && raffleVO.getDelivery().equals("방문수령")) {
      result = "방문 구매";
    }
    if(!raffleVO.getSpecialCase().isEmpty()) {
      result += ", "+raffleVO.getSpecialCase();
    }
    return result;
  }

  private String firstcomeStoryText(String specialCase) {
    return raffleVO.getStoreName() + "\n"
        + "선착 (" + raffleVO.getEndWeek() + ") " + raffleVO.getEndTime() + " 시작\n"
        + "*" + specialCase + "\n\n"
        + "↗선착 구매하기↖";
  }

  private String raffleStoryText(String specialCase) {
    return raffleVO.getStoreName() + "\n"
        + "(" + raffleVO.getEndWeek() + ") " + raffleVO.getEndTime() + " 종료\n"
        + "*" + specialCase + "\n\n"
        + "↗응모하러 가기↖";
  }

}
