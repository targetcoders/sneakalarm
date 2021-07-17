package com.sneakalarm.raffle.controller;

import com.sneakalarm.raffle.domain.Jsoup;
import com.sneakalarm.raffle.domain.JsoupImpl;
import com.sneakalarm.raffle.domain.SiteCardParser;
import java.io.IOException;
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
      @RequestParam("storeName") String storeName, @PathVariable("productId") String productId)
      throws IOException {

    Jsoup jsoup = new JsoupImpl();
    SiteCardParser siteCardParser = new SiteCardParser(url, jsoup);
    Elements elements = siteCardParser.getActiveSiteCards(storeName);
    for(Element e : elements){
      String country = siteCardParser.getCountry(e);
      String delivery = siteCardParser.getDelivery(e);

      System.out.println("country: "+country);
      System.out.println("delivery: "+delivery);

      String aText = e.getElementsByTag("a").get(0).attr("href");
      System.out.println("aText: "+aText);
    }
    System.out.println(siteCardParser.getActiveSiteCards(storeName));
    return new ResponseEntity<>("{\"url\":\""+url+"\"}", HttpStatus.OK);
  }

}
