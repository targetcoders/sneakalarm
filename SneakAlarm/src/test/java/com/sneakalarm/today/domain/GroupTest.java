package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.today.domain.Group.Fake;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.parameters.P;

public class GroupTest {

  @Mock
  ProductMapper productMapper;
  @Mock
  RaffleMapper raffleMapper;

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

  @Test
  public void testDefaultConstructor() throws Exception {
    String[] deliveryTypes = {"방문수령","택배배송"};

    Fake drawGroup = new Group.Fake("100", deliveryTypes, "active", productMapper, raffleMapper);
    List<RaffleVO> raffleVOList = drawGroup.getTargetRaffleVOList();

    Assert.assertEquals(1, raffleVOList.size());
    Assert.assertEquals("1", raffleVOList.get(0).getId());
  }

  @Test
  public void testProductConstructor() throws Exception {
    RaffleVO raffleVO = new RaffleVO();
    raffleVO.setProductId("100");

    Fake drawGroup = new Group.Fake(raffleVO, productMapper);

    List<RaffleVO> raffleVOList = drawGroup.getTargetRaffleVOList();
    Assert.assertEquals("100", drawGroup.getProductVO().getId());
    Assert.assertEquals(new ArrayList<>().isEmpty(), drawGroup.getTargetRaffleVOList().isEmpty());
    Assert.assertEquals(sdf.parse("2021/09/01 09:30"), drawGroup.getFirstEndDateTime());
  }
}