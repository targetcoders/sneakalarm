package com.sneakalarm.raffle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaffleController {

  @GetMapping("raffle-insert")
  public String raffleInsert() {
    return "views/raffle-insert";
  }

}
