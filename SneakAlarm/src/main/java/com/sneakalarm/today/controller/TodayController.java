package com.sneakalarm.today.controller;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
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

  private int[] unregisteredRaffleListSize;
  private int[] koreaRaffleListSize;
  private int[] directRaffleListSize;
  private int[] agentRaffleListSize;

  @GetMapping("/")
  public String nowPage(Model model) throws Exception {
    unregisteredRaffleListSize = new int[2];
    koreaRaffleListSize = new int[2];
    directRaffleListSize = new int[2];
    agentRaffleListSize = new int[2];
    model.addAttribute("unregisteredDrawGroupList",drawListUnregistered());
    model.addAttribute("unregisteredRaffleListSize", unregisteredRaffleListSize[0]);
    model.addAttribute("readyUnregisteredDrawGroupList",drawListReadyUnregistered());
    model.addAttribute("readyUnregisteredRaffleListSize", unregisteredRaffleListSize[1]);
    model.addAttribute("koreaDrawGroupList",drawListKorea());
    model.addAttribute("koreaRaffleListSize", koreaRaffleListSize[0]);
    model.addAttribute("readyKoreaDrawGroupList",drawListReadyKorea());
    model.addAttribute("readyKoreaRaffleListSize", koreaRaffleListSize[1]);
    model.addAttribute("directDrawGroupList",drawListDirect());
    model.addAttribute("directRaffleListSize", directRaffleListSize[0]);
    model.addAttribute("readyDirectDrawGroupList",drawListReadyDirect());
    model.addAttribute("readyDirectRaffleListSize", directRaffleListSize[1]);
    model.addAttribute("agentDrawGroupList",drawListAgent());
    model.addAttribute("agentRaffleListSize", agentRaffleListSize[0]);
    model.addAttribute("readyAgentDrawGroupList", drawListReadyAgent());
    model.addAttribute("readyAgentRaffleListSize", agentRaffleListSize[1]);
    return "views/today";
  }

  private List<DrawGroup> drawListUnregistered() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      List<ProductVO> productList = productService.getProductByDeliveryType(deliveryType);
      for(ProductVO product : productList){
        List<RaffleVO> list = raffleMapper
            .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_ACTIVE));
        if (!hasTargetDeliveryType(deliveryTypes, list)) {
          continue;
        }
        if(product.getModel_kr().equals("?")) {
          productSet.add(product);
        }
      }
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      DrawGroup unregisteredDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_ACTIVE, productMapper, raffleMapper);
      unregisteredRaffleListSize[0] += unregisteredDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(unregisteredDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }
  private List<DrawGroup> drawListReadyUnregistered() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      List<ProductVO> productList = productService.getProductByDeliveryType(deliveryType);
      for(ProductVO product : productList){
        List<RaffleVO> list = raffleMapper
            .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_READY));
        if (!hasTargetDeliveryType(deliveryTypes, list)) {
          continue;
        }
        if(product.getModel_kr().equals("?")) {
          productSet.add(product);
        }
      }
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      DrawGroup readyUnregisteredDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_READY, productMapper, raffleMapper);
      unregisteredRaffleListSize[1] += readyUnregisteredDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(readyUnregisteredDrawGroup);
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
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_ACTIVE));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup koreaDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_ACTIVE, productMapper, raffleMapper);
      koreaRaffleListSize[0] += koreaDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(koreaDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }
  private List<DrawGroup> drawListReadyKorea() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"택배배송", "방문수령"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_READY));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup readyKoreaDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_READY, productMapper, raffleMapper);
      koreaRaffleListSize[1] += readyKoreaDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(readyKoreaDrawGroup);
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
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_ACTIVE));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup directDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_ACTIVE, productMapper, raffleMapper);
      directRaffleListSize[0] += directDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(directDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }
  private List<DrawGroup> drawListReadyDirect() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"직배"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_READY));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup readyDirectDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_READY, productMapper, raffleMapper);
      directRaffleListSize[1] += readyDirectDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(readyDirectDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }

  private boolean hasTargetDeliveryType(String[] deliveryTypes, List<RaffleVO> list) {
    for(RaffleVO raffleVO : list){
      for(String deliveryType : deliveryTypes)
      if(raffleVO.getDelivery().contains(deliveryType)){
        return true;
      }
    }
    return false;
  }

  private List<DrawGroup> drawListAgent() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"배대지"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_ACTIVE));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup readyAgentDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_ACTIVE, productMapper, raffleMapper);
      agentRaffleListSize[0] += readyAgentDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(readyAgentDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }
  private List<DrawGroup> drawListReadyAgent() throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    String[] deliveryTypes = {"배대지"};
    Set<ProductVO> productSet = new HashSet<>();
    for (String deliveryType : deliveryTypes) {
      productSet.addAll(productService.getProductByDeliveryType(deliveryType));
    }
    for (ProductVO product : new ArrayList<>(productSet)) {
      List<RaffleVO> list = raffleMapper
          .getRaffleListByStatus(new RaffleListByStatusVO(product.getId(), DrawGroup.STATUS_READY));
      if (!hasTargetDeliveryType(deliveryTypes, list)) {
        continue;
      }
      if(product.getModel_kr().equals("?")){
        continue;
      }
      DrawGroup readyAgentDrawGroup = new DrawGroup(product.getId(), deliveryTypes, DrawGroup.STATUS_READY, productMapper, raffleMapper);
      agentRaffleListSize[1] += readyAgentDrawGroup.getTargetRaffleVOList().size();
      drawGroupList.add(readyAgentDrawGroup);
    }
    Collections.sort(drawGroupList);
    return drawGroupList;
  }

}
