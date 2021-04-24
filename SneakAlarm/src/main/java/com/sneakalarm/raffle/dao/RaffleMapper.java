package com.sneakalarm.raffle.dao;

import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import java.util.ArrayList;

import com.sneakalarm.raffle.dto.RaffleUpdateStatusVO;
import org.apache.ibatis.annotations.Mapper;

import com.sneakalarm.raffle.dto.RaffleVO;

@Mapper
public interface RaffleMapper {
  void raffleInsert(RaffleVO raffleVO);

  ArrayList<RaffleVO> getRaffleList(String productId);

  ArrayList<RaffleVO> getRaffleListAll();

  ArrayList<RaffleVO> getRaffle(String raffleId);

  void updateRaffle(RaffleVO raffleVO);

  void deleteRaffle(String id);

  void updateDrawStatus(RaffleUpdateStatusVO updateDrawStatusVO);

  String getDrawStatus(String id);

  ArrayList<RaffleVO> getRaffleListByStatus(RaffleListByStatusVO status);
}
