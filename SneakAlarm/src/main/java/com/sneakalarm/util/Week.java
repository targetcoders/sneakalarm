package com.sneakalarm.util;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

public class Week {
  private final Date date;

  private final String[] weeks = {"일", "월", "화", "수", "목", "금", "토"};

  public Week(Date date){
    this.date = date;
  }

  public String get() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return weeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
  }
}
