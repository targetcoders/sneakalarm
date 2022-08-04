package com.sneakalarm.util;

import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;

public class WeekTest {

  @Test
  public void constructor() throws Exception {
    String week = new DateTranslator(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-08-11 00:30")).krWeek();

    Assert.assertEquals("수", week);
  }

}