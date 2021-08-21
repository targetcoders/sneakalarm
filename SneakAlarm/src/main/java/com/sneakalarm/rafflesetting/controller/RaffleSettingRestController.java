package com.sneakalarm.rafflesetting.controller;

import com.google.gson.Gson;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.domain.DateTimeImpl;
import com.sneakalarm.raffle.domain.InstaFeed;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.text.ParseException;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaffleSettingRestController {

  @Autowired
  RaffleSettingService raffleSettingService;
  @Autowired
  RaffleMapper raffleMapper;

  @PostMapping("/raffle-settings")
  public ResponseEntity<String> createRaffleSetting(RaffleSetting raffleSetting) {
    raffleSettingService.createRaffleSetting(raffleSetting);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @GetMapping("/raffle-settings/{id}")
  public ResponseEntity<String> getRaffleSetting(@PathVariable("id") Long id) {
    RaffleSetting raffleSetting = raffleSettingService.getRaffleSetting(id);
    String jsonString = raffleSetting.getJsonString();
    return new ResponseEntity<>(jsonString,HttpStatus.OK);
  }

  @GetMapping("/raffle-settings")
  public ResponseEntity<String> getRaffleSettingAll() {
    List<RaffleSetting> raffleSettingList = raffleSettingService.getRaffleSettingAll();
    String raffleSettingListJson = new Gson().toJson(raffleSettingList);
    return new ResponseEntity<>(raffleSettingListJson,HttpStatus.OK);
  }

  @GetMapping("/raffle-settings/keyword/{keyword}")
  public ResponseEntity<String> getRaffleSettingByKeyword(@PathVariable(value="keyword") String keyword) {
    //System.out.println("keyword : "+keyword);
    List<RaffleSetting> raffleSettingList = raffleSettingService.getRaffleSettingByKeyword(keyword);
    String raffleSettingListJson = new Gson().toJson(raffleSettingList);
    return new ResponseEntity<>(raffleSettingListJson,HttpStatus.OK);
  }

  @PutMapping("/raffle-settings")
  public ResponseEntity<String> updateRaffleSetting(RaffleSetting raffleSetting){
    raffleSettingService.updateRaffleSetting(raffleSetting);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @DeleteMapping("/raffle-settings/{id}")
  public ResponseEntity<String> deleteRaffleSetting(@PathVariable("id") Long id) {
    raffleSettingService.deleteRaffleSetting(id);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }


  @PostMapping("/raffles/addition/{productId}")
  public ResponseEntity<String> insertRaffle(@PathVariable("productId") String productId,
      RaffleVO raffleVO) {
    raffleVO.setProductId(productId);
    return new ResponseEntity<>(raffleSettingService.insertRaffle(raffleVO), HttpStatus.OK);
  }
}
