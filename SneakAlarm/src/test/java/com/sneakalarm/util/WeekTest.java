package com.sneakalarm.util;

import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;

public class WeekTest {

  @Test
  public void constructor() throws Exception {
    String week = new Week(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-08-11 00:30")).get();

    Assert.assertEquals("수", week);
  }

}