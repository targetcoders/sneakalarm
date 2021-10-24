package com.sneakalarm.util;

import java.util.Calendar;
import java.util.Date;

public class DateTranslator {
  private Calendar cal;

  private static final String[] krWeeks = {"일", "월", "화", "수", "목", "금", "토"};
  private static final String[] enWeeks = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
  private static final String[] enMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

  public DateTranslator(Date date) {
    this.cal = Calendar.getInstance();
    cal.setTime(date);
  }

  public String krWeek() {
    return krWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
  }

  public String week_en() {
    return enWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
  }

  public String date_en() {
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int monthIndex = cal.get(Calendar.MONTH);
    return  day + " " + enMonth[monthIndex];
  }
}
