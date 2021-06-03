package com.sneakalarm.rafflesetting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RaffleSettingController {

  @GetMapping("/raffle-setting")
  public String raffleSettingPage() {
    return "views/raffle-setting";
  }

  @GetMapping("/raffle-setting/detail/{id}")
  public String raffleSettingDetailPage(@PathVariable("id") Long id, Model model) {
    model.addAttribute("id",id);
    return "views/raffle-setting-detail";
  }
}
