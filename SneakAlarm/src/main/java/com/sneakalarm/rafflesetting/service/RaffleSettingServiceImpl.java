package com.sneakalarm.rafflesetting.service;

import com.sneakalarm.rafflesetting.dao.RaffleSettingMapper;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import org.springframework.beans.factory.annotation.Autowired;

public class RaffleSettingServiceImpl implements RaffleSettingService {

  @Autowired
  RaffleSettingMapper raffleSettingMapper;

  @Override
  public void createRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingMapper.createRaffleSetting(raffleSetting);
  }
}
