package com.sneakalarm.raffle.service;

import com.sneakalarm.raffle.domain.RaffleInsertAssistant;
import com.sneakalarm.raffle.domain.Jsoup;
import com.sneakalarm.raffle.domain.JsoupImpl;
import com.sneakalarm.raffle.domain.RaffleElementsParser;
import com.sneakalarm.raffle.domain.RaffleInsertAssistantForLuckD;
import com.sneakalarm.raffle.domain.RaffleElementsParserForLuckD;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.util.List;
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
    RaffleElementsParser siteCardElementsParser = new RaffleElementsParserForLuckD(targetUrl, jsoup);
    Elements targetStoreElements = siteCardElementsParser.getTargetStoreElements(targetUrl, storeName);

    RaffleInsertAssistant insertAssistant = new RaffleInsertAssistantForLuckD(model_kr, storeName, productId);
    return insertAssistant.insertParsedRaffles(raffleSettingService, targetStoreElements);
  }
}
