package com.sneakalarm.rafflesetting.dao;

import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RaffleSettingMapper {

  long createRaffleSetting(RaffleSetting raffleSetting);

  RaffleSetting getRaffleSetting(Long id);

  List<RaffleSetting> getRaffleSettingAll();
}
