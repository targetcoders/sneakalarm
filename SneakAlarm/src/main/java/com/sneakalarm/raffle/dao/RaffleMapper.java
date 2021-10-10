package com.sneakalarm.raffle.dao;

import com.sneakalarm.raffle.dto.ActiveRafflesVO;
import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleSearchCondition;
import java.util.ArrayList;

import com.sneakalarm.raffle.dto.RaffleUpdateStatusVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.sneakalarm.raffle.dto.RaffleVO;

@Mapper
public interface RaffleMapper {
  void raffleInsert(RaffleVO raffleVO);

  ArrayList<RaffleVO> getCheckedRaffleList(String productId);

  List<RaffleVO> getCheckedRaffleList(String[] myRaffles);

  ArrayList<RaffleVO> getRaffleListAll();

  ArrayList<RaffleVO> getRaffle(String raffleId);

  void updateRaffle(RaffleVO raffleVO);

  void deleteRaffle(String id);

  void updateDrawStatus(RaffleUpdateStatusVO updateDrawStatusVO);

  String getDrawStatus(String id);

  ArrayList<RaffleVO> getRaffleListByStatus(RaffleListByStatusVO status);

  ArrayList<RaffleVO> getRaffleListByDeliveryType(RaffleListByDeliveryTypeVO DeliveryTypeVO);

  List<RaffleVO> activeRaffles(ActiveRafflesVO activeRafflesVO);

  List<RaffleVO> duplicatedRaffles(RaffleSearchCondition raffleSearchCondition);
}
