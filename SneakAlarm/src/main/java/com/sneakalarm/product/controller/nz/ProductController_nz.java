package com.sneakalarm.product.controller.nz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController_nz {

  @GetMapping("/nz/launch")
  public String launchPage_nz() {
    return "views/nz/home";
  }
}
