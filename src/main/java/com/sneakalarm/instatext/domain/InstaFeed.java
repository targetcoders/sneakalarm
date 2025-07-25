package com.sneakalarm.instatext.domain;

import com.sneakalarm.raffle.domain.DateTime;
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

  public String feedText() throws Exception {
    if(this.text != null) {
      return text;
    }
    initTagList();
    return text = generateFeedText() + getRelatedTags() + "\n\n#스알 #응모";
  }

  private String generateFeedText() throws Exception {
    String raffleType = raffleVO.getRaffleType();
    return "⏰"+raffleVO.getStoreName()+" "+raffleVO.getRaffleType()+": "
        + " "+raffleVO.getEndDate().substring(5).replaceAll("-","/")
        + " "+ raffleVO.getEndWeek()
        + " "+((raffleType.equals("응모")) ? raffleVO.getEndTime().substring(0,5) : raffleVO.getStartTime().substring(0,5))
        + " "+((raffleType.equals("응모")) ? "종료" : "시작")
        + "\n" + raffleVO.getModel_kr()
        + "\n" + specialCaseText(raffleVO.getSpecialCase())
        + "\n\n" + announceText()
        + "\n\n" + commonText();
  }

  private String specialCaseText(String specialCase) {
    if(specialCase == null)
      return "";

    return "✅ " + specialCase;
  }

  private String announceText(){
    String result;
    if(raffleVO.getRaffleType().equals("응모")){
      result = raffleVO.getStoreName()
          + "에서 응모 진행중입니다.\n"
          + "당첨 기회를 놓치지 마세요! \uD83D\uDD25";
    } else {
      result = raffleVO.getStoreName()
          + "에서 선착순 구매를 진행합니다.\n"
          + "기회를 놓치지 마세요! \uD83D\uDD25";
    }
    return result;
  }

  private String commonText() {
    return
        "[응모 참여 방법 1]\n"
        + "1. @sneakalarm 인스타그램 프로필로 이동\n"
        + "2. 홈페이지 주소 클릭\n"
        + "3. 참여할 응모 카드 클릭"
        + "\n\n"
        + "[응모 참여 방법 2]\n"
        + "1. 브라우저 실행\n"
        + "2. 주소창에 sneakalarm.com 입력\n"
        + "3. 참여할 응모 카드 클릭"
        + "\n\n"
        + "\" 게시물 \uD83D\uDECE알람 설정하고 한정판 신발 겟하세요! \""
        + "\n\n"
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
    String model_kr = raffleVO.getModel_kr();
    if(model_kr.contains("나이키")){
      if(model_kr.contains("오프화이트")) {
        tagList.add("#나이키콜라보");
        tagList.add("#나이키오프화이트");
        tagList.add("#오프화이트콜라보");
      }
    }
    if(model_kr.contains("덩크")){
      if(model_kr.contains("하이")){
        tagList.add("#덩크하이");
        tagList.add("#나이키덩크하이");
        if(model_kr.contains("블랙")){
          tagList.add("#덩크하이블랙");
          tagList.add("#덩크하이범고래");
        }
      }
      if(model_kr.contains("로우")){
        tagList.add("#덩크로우");
        tagList.add("#나이키덩크로우");
        if(model_kr.contains("블랙")){
          tagList.add("#덩크로우블랙");
          tagList.add("#덩크로우범고래");
        }
        if(model_kr.contains("코스트")){
          tagList.add("#덩크로우코스트");
        }
      }
      if(model_kr.contains("미드")){
        tagList.add("#덩크미드");
        tagList.add("#나이키덩크미드");
      }
    }
    if(model_kr.contains("조던 1")){
      tagList.add("#조던1");
      if(model_kr.contains("에어")) {
        tagList.add("#에어조던");
      }
      if(model_kr.contains("하이")) {
        tagList.add("#조던1하이");
        tagList.add("#조던하이");
      }
      if(model_kr.contains("미드")) {
        tagList.add("#조던1미드");
      }
      if(model_kr.contains("로우")) {
        tagList.add("#조던1로우");
        tagList.add("#조던로우");
      }
      if(model_kr.contains("트래비스스캇")) {
        tagList.add("#트래비스스캇");
      }
    }
    if(model_kr.contains("아디다스")){
      tagList.add("#아디다스");
      if(model_kr.contains("이지")) {
        tagList.add("#아디다스이지");
      }
    }

    tagList.add("#나이키매니아");
    tagList.add("#나매");
    tagList.add("#나매인");
    tagList.add("#스니커");
    tagList.add("#스니커헤드");
    tagList.add("#오뭐신");
    tagList.add("#신발추천");
    tagList.add("#조던");
    tagList.add("#나이키");
  }

  private String getRelatedTags(){
    Collections.shuffle(tagList);
    if(tagList.size() >= 13)
      return String.join(" ",tagList.subList(0,13));

    return String.join(" ",tagList);
  }
}
