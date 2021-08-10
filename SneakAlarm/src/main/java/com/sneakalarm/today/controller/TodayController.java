package com.sneakalarm.today.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.today.domain.DrawGroup;
import com.sneakalarm.today.dto.TodayDrawResponseVO;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import com.sneakalarm.raffle.service.RaffleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Lists;

@RestController
public class TodayController {

  @Autowired
  ProductService productService;
  @Autowired
  RaffleService raffleService;
  @Autowired
  ProductMapper productMapper;
  @Autowired
  RaffleMapper raffleMapper;

  @GetMapping("/now/productList")
  public ArrayList<TodayProductResponseVO> getProductList(){
    return productService.getTodayProductResponseVO();
  }

  @GetMapping("/now/drawList/korea")
  public ArrayList<DrawGroup> koreaDrawList(){
    return null;
  }

  @GetMapping("/now/draw-list/korea")
  public String getTodayKorea() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      drawGroupList.add(new DrawGroup(product.getId(), deliveryTypes, productMapper, raffleMapper));
    }
    Collections.sort(drawGroupList);
    return new ObjectMapper().writeValueAsString(drawGroupList);
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
