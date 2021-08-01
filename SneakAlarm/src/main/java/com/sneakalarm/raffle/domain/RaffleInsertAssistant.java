package com.sneakalarm.raffle.domain;

import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.ParseException;
import java.util.List;
import lombok.Data;
import org.jsoup.select.Elements;

@Data
public abstract class RaffleInsertAssistant {
  private String model_kr;
  private String storeName;
  private String productId;

  public RaffleInsertAssistant(String model_kr, String storeName,
      String productId) {
    this.model_kr = model_kr;
    this.storeName = storeName;
    this.productId = productId;
  }

  public abstract List<RaffleVO> insertParsedRaffles(RaffleSettingService raffleSettingService,
      Elements targetStoreElements) throws Exception;

}
