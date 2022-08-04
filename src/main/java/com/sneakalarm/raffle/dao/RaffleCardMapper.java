package com.sneakalarm.raffle.dao;

import com.sneakalarm.raffle.dto.RaffleCardVO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RaffleCardMapper {

  ArrayList<RaffleCardVO> getRaffleCardList(String productId);
}
