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
public class TodayRestController {

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

  @GetMapping("/now/draw-list/unregistered")
  public String drawListUnregistered() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      List<ProductVO> productList = productService.getProductByDeliveryType(deliveryType);
      for(ProductVO product : productList){
        if(product.getModel_kr().equals("?")) {
          productSet.add(product);
        }
      }
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      drawGroupList.add(new DrawGroup(product.getId(), deliveryTypes, productMapper, raffleMapper));
    }
    Collections.sort(drawGroupList);
    return new ObjectMapper().writeValueAsString(drawGroupList);
  }

  @GetMapping("/now/draw-list/korea")
  public String drawListKorea() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      if(product.getModel_kr().equals("?")){
        continue;
      }
      drawGroupList.add(new DrawGroup(product.getId(), deliveryTypes, productMapper, raffleMapper));
    }
    Collections.sort(drawGroupList);
    return new ObjectMapper().writeValueAsString(drawGroupList);
  }

  @GetMapping("/now/draw-list/direct")
  public String drawListDirect() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"직배"};
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

  @GetMapping("/now/draw-list/agent")
  public String drawListAgent() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"배대지"};
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

}
