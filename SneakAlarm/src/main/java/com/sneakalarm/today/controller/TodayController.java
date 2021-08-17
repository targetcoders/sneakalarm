package com.sneakalarm.today.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.today.domain.DrawGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodayController {
  @Autowired
  ProductService productService;
  @Autowired
  ProductMapper productMapper;
  @Autowired
  RaffleMapper raffleMapper;

  @GetMapping("/")
  public String nowPage(Model model) throws Exception {
    model.addAttribute("unregisteredDrawGroupList",drawListUnregistered());
    model.addAttribute("koreaDrawGroupList",drawListKorea());
    model.addAttribute("directDrawGroupList",drawListDirect());
    model.addAttribute("agentDrawGroupList",drawListAgent());
    return "views/today";
  }

  private List<DrawGroup> drawListUnregistered() throws Exception {
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
    return drawGroupList;
  }
  private List<DrawGroup> drawListKorea() throws Exception {
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
    return drawGroupList;
  }
  private List<DrawGroup> drawListDirect() throws Exception {
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
    return drawGroupList;
  }
  private List<DrawGroup> drawListAgent() throws Exception {
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
    return drawGroupList;
  }

}
