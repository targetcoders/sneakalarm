package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.ParsedElementForLuckD;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RaffleInsertAssistantForShoeprize extends RaffleInsertAssistant{

  public RaffleInsertAssistantForShoeprize(String model_kr, String storeName, String productId) {
    super(model_kr, storeName, productId);
  }

  @Override
  public List<RaffleVO> insertParsedRaffles(RaffleSettingService raffleSettingService,
      Elements targetStoreElements) {
    List<RaffleVO> raffleVOList = new ArrayList<>();
    return raffleVOList;
  }
}
