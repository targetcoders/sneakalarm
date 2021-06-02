package com.sneakalarm.rafflesetting.service;

import com.sneakalarm.rafflesetting.dao.RaffleSettingMapper;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaffleSettingServiceImpl implements RaffleSettingService {

  @Autowired
  RaffleSettingMapper raffleSettingMapper;

  @Override
  public void createRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingMapper.createRaffleSetting(raffleSetting);
  }

  @Override
  public RaffleSetting getRaffleSetting(Long id) {
    return raffleSettingMapper.getRaffleSetting(id);
  }

  @Override
  public List<RaffleSetting> getRaffleSettingAll() {
    return raffleSettingMapper.getRaffleSettingAll();
  }

  @Override
  public void updateRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingMapper.updateRaffleSetting(raffleSetting);
  }

  @Override
  public void deleteRaffleSetting(Long id) {
    raffleSettingMapper.deleteRaffleSetting(id);
  }
}
