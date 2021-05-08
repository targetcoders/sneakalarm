package com.sneakalarm.today.controller;

import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.today.dto.TodayDrawResponseVO;
import com.sneakalarm.today.dto.TodayGetProductByDeliveryTypeVO;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import com.sneakalarm.raffle.service.RaffleService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodayController {

  @Autowired
  ProductService productService;
  @Autowired
  RaffleService raffleService;

  @GetMapping("/today/productList")
  public ArrayList<TodayProductResponseVO> getProductList(){
    return productService.getTodayProductResponseVO();
  }

  @GetMapping("/today/drawList")
  public ArrayList<TodayDrawResponseVO> getTodayKorea(
      @RequestParam("deliveryType") String deliveryType) {
    ArrayList<TodayDrawResponseVO> ret = new ArrayList<>();
    String[] deliveryList = deliveryType.split(",");
    for (String delivery : deliveryList) {
      ArrayList<ProductVO> productList = productService.getProductByDeliveryType(delivery);
      for (ProductVO productVO : productList) {
        ret.add(getTodayDrawResponseVO(delivery, productVO));
      }
    }
    return ret;
  }

  private TodayDrawResponseVO getTodayDrawResponseVO(String deliveryType, ProductVO productVO) {
    TodayDrawResponseVO todayDrawResponseVO = new TodayDrawResponseVO();
    todayDrawResponseVO.setProductId(productVO.getId());
    ArrayList<RaffleVO> raffleList = raffleService.getRaffleListByDeliveryType(
        new RaffleListByDeliveryTypeVO(productVO.getId(), deliveryType));
    todayDrawResponseVO.setTodayDraws(raffleList);
    todayDrawResponseVO.sortTodayDraws();
    return todayDrawResponseVO;
  }

}
