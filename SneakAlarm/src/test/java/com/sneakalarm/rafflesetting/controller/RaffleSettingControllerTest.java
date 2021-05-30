package com.sneakalarm.rafflesetting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sneakalarm.rafflesetting.service.RaffleSettingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RaffleSettingControllerTest {

  @MockBean
  RaffleSettingService raffleSettingService;

  @InjectMocks
  @Autowired
  RaffleSettingController raffleSettingController;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void createRaffleSetting() throws Exception {
    mockMvc.perform(put("/raffle-setting"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("success"));
  }
}