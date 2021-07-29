package com.sneakalarm.raffle.service;

import com.sneakalarm.raffle.domain.DateTime;
import com.sneakalarm.raffle.domain.DateTimeImpl;
import com.sneakalarm.raffle.domain.Jsoup;
import com.sneakalarm.raffle.domain.JsoupImpl;
import com.sneakalarm.raffle.domain.SiteCardParser;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaffleAutoInsertServiceImpl implements RaffleAutoInsertService{

  @Autowired
  RaffleSettingService raffleSettingService;


  @Override
  public List<RaffleVO> raffleAutoInsertForLuckD(String targetUrl, String storeName, String productId,
      String model_kr)
      throws Exception {
    Jsoup jsoup = new JsoupImpl();
    SiteCardParser siteCardParser = new SiteCardParser(targetUrl, jsoup);
    Elements elements = siteCardParser.getActiveSiteCards(storeName);
    List<RaffleVO> raffleVOList = new ArrayList<>();
    for(Element e : elements){
      String country = siteCardParser.getCountry(e);
      String delivery = siteCardParser.getDelivery(e);
      String raffleUrl = siteCardParser.getRaffleUrl(e);
      String raffleEndDateTime = siteCardParser.getRaffleEndDateTime(e);
      String releasePrice = ((model_kr==null) || model_kr.isEmpty()) ? "" : "미등록 제품";

      List<RaffleSetting> raffleSettingList = raffleSettingService
          .getRaffleSettingByKeyword("["+usingDeliveryFor(delivery)+"]"+storeName);

      if(raffleSettingList.isEmpty()){
        return new ArrayList<>();
      }

      RaffleSetting raffleSetting = raffleSettingList.get(0);
      DateTime dateTime = new DateTimeImpl();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
      String specialCase =raffleSetting.getSpecialCase();
      if(specialCase != null && !specialCase.isEmpty()){
        specialCase = ", " + specialCase;
      }

      RaffleVO raffleVO = RaffleVO.builder()
          .productId(productId)
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
          .startTime(sdf.format(dateTime.getDate()).split(" ")[1])
          .endDate(sdf.format(dateTime.getDate()).substring(0,4) + "-" + raffleEndDateTime.split(" ")[0])
          .endTime(raffleEndDateTime.split(" ")[1])
          .model_kr(model_kr)
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
