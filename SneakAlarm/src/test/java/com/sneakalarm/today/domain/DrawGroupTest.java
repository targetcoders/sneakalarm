package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DrawGroupTest {

  private ProductMapper productMapper;
  private RaffleMapper raffleMapper;

  @Before
  public void init(){
    productMapper = Mockito.mock(ProductMapper.class);
    raffleMapper = Mockito.mock(RaffleMapper.class);
  }

  @Test
  public void testConstructor() throws ParseException {
    List<ProductVO> expectedProductVOList = new ArrayList<>();
    ProductVO expectedProductVO = new ProductVO();
    expectedProductVOList.add(expectedProductVO);
    RaffleVO activeRaffleVO = new RaffleVO();
    activeRaffleVO.setEndDate("2021-08-13");
    activeRaffleVO.setEndTime("00:00");
    RaffleVO activeRaffleVO2 = new RaffleVO();
    activeRaffleVO2.setEndDate("2021-08-07");
    activeRaffleVO2.setEndTime("02:00");
    List<RaffleVO> raffleVOListActive = new ArrayList<>();
    raffleVOListActive.add(activeRaffleVO);
    raffleVOListActive.add(activeRaffleVO2);
    List<RaffleVO> raffleVOListReady = new ArrayList<>();
    RaffleVO readyRaffle = new RaffleVO();
    readyRaffle.setEndDate("2021-08-07");
    readyRaffle.setEndTime("01:30");
    RaffleVO readyRaffle2 = new RaffleVO();
    readyRaffle2.setEndDate("2021-08-17");
    readyRaffle2.setEndTime("18:00");
    raffleVOListReady.add(readyRaffle);
    raffleVOListReady.add(readyRaffle2);
    List<RaffleVO> expectedRaffleVOList = new ArrayList<>();
    expectedRaffleVOList.add(readyRaffle);
    expectedRaffleVOList.add(activeRaffleVO2);
    expectedRaffleVOList.add(activeRaffleVO);
    expectedRaffleVOList.add(readyRaffle2);
    Mockito.doReturn(expectedProductVOList).when(productMapper).getProductList("0");
    Mockito.doReturn(raffleVOListActive).when(raffleMapper).getRaffleListByStatus(new RaffleListByStatusVO("0", "active"));
    Mockito.doReturn(raffleVOListReady).when(raffleMapper).getRaffleListByStatus(new RaffleListByStatusVO("0", "ready"));

    DrawGroup drawGroup = new DrawGroup("0", productMapper, raffleMapper);

    System.out.println(drawGroup.getRaffleVOList().toString());
    Assert.assertEquals(expectedProductVO, drawGroup.getProductVO());
    Assert.assertEquals(expectedRaffleVOList, drawGroup.getRaffleVOList());
  }

}