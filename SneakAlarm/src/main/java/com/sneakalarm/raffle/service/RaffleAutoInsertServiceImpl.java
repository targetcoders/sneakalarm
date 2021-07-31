package com.sneakalarm.raffle.service;

import com.sneakalarm.raffle.domain.RaffleElementsParserForShoepize;
import com.sneakalarm.raffle.domain.RaffleInsertAssistant;
import com.sneakalarm.raffle.domain.JsoupImpl;
import com.sneakalarm.raffle.domain.RaffleElementsParser;
import com.sneakalarm.raffle.domain.RaffleInsertAssistantForLuckD;
import com.sneakalarm.raffle.domain.RaffleElementsParserForLuckD;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.io.IOException;
import java.util.List;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaffleAutoInsertServiceImpl implements RaffleAutoInsertService{

  @Autowired
  RaffleSettingService raffleSettingService;

  @Override
  public List<RaffleVO> raffleAutoInsert(String targetUrl, String storeName, String productId,
      String model_kr)
      throws Exception {
    RaffleElementsParser raffleElementsParser = getRaffleElementsParser(targetUrl);
    Elements targetStoreElements = raffleElementsParser.getTargetStoreElements(targetUrl, storeName);

    RaffleInsertAssistant insertAssistant = new RaffleInsertAssistantForLuckD(model_kr, storeName, productId);
    return insertAssistant.insertParsedRaffles(raffleSettingService, targetStoreElements);
  }

  private RaffleElementsParser getRaffleElementsParser(String targetUrl) throws IOException {
    RaffleElementsParser raffleElementsParser;
    if(targetUrl.contains("luck-d.com")) {
      raffleElementsParser = new RaffleElementsParserForLuckD(targetUrl, new JsoupImpl());
    } else {
      raffleElementsParser = new RaffleElementsParserForShoepize(targetUrl, new JsoupImpl());
    }
    return raffleElementsParser;
  }

}
