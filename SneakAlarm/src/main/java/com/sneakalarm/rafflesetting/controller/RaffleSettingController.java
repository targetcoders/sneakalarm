package com.sneakalarm.rafflesetting.controller;

import com.google.gson.Gson;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaffleSettingController {

  @Autowired
  RaffleSettingService raffleSettingService;

  @PostMapping("/raffle-setting")
  public ResponseEntity<String> createRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingService.createRaffleSetting(raffleSetting);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @GetMapping("/raffle-settings/{id}")
  public ResponseEntity<String> getRaffleSetting(@PathVariable("id") Long id) {
    RaffleSetting raffleSetting = raffleSettingService.getRaffleSetting(id);
    String raffleSettingJson = new Gson().toJson(raffleSetting);
    return new ResponseEntity<>(raffleSettingJson,HttpStatus.OK);
  }

  @GetMapping("/raffle-settings")
  public ResponseEntity<String> getRaffleSettingAll() {
    List<RaffleSetting> raffleSettingList = raffleSettingService.getRaffleSettingAll();
    String raffleSettingListJson = new Gson().toJson(raffleSettingList);
    return new ResponseEntity<>(raffleSettingListJson,HttpStatus.OK);
  }

  @PutMapping("/raffle-settings")
  public ResponseEntity<String> updateRaffleSetting(RaffleSetting raffleSetting){
    raffleSettingService.updateRaffleSetting(raffleSetting);
    System.out.println(raffleSetting.toString());
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @DeleteMapping("/raffle-settings/{id}")
  public ResponseEntity<String> deleteRaffleSetting(@PathVariable("id") Long id) {
    raffleSettingService.deleteRaffleSetting(id);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}
