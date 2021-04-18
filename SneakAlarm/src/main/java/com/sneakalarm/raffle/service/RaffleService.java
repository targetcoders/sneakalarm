package com.sneakalarm.raffle.service;

import java.text.ParseException;
import java.util.ArrayList;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleInsertVO;
import com.sneakalarm.raffle.dto.RaffleVO;

public interface RaffleService {
  void raffleInsert(RaffleInsertVO raffleInsertVO);

  ArrayList<RaffleVO> getRaffleList(Integer raffleId);

  ArrayList<RaffleCardVO> getRaffleCardList(String productId);

  ArrayList<RaffleVO> getRaffleListAll();

  void updateRaffle(RaffleInsertVO raffleInsertVO);

  void deleteRaffle(Integer id);

  public String getRaffleStatus(String startDate, String startTime, String endDate, String endTime) throws ParseException;

  public void updateDrawStatus(String id, String status);
}
