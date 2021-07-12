package com.sneakalarm.raffle.domain;

import java.util.Date;

interface DateTime{
  Date getDate();
}

public class DateTimeImpl implements DateTime
{
  @Override
  public Date getDate() {
    return new Date();
  }
}
