package com.sneakalarm.today.controller;

import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.today.dto.TodayDrawResponseVO;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import com.sneakalarm.raffle.service.RaffleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

  @GetMapping("/now/productList")
  public ArrayList<TodayProductResponseVO> getProductList(){
    return productService.getTodayProductResponseVO();
  }

  @GetMapping("/now/drawList")
  public ArrayList<TodayDrawResponseVO> getTodayKorea(
      @RequestParam("deliveryType") String deliveryType) throws Exception {
    ArrayList<TodayDrawResponseVO> ret = new ArrayList<>();
    String[] deliveryList = deliveryType.split(",");
    List<String> sortingDeliveryArrayList = new ArrayList<>();
    addEqualsItemsToList(deliveryList, sortingDeliveryArrayList, "택배배송");
    addEqualsItemsToList(deliveryList, sortingDeliveryArrayList, "방문수령");
    addEqualsItemsToList(deliveryList, sortingDeliveryArrayList, "직배");
    addEqualsItemsToList(deliveryList, sortingDeliveryArrayList, "배대지");

    for (String delivery : sortingDeliveryArrayList) {
      ArrayList<ProductVO> productList = productService.getProductByDeliveryType(delivery);
      for (ProductVO productVO : productList) {
        ret.add(getTodayDrawResponseVO(delivery, productVO));
      }
    }
    return ret;
  }

  private void addEqualsItemsToList(String[] deliveryList, List<String> sortingDeliveryArrayList, String deliveryType) {
    for(String delivery : deliveryList){
      if(delivery.equals(deliveryType))
        sortingDeliveryArrayList.add(delivery);
    }
  }

  private TodayDrawResponseVO getTodayDrawResponseVO(String deliveryType, ProductVO productVO)
      throws Exception {
    TodayDrawResponseVO todayDrawResponseVO = new TodayDrawResponseVO();
    todayDrawResponseVO.setProductId(productVO.getId());
    ArrayList<RaffleVO> raffleList = raffleService.getRaffleListByDeliveryType(
        new RaffleListByDeliveryTypeVO(productVO.getId(), deliveryType));
    todayDrawResponseVO.setTodayDraws(raffleList);
    Collections.sort(todayDrawResponseVO.getTodayDraws());
    return todayDrawResponseVO;
  }

}
