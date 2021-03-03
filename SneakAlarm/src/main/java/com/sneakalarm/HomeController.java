package com.sneakalarm;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
  @GetMapping("/comingsoon")
  public String home() {
    return "views/comingsoon";
  }
  
  @RequestMapping(value = "/ads.txt")
  @ResponseBody
  public String adstxt(HttpServletResponse response) {
       String fileName = "ads.txt";
       response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
       String content = "google.com, pub-2682527241610745, DIRECT, f08c47fec0942fa0";
       return content;
  }
}
