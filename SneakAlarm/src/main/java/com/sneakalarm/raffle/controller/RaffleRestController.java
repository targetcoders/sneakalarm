package com.sneakalarm.raffle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneakalarm.raffle.dto.ActiveRafflesVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaffleRestController {

  @Autowired
  RaffleService raffleService;
  @Autowired
  RaffleSettingService raffleSettingService;

  @GetMapping("/raffles/{productId}/{storeName}")
  @ResponseBody
  public ResponseEntity<String> activeRaffles(@PathVariable("productId") String productId,
      @PathVariable("storeName") String storeName) throws JsonProcessingException {
    System.out.println(productId);
    System.out.println(storeName);
    List<RaffleSetting> raffleSettingList = raffleSettingService
        .getRaffleSettingByKeyword(storeName);
    if (raffleSettingList.isEmpty()) {
      return new ResponseEntity<>(new ObjectMapper().writeValueAsString(new ArrayList<>()),
          HttpStatus.OK);
    }
    List<RaffleVO> raffleVOList = raffleService
        .activeRaffles(new ActiveRafflesVO(productId, raffleSettingList.get(0).getStoreName()));
    return new ResponseEntity<>(new ObjectMapper().writeValueAsString(raffleVOList),
        HttpStatus.OK);

  }
}
