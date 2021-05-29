package com.sneakalarm.rafflesetting.controller;

import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaffleSettingController {

  @Autowired
  RaffleSettingService raffleSettingService;

  @PutMapping("/raffle-setting")
  public ResponseEntity createRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingService.createRaffleSetting(raffleSetting);
    return new ResponseEntity("sucess", HttpStatus.OK);
  }
}
