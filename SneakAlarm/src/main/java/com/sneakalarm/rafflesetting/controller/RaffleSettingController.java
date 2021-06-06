package com.sneakalarm.rafflesetting.controller;

import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class RaffleSettingController {

  @Autowired
  RaffleSettingService raffleSettingService;

  @GetMapping("/raffle-setting")
  public String raffleSettingPage() {
    return "views/raffle-setting";
  }

  @GetMapping("/raffle-setting/detail/{id}")
  public String raffleSettingDetailPage(@PathVariable("id") Long id, Model model) {
    model.addAttribute("id",id);
    return "views/raffle-setting-detail";
  }

  @GetMapping("/raffle-setting/addition")
  public String raffleSettingAddition() {
    return "views/raffle-setting/addition";
  }

  @GetMapping("/raffle-setting/modification/{id}")
  public String raffleSettingModification(@PathVariable("id") Long id, Model model) {
    RaffleSetting raffleSetting = raffleSettingService.getRaffleSetting(id);
    model.addAttribute("raffleSetting",raffleSetting);
    return "views/raffle-setting/modification";
  }
}
