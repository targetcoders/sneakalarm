package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class InstaFeed {
  private DateTime datetime;
  private RaffleVO raffleVO;
  private String text;
  private List<String> tagList;

  public InstaFeed(RaffleVO raffleVO, DateTime datetime){
    this.raffleVO = raffleVO;
    this.datetime = datetime;
    this.tagList = new ArrayList<>();
  }

  public String getText() throws ParseException {
    if(this.text != null) {
      return text + getCommonTags();
    }
    initTagList();
    return text = generateFeedText() + getCommonTags();
  }

  private String generateFeedText() throws ParseException {
    return "⏰"+raffleVO.getStoreName()+" "+raffleVO.getRaffleType()+": ~ "
        + textForTodayOrTomorrow(raffleVO.getEndDate())+" "+raffleVO.getEndTime()
        + "\n" + raffleVO.getModel_kr()
        + "\n\n" + announceText()
        + "\n\n\n" + specialCaseText(raffleVO.getSpecialCase())
        + "\n\n\n" + commonText();
  }

  private String specialCaseText(String specialCase) {
    if(specialCase == null)
      return "";

    return "✅ 주의 사항 ✅"
        +"\n" + specialCase;
  }

  private String announceText(){
    String result = raffleVO.getStoreName()
        + "에서 "+ raffleVO.getRaffleType() +" 진행합니다!!\uD83D\uDD25\n"
        + "기회를 놓치지 마세요!! \uD83D\uDD25";

    if(raffleVO.getRaffleType().equals("응모")) {
      result += "\n여러분의 당첨을 기원합니다.\uD83E\uDD17";
    }

    return result;
  }

  private String commonText() {
    return
        "[편하게 응모하는 방법1]\n"
        + "1. @sneakalarm 프로필로 이동\n"
        + "2. 홈페이지 주소를 클릭\uD83D\uDE0D\n"
        + "3. 참여할 응모 카드를 클릭"
        + "\n\n"
        + "[편하게 응모하는 방법2]\n"
        + "1. 브라우저 실행\n"
        + "2. 주소창에 sneakalarm.com 입력\n"
        + "3. 참여할 응모 카드를 클릭"
        + "\n\n"
        + "\" 게시물 \uD83D\uDECE알람 설정하고 한정판 신발 겟하세요! \""
        + "\n\n"
        + "스알님들의 좋아요+댓글은 큰 힘이 됩니다\uD83D\uDC4D\uD83C\uDFFB"
        + "\n"
        + ".\n"
        + ".\n"
        + ".\n"
        + ".\n";
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

  private void initTagList(){
    tagList.add("#나이키매니아");
    tagList.add("#나매");
    tagList.add("#나매인");
    tagList.add("#스니커");
    tagList.add("#스니커헤드");
    tagList.add("#오뭐신");
    tagList.add("#sneakalrm");
    tagList.add("#스알");
    tagList.add("#응모맛집");
    tagList.add("#스니커응모");
  }

  private String getCommonTags(){
    Collections.shuffle(tagList);
    return String.join(" ",tagList);
  }
}
