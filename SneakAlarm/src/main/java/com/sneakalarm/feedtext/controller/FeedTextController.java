package com.sneakalarm.feedtext.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FeedTextController {

  @GetMapping("/feed-text/{raffleId}")
  public String getFeedTextPage(@PathVariable("raffleId") String raffleId) {
    return "views/feed-text/feed-text";
  }

}
