package com.sneakalarm.raffle.service;

import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import java.text.ParseException;
import java.util.ArrayList;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleInsertVO;
import com.sneakalarm.raffle.dto.RaffleVO;

public interface RaffleService {
  void raffleInsert(RaffleInsertVO raffleInsertVO);

  ArrayList<RaffleVO> getRaffleList(String raffleId);

  ArrayList<RaffleCardVO> getRaffleCardList(String productId);

  ArrayList<RaffleVO> getRaffleListAll();

  void updateRaffle(RaffleInsertVO raffleInsertVO);

  void deleteRaffle(String id);

  public String calcRaffleStatus(String startDate, String startTime, String endDate, String endTime) throws ParseException;

  public void updateDrawStatus(String id, String status);

  String getDrawStatus(String id);

  ArrayList<RaffleVO> getRaffleListByStatus(RaffleListByStatusVO raffleListByStatusVO);

  ArrayList<RaffleVO> getRaffleListByDeliveryType(RaffleListByDeliveryTypeVO raffleListByDeliveryTypeVO);
}
