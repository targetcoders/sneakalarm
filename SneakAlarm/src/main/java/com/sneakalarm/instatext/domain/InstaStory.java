package com.sneakalarm.instatext.domain;

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
    if(raffleVO.getSpecialCase().isEmpty()) {
      return "\n\n";
    }
    return "*" + raffleVO.getSpecialCase()+ "\n\n";
  }

  private String firstcomeStoryText(String specialCase) {
    return raffleVO.getStoreName() + "\n"
        + "선착 (" + raffleVO.getEndWeek() + ") " + raffleVO.getEndTime() + " 시작\n"
        + specialCase
        + "↗선착 구매하기↖";
  }

  private String raffleStoryText(String specialCase) {
    return raffleVO.getStoreName() + "\n"
        + "(" + raffleVO.getEndWeek() + ") " + raffleVO.getEndTime() + " 종료\n"
        + specialCase
        + "↗응모하러 가기↖";
  }

}
