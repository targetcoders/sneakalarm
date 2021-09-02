package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.ParsedElement;
import com.sneakalarm.raffle.dto.ParsedElementForLuckD;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RaffleInsertAssistantForLuckD extends RaffleInsertAssistant{

  public RaffleInsertAssistantForLuckD(String model_kr, String storeName, String productId) {
    super(model_kr, storeName, productId);
  }

  @Override
  public List<RaffleVO> insertParsedRaffles(RaffleSettingService raffleSettingService,
      Elements targetStoreElements) throws Exception {
    List<RaffleVO> raffleVOList = new ArrayList<>();
    for(Element e : targetStoreElements){
      ParsedElement elementForLuckD = new ParsedElementForLuckD(e);
      String delivery = elementForLuckD.parseDelivery();
      String raffleUrl = elementForLuckD.parseRaffleUrl();
      String raffleEndDateTime = elementForLuckD.parseEndDateTime();
      String releasePrice = ((getModel_kr()==null) || getModel_kr().isEmpty()) ? "" : "미등록 제품";

      String keyword = "["+usingDeliveryFor(delivery)+"]"+getStoreName();
      keyword = keyword.replaceAll(" ","").toUpperCase();
      List<RaffleSetting> raffleSettingList = raffleSettingService
          .getRaffleSettingByKeyword(keyword);
      if(raffleSettingList.isEmpty()){
        return new ArrayList<>();
      }
      RaffleSetting raffleSetting = raffleSettingList.get(0);
      DateTime dateTime = new DateTimeImpl();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String specialCase =raffleSetting.getSpecialCase();
      if(specialCase != null && !specialCase.isEmpty()){
        specialCase = ", " + specialCase;
      }

      RaffleVO raffleVO = RaffleVO.builder()
          .productId(getProductId())
          .storeName(raffleSetting.getStoreName())
          .url(raffleUrl)
          .raffleType(raffleSetting.getRaffleType())
          .country(raffleSetting.getCountry())
          .imgSrc(raffleSetting.getImgSrc())
          .delivery(raffleSetting.getDelivery())
          .content(raffleSetting.getContent())
          .specialCase(usingDeliveryFor(delivery) + specialCase)
          .releasePrice(raffleSetting.getReleasePrice())
          .payType(raffleSetting.getPayType())
          .startDate(sdf.format(dateTime.getDate()).split(" ")[0])
          .startTime("00:00")
          .endDate(sdf.format(dateTime.getDate()).substring(0,4) + "-" + raffleEndDateTime.split(" ")[0])
          .endTime(raffleEndDateTime.split(" ")[1])
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
    if(delivery == null || delivery.equals("배대지") || delivery.equals("직배")){
      return "온라인 구매";
    }
    return "방문 구매";
  }
}
