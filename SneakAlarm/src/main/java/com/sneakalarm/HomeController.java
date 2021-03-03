package com.sneakalarm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/comingsoon")
  public String home() {
    return "views/comingsoon";
  }
  @GetMapping("/ads.txt")
  public String adsTxt() {
	  return "google.com, pub-2682527241610745, DIRECT, f08c47fec0942fa0";
  }
}
