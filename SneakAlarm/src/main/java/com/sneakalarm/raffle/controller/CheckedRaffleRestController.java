package com.sneakalarm.raffle.controller;

import javax.xml.ws.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckedRaffleRestController {

  @GetMapping("/raffles/checked")
  ResponseEntity<String> checkedRaffleList() {

    return new ResponseEntity("Success", HttpStatus.OK);
  }

}
