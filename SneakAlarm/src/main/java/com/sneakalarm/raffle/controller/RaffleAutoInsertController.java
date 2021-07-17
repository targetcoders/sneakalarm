package com.sneakalarm.raffle.controller;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RaffleAutoInsertController {

  @GetMapping("/raffle-auto-insert")
  public String showRaffleAutoInsertView(@RequestParam(name = "productId") String productId,
      @RequestParam(name = "productName") String productName, Model model) {
    String tempProductName = productName;
    String tempProductId = productId;
    model.addAttribute("productName", tempProductName);
    model.addAttribute("productId", tempProductId);

    return "views/raffle/auto-addition";
  }

  @ResponseBody
  @PostMapping("/raffle-auto-insert/{productId}")
  public ResponseEntity<String> autoInsertRaffle(@RequestParam("url") String url,
      @PathVariable("productId") String productId) throws IOException {
        Document luckyDrawHTML = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
        .referrer("http://www.google.com")
        .get();

        Elements siteCardList = luckyDrawHTML.select("div.site_card");
        for(Element e : siteCardList) {
          System.out.println(e);
        }

    //System.out.println(luckyDrawHTML);
    return new ResponseEntity<>("{\"url\":\""+url+"\"}", HttpStatus.OK);
  }

}
