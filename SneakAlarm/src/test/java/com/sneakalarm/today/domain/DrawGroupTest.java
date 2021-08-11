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
  private List<RaffleVO> raffleVOListReady;
  private List<RaffleVO> raffleVOListActive;
  private RaffleVO readyRaffleVO1;
  private RaffleVO readyRaffleVO2;
  private RaffleVO activeRaffleVO1;
  private RaffleVO activeRaffleVO2;

  @Before
  public void init(){
    productMapper = Mockito.mock(ProductMapper.class);
    raffleMapper = Mockito.mock(RaffleMapper.class);
    raffleVOListReady = new ArrayList<>();
    raffleVOListActive = new ArrayList<>();
    readyRaffleVO1 = RaffleVO.builder().endDate("2021-08-07").endTime("01:30").delivery("택배배송").build();
    readyRaffleVO2 = RaffleVO.builder().endDate("2021-08-17").endTime("18:00").delivery("방문수령").build();
    raffleVOListReady.add(readyRaffleVO1);
    raffleVOListReady.add(readyRaffleVO2);

    activeRaffleVO1 = RaffleVO.builder().endDate("2021-08-13").endTime("00:00").delivery("방문수령").build();
    activeRaffleVO2 = RaffleVO.builder().endDate("2021-08-07").endTime("02:00").delivery("방문수령").build();
    raffleVOListActive.add(activeRaffleVO1);
    raffleVOListActive.add(activeRaffleVO2);
  }

  @Test
  public void testConstructor() throws Exception {
    List<ProductVO> expectedProductVOList = new ArrayList<>();
    ProductVO expectedProductVO = new ProductVO();
    expectedProductVO.setId("0");
    expectedProductVO.setDeliveryTypes("방문수령,택배배송,배대지,직배");
    expectedProductVOList.add(expectedProductVO);
    Mockito.doReturn(expectedProductVOList).when(productMapper).getProductList("0");
    Mockito.doReturn(raffleVOListActive).when(raffleMapper).getRaffleListByStatus(new RaffleListByStatusVO("0", "active"));
    Mockito.doReturn(raffleVOListReady).when(raffleMapper).getRaffleListByStatus(new RaffleListByStatusVO("0", "ready"));
    List<RaffleVO> expectedRaffleVOList = new ArrayList<>();
    expectedRaffleVOList.add(readyRaffleVO1);
    expectedRaffleVOList.add(activeRaffleVO2);
    expectedRaffleVOList.add(activeRaffleVO1);
    expectedRaffleVOList.add(readyRaffleVO2);

    String[] deliveryTypes = {"방문수령","택배배송"};
    DrawGroup drawGroup = new DrawGroup("0", deliveryTypes, productMapper, raffleMapper);

    System.out.println(drawGroup.getTargetRaffleVOList().toString());
    Assert.assertEquals(expectedProductVO, drawGroup.getProductVO());
    Assert.assertEquals(expectedRaffleVOList, drawGroup.getTargetRaffleVOList());
  }

}