package com.sneakalarm.rafflesetting;

import static org.hamcrest.Matchers.is;

import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RaffleSettingTest {

  @Autowired
  RaffleSetting raffleSetting;

  @Test
  public void builder() {
    RaffleSetting raffleSetting = RaffleSetting.builder().raffleSettingName("testName").build();
    Assert.assertThat(raffleSetting.getRaffleSettingName(), is("testName"));
  }

}