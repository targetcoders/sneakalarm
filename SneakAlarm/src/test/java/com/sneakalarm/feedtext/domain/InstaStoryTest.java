package com.sneakalarm.feedtext.domain;

import com.sneakalarm.raffle.dto.RaffleVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InstaStoryTest {

  @Before
  public void init() {

  }

  @Test
  public void Should_ReturnRaffleStory_When_응모방문수령() {
    RaffleVO raffleVO = RaffleVO.builder()
        .storeName("솔드아웃(App)")
        .endDate("09/20")
        .endTime("18:00")
        .endWeek("월")
        .specialCase("")
        .delivery("방문수령")
        .country("한국")
        .raffleType("응모")
        .build();
    String expectedText = raffleVO.getStoreName() + "\n"
        + "("+raffleVO.getEndWeek()+") "+raffleVO.getEndTime()+" 종료\n"
        + "*방문 구매\n\n"
        + "↗응모하러 가기↖";

    String result = new InstaStory(raffleVO).makeText();

    Assert.assertEquals(result, expectedText);
  }

  @Test
  public void Should_ReturnRaffleStory_When_응모택배배송() {
    RaffleVO raffleVO = RaffleVO.builder()
        .storeName("솔드아웃(App)")
        .endDate("09/20")
        .endTime("18:00")
        .endWeek("월")
        .specialCase("")
        .delivery("택배배송")
        .country("한국")
        .raffleType("응모")
        .build();
    String expectedText = raffleVO.getStoreName() + "\n"
        + "("+raffleVO.getEndWeek()+") "+raffleVO.getEndTime()+" 종료\n"
        + "*온라인 구매\n\n"
        + "↗응모하러 가기↖";

    String result = new InstaStory(raffleVO).makeText();

    Assert.assertEquals(result, expectedText);
  }

  @Test
  public void Should_ReturnRaffleStory_When_응모택배배송특이사항있음() {
    RaffleVO raffleVO = RaffleVO.builder()
        .storeName("솔드아웃(App)")
        .endDate("09/20")
        .endTime("18:00")
        .endWeek("월")
        .specialCase("1000원 구매 가능")
        .delivery("택배배송")
        .country("한국")
        .raffleType("응모")
        .build();
    String expectedText = raffleVO.getStoreName() + "\n"
        + "("+raffleVO.getEndWeek()+") "+raffleVO.getEndTime()+" 종료\n"
        + "*온라인 구매, 1000원 구매 가능\n\n"
        + "↗응모하러 가기↖";

    String result = new InstaStory(raffleVO).makeText();

    Assert.assertEquals(result, expectedText);
  }

  @Test
  public void Should_ReturnRaffleStory_When_선착택배배송특이사항있음() {
    RaffleVO raffleVO = RaffleVO.builder()
        .storeName("솔드아웃(App)")
        .endDate("09/20")
        .endTime("18:00")
        .endWeek("월")
        .specialCase("1000원 구매 가능")
        .delivery("택배배송")
        .country("한국")
        .raffleType("선착순")
        .build();
    String expectedText = raffleVO.getStoreName() + "\n"
        + "선착 ("+raffleVO.getEndWeek()+") "+raffleVO.getEndTime()+" 시작\n"
        + "*온라인 구매, 1000원 구매 가능\n\n"
        + "↗선착 구매하기↖";

    String result = new InstaStory(raffleVO).makeText();

    Assert.assertEquals(result, expectedText);
  }

  @Test
  public void Should_ReturnRaffleStory_When_선착택배배송() {
    RaffleVO raffleVO = RaffleVO.builder()
        .storeName("솔드아웃(App)")
        .endDate("09/20")
        .endTime("18:00")
        .endWeek("월")
        .specialCase("")
        .delivery("택배배송")
        .country("한국")
        .raffleType("선착순")
        .build();
    String expectedText = raffleVO.getStoreName() + "\n"
        + "선착 ("+raffleVO.getEndWeek()+") "+raffleVO.getEndTime()+" 시작\n"
        + "*온라인 구매\n\n"
        + "↗선착 구매하기↖";

    String result = new InstaStory(raffleVO).makeText();

    Assert.assertEquals(result, expectedText);
  }
}