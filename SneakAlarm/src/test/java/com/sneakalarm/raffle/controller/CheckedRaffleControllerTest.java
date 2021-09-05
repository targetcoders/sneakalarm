package com.sneakalarm.raffle.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.raffle.dao.RaffleMapper;
import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CheckedRaffleControllerTest {

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
    Cookie[] cookies = new Cookie[1];
    cookies[0] = new Cookie("myRaffles","1/2");
    ObjectMapper objectMapper = new ObjectMapper();

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/raffles/checked")
        .cookie(cookies);

    mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cookies)))
        .andDo(print());
  }
}