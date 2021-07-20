package com.sneakalarm.raffle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleAutoInsertService;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RaffleAutoInsertController {

  @Autowired
  private RaffleAutoInsertService raffleAutoInsertService;

  @GetMapping("/raffle-auto-insert")
  public String showRaffleAutoInsertView(@RequestParam(name = "productId") String productId,
      @RequestParam(name = "productName") String productName, Model model) {
    String tempProductName = productName;
    String tempProductId = productId;
    model.addAttribute("productName", tempProductName);
    model.addAttribute("productId", tempProductId);

    return "views/raffle/auto-addition";
  }

  @ResponseBody
  @PostMapping("/raffle-auto-insert")
  public ResponseEntity<String> autoInsertRaffle(@RequestParam("url") String targetUrl,
      @RequestParam("storeName") String storeName, @RequestParam("model_kr") String model_kr, @RequestParam("productId") String productId) {
    List<RaffleVO> raffleVO;
    try {
      raffleVO= raffleAutoInsertService
          .raffleAutoInsert(targetUrl, storeName, productId, model_kr);
    } catch(Exception e) {
      return new ResponseEntity<>("url을 확인하세요.", HttpStatus.BAD_REQUEST);
    }
    if(raffleVO.isEmpty()){
      String message = "storeName이 정확하게 일치하는지, raffleSetting이 제대로 등록되어 있는지 확인하세요.";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("success", HttpStatus.OK);
  }

}
