package com.sneakalarm.rafflesetting.dao;

import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RaffleSettingMapper {

  void createRaffleSetting(RaffleSetting raffleSetting);
}
