package com.sneakalarm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/comingsoon")
  public String home() {
    return "views/comingsoon";
  }
}
