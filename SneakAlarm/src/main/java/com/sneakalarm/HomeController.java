package com.sneakalarm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String comingsoon(Model model) {
    return "views/comingsoon";
  }

}
