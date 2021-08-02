package com.sneakalarm.feedtext.controller;

import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.domain.DateTimeImpl;
import com.sneakalarm.raffle.domain.InstaFeed;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FeedTextController {

  @Autowired
  RaffleService raffleService;
  @Autowired
  ProductService productService;

  @GetMapping("/feed-text/{raffleId}")
  public String getFeedTextPage(@PathVariable("raffleId") String raffleId, Model model)
      throws Exception {
    List<RaffleVO> raffleVOList = raffleService.getRaffleList(raffleId);
    RaffleVO raffleVO = raffleVOList.get(0);
    if(raffleVO.getModel_kr() == null || raffleVO.getModel_kr().isEmpty()) {
      List<ProductVO> productVOList = productService.getProductList(raffleVO.getProductId());
      raffleVO.setModel_kr(productVOList.get(0).getModel_kr());
    }
    InstaFeed instaFeed = new InstaFeed(raffleVOList.get(0), new DateTimeImpl());

    model.addAttribute("feedText",instaFeed.feedText());
    model.addAttribute("raffleVO",raffleVOList.get(0));
    return "views/feed-text/feed-text";
  }

}
