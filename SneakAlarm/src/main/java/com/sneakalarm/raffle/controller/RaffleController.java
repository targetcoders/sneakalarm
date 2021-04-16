package com.sneakalarm.raffle.controller;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sneakalarm.raffle.dto.RaffleInsertVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;

@Controller
public class RaffleController {

  @Autowired
  private RaffleService raffleService;

  @GetMapping("/raffle-insert")
  public String raffleInsert(@Param("productId") Long productId, Model productInfo) {
    productInfo.addAttribute("productId", productId);
    return "views/raffle-insert";
  }

  @PostMapping("/raffle-insert")
  @ResponseBody
  public void raffleInsert(RaffleInsertVO raffleInsertVO) {
    raffleService.raffleInsert(raffleInsertVO);
  }

  @GetMapping("/raffle-modify")
  public String modifyProduct(@Param("id") Integer id, Model model) {
    ArrayList<RaffleVO> list = (ArrayList<RaffleVO>) raffleService.getRaffleList(id);
    RaffleVO raffleVO = list.get(0);
    model.addAttribute("raffleVO", raffleVO);
    return "views/raffle-modify";
  }

  @PostMapping("/raffle-modify")
  @ResponseBody
  public boolean modifyRaffle(RaffleInsertVO RaffleInsertVO) {
    raffleService.updateRaffle(RaffleInsertVO);
    return true;
  }

  @GetMapping("/raffle-delete")
  @ResponseBody
  public boolean deleteRaffle(@Param("id") Integer id) {
    raffleService.deleteRaffle(id);
    return true;
  }

  @GetMapping("/today")
  public String today(){
    return "/views/today";
  }

}
