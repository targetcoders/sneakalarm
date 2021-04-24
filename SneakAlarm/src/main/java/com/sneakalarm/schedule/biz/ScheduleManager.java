package com.sneakalarm.schedule.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleManager {

  @Autowired
  DrawSchedule drawSchedule;
  @Autowired
  ProductSchedule productSchedule;

  @Scheduled(fixedDelay = 60000)
  public void scheduleStart() throws Exception {
    drawSchedule.updateDrawStatus();
    productSchedule.DrawStatusNumSynchronize();
    productSchedule.updateDeliveryTypes();
  }

}
