package com.sneakalarm.raffle.dao;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

import com.sneakalarm.product.dto.InsertDrawVO;
import com.sneakalarm.raffle.dto.RaffleVO;

@Mapper
public interface RaffleMapper {
  public void raffleInsert(RaffleVO raffleVO);

  public ArrayList<RaffleVO> getRaffleList(String productId);

  public ArrayList<RaffleVO> getRaffle(Integer raffleId);

  public void updateRaffle(RaffleVO raffleVO);

  public void deleteRaffle(Integer id);
}
