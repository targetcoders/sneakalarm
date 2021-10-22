package com.sneakalarm.util;

import java.util.Calendar;
import java.util.Date;

public class DateTranslator {
  private final Calendar cal;

  private static final String[] krWeeks = {"일", "월", "화", "수", "목", "금", "토"};
  private static final String[] enWeeks = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};

  public DateTranslator(Date date){
    this.cal = Calendar.getInstance();
    cal.setTime(date);
  }

  public String krWeek() {
    return krWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
  }

  public String enWeek() {
    return enWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
  }
}
