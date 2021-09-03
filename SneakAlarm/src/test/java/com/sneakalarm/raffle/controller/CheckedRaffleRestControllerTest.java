package com.sneakalarm.raffle.controller;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;
import com.sneakalarm.security.config.SecurityConfig;
import com.sneakalarm.security.service.SecurityService;
import com.sneakalarm.today.domain.DrawGroup;
import com.sneakalarm.today.domain.Group;
import com.sneakalarm.today.domain.Group.Fake;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CheckedRaffleRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Mock
  private ProductMapper productMapper;
  @Mock
  private RaffleMapper raffleMapper;

  @Before
  public void init() {

  }

  @Test
  public void testCheckedRaffleList() throws Exception {

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/raffles/checked")
        .cookie(new Cookie("myRaffles","1/2/3/4/5/6/7"));

    mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }
}