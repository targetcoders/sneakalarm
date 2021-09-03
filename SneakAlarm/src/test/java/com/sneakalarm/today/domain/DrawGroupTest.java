package com.sneakalarm.today.domain;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.today.domain.Group.Fake;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DrawGroupTest {

  @Test
  public void testConstructor() throws Exception {
    String[] deliveryTypes = {"방문수령","택배배송"};

    Fake drawGroup = new Group.Fake("100",deliveryTypes,"active");
    List<RaffleVO> raffleVOList = drawGroup.getTargetRaffleVOList();

    Assert.assertEquals(1, raffleVOList.size());
    Assert.assertEquals("1", raffleVOList.get(0).getId());
  }

}