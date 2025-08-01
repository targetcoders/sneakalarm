package com.sneakalarm.rafflesetting.service;

import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import java.util.List;

public interface RaffleSettingService {

  void createRaffleSetting(RaffleSetting raffleSetting);

  RaffleSetting getRaffleSetting(Long id);

  List<RaffleSetting> getRaffleSettingAll();

  void updateRaffleSetting(RaffleSetting raffleSetting);

  void deleteRaffleSetting(Long id);

  List<RaffleSetting> getRaffleSettingByKeyword(String keyword);

  List<RaffleSetting> getRaffleSettingByStoreName(String storeName);

  String insertRaffle(RaffleVO raffleVO);
}
