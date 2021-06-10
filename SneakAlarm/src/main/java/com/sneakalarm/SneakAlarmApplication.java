package com.sneakalarm;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class SneakAlarmApplication {

  public static void main(String[] args) {
    SpringApplication.run(SneakAlarmApplication.class, args);
  }

  @PostConstruct
  public void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
  }
}
