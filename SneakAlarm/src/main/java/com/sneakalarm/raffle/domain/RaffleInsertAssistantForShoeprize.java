package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.ParsedElementForShoeprize;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RaffleInsertAssistantForShoeprize extends RaffleInsertAssistant{

  public RaffleInsertAssistantForShoeprize(String model_kr, String storeName, String productId) {
    super(model_kr, storeName, productId);
  }

  @Override
  public List<RaffleVO> insertParsedRaffles(RaffleSettingService raffleSettingService,
      Elements targetStoreElements) throws Exception {
    List<RaffleVO> raffleVOList = new ArrayList<>();

    for(Element e : targetStoreElements) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      JSONParser jsonParser = new JSONParser();
      String dataUUID = e.select(".btn_area").select("button").get(0).attr("data-uuid");
      Document doc = new JsoupImplForShoeprize().productDetail(dataUUID);

      String raffleType = e.select(".btn_area button").get(0).text();
      JSONObject jsonObj = (JSONObject) jsonParser.parse(doc.text().substring(doc.text().indexOf('{')));
      String raffleUrl = jsonObj.get("url").toString();
      String startDateTime;
      if(jsonObj.get("startTimestamp") == null){
        startDateTime = new ParsedElementForShoeprize(e).parseStartDateTime();
      } else {
        startDateTime = oClockDateTime(sdf, jsonObj.get("startTimestamp").toString());
      }

      String endDateTime = oClockDateTime(sdf, jsonObj.get("closedAt").toString());
      if(raffleType.equals("선착순")){
        //선착순은 종료시간과 시작시간이 같으면 안 되므로 +30분
        long closedAt = Long.parseLong(jsonObj.get("closedAt").toString()) + (1000L*30*60);
        endDateTime = oClockDateTime(sdf, Long.toString(closedAt));
      }
      String delivery = jsonObj.get("shippingMethod").toString();
      String releasePrice = ((getModel_kr() == null) || getModel_kr().isEmpty()) ? "" : "미등록 제품";

      List<RaffleSetting> raffleSettingList = raffleSettingService
          .getRaffleSettingByKeyword("[" + usingDeliveryFor(delivery) + "]" + getStoreName());

      if (raffleSettingList.isEmpty()) {
        return new ArrayList<>();
      }

      RaffleSetting raffleSetting = raffleSettingList.get(0);

      String specialCase = raffleSetting.getSpecialCase();
      if (specialCase != null && !specialCase.isEmpty()) {
        specialCase = ", " + specialCase;
      }
      RaffleVO raffleVO = RaffleVO.builder()
          .productId(getProductId())
          .storeName(raffleSetting.getStoreName())
          .url(raffleUrl)
          .raffleType(raffleType)
          .country(raffleSetting.getCountry())
          .imgSrc(raffleSetting.getImgSrc())
          .delivery(raffleSetting.getDelivery())
          .content(raffleSetting.getContent())
          .specialCase(usingDeliveryFor(delivery) + specialCase)
          .releasePrice(raffleSetting.getReleasePrice())
          .payType(raffleSetting.getPayType())
          .startDate(startDateTime.split(" ")[0])
          .startTime(startDateTime.split(" ")[1])
          .endDate(endDateTime.split(" ")[0])
          .endTime(endDateTime.split(" ")[1])
          .model_kr(getModel_kr())
          .releasePrice(releasePrice)
          .build();
      raffleVOList.add(raffleVO);

    }
    for(RaffleVO raffleVO : raffleVOList) {
      String raffleId = raffleSettingService.insertRaffle(raffleVO);
      raffleVOList.get(0).setId(raffleId);
    }
    return raffleVOList;
  }
  private String usingDeliveryFor(String delivery) {
    if(delivery == null || delivery.equals("배대지") || delivery.equals("직배") || delivery.contains("국내")){
      return "온라인 구매";
    }
    return "방문 구매";
  }
  private String oClockDateTime(SimpleDateFormat sdf, String dateTime) {
    if(dateTime == null){
      return "";
    }
    if(dateTime.equals("0")) {
      return sdf.format(new DateTimeImpl().getDate()).split(" ")[0] + " 00:00";
    }

    String result = sdf.format(Long.parseLong(dateTime));
    String[] splitResult = result.split(" ");
    String[] time = splitResult[1].split(":");
    if(time[1].equals("58") || time[1].equals("59")){
      int min = 59 - Integer.parseInt(time[1]);
      result = sdf.format(Long.parseLong(dateTime)+(60L*min));
    }
    return result;
  }
}
