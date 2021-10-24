package com.sneakalarm.raffle.controller;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;
import com.sneakalarm.today.domain.DrawGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckedRaffleController {

  @Autowired
  RaffleService raffleService;
  @Autowired
  ProductService productService;
  @Autowired
  ProductMapper productMapper;

  @GetMapping("/checked")
  public String checkedRaffleList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model)
      throws Exception {
    Cookie[] cookies = httpServletRequest.getCookies();
    String myRaffles = searchCookie(cookies,"myRaffles").getValue();
    String conditions = searchCookie(cookies,"searchDrawConditions").getValue();
    if(myRaffles.equals("fail")) {
      httpServletResponse.addCookie(new Cookie("myRaffles",""));
    }
    if(conditions.equals("fail")) {
      httpServletResponse.addCookie(new Cookie("searchDrawConditions","ended"));
      conditions = "ended";
    }

    String[] splitMyRaffles = myRaffles.split("/");
    if(splitMyRaffles.length == 0){
      model.addAttribute("checkedDrawNumbers", 0);
      model.addAttribute("status", conditions);
      return "views/checked-raffles/list";
    }

    List<RaffleVO> checkedRaffleVOList = raffleService.getCheckedRaffleList(splitMyRaffles);
    List<DrawGroup> drawGroupList = checkedDrawGroups(conditions, checkedRaffleVOList);
    model.addAttribute("checkedDrawGroupList", drawGroupList);
    int raffleListSize = 0;
    for(DrawGroup drawGroup : drawGroupList) {
      raffleListSize += drawGroup.getTargetRaffleVOList().size();
    }
    model.addAttribute("checkedDrawNumbers", raffleListSize);
    model.addAttribute("status", conditions);
    return "views/checked-raffles/list";
  }

  private List<DrawGroup> checkedDrawGroups(String conditions, List<RaffleVO> checkedRaffleVOList)
      throws Exception {
    List<DrawGroup> drawGroupList = new ArrayList<>();
    for(RaffleVO raffleVO : checkedRaffleVOList) {
      if(!conditions.contains(raffleVO.getStatus())){
        continue;
      }
      DrawGroup drawGroup = existingDrawGroup(drawGroupList, raffleVO.getProductId());
      if(drawGroup == null) {
        DrawGroup newDrawGroup = new DrawGroup(raffleVO, productMapper);
        newDrawGroup.setProductVO(productService.getProductList(raffleVO.getProductId()).get(0));
        drawGroupList.add(newDrawGroup);
        continue;
      }
      drawGroup.addRaffle(raffleVO);
    }
    Collections.sort(drawGroupList);
    for(DrawGroup drawGroup : drawGroupList) {
      drawGroup.sortRaffleList();
      drawGroup.convertFormat();
    }

    return drawGroupList;
  }

  private DrawGroup existingDrawGroup(List<DrawGroup> drawGroupList, String productId) {
    for(DrawGroup drawGroup : drawGroupList) {
      if(drawGroup.getProductVO().getId().equals(productId)){
        return drawGroup;
      }
    }
    return null;
  }

  private Cookie searchCookie(Cookie[] cookies, String keyword) {
    if(cookies == null) {
      return new Cookie(keyword,"fail");
    }
    for(Cookie cookie : cookies){
      if(cookie.getName().equals(keyword)){
        return cookie;
      }
    }
    return new Cookie(keyword,"fail");
  }

}
