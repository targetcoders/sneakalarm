package com.sneakalarm.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

  @GetMapping("/login")
  public String loginForm() {
    return "views/login";
  }
}
