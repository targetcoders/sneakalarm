package com.sneakalarm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
  @RequestMapping("/comingsoon")
  public String home(Model model) {
    return "views/comingsoon";
  }

  @RequestMapping("/")
  public String index() {
    return "views/comingsoon";
  }
}
