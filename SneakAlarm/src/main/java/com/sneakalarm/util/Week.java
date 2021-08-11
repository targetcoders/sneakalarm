package com.sneakalarm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class Week {
  private final String[] day = {"일", "월", "화", "수", "목", "금", "토"};

  public String getWeek(String date, SimpleDateFormat dateFormat) throws Exception {
    Date inputDate = dateFormat.parse(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(inputDate);

    int dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
    return day[dayNum];
  }
}
