package com.sneakalarm.rafflesetting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaffleSettingController {

  @GetMapping("raffle-setting")
  public String raffleSettingPage() {
    return "views/raffle-setting";
  }
}
