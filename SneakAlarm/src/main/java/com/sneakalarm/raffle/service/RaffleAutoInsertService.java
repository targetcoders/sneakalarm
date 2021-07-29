package com.sneakalarm.raffle.service;

import com.sneakalarm.raffle.dto.RaffleVO;
import java.util.List;

public interface RaffleAutoInsertService {
  List<RaffleVO> raffleAutoInsertForLuckD(String targetUrl, String storeName, String productId,
      String model_kr)
      throws Exception;
}
