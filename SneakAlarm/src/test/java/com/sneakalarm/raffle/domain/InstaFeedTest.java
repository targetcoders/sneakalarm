package com.sneakalarm.raffle.domain;

import static org.hamcrest.Matchers.is;

import com.sneakalarm.feedtext.domain.InstaFeed;
import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class InstaFeedTest {

  private DateTime dateTime;
  private InstaFeed instaFeed;

  @Before
  public void init(){
    dateTime = Mockito.mock(DateTime.class);
    RaffleVO raffleVO = RaffleVO.builder()
        .productId("100")
        .url("textUrl")
        .model_kr("(M)덩크 로우 블랙")
        .raffleType("응모")
        .releasePrice("123,000")
        .storeName("나이키 수유")
        .delivery("방문 수령")
        .payType("당첨 직접 결제")
        .endDate("2021-07-12")
        .endTime("18:00")
        .specialCase("✔️ 중복 응모 시 1회 응모로 처리됩니다.")
        .build();
    instaFeed = new InstaFeed(raffleVO, dateTime);
  }

  @Test
  public void testGetTextWhenTodayRaffle() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Mockito.doReturn(sdf.parse("2021-07-12")).when(dateTime).getDate();

    String result = instaFeed.feedText();

    Assert.assertEquals(result,
        "⏰나이키 수유 응모: ~ 오늘 18:00\n(M)덩크 로우 블랙\n\n나이키 수유에서 응모를 진행합니다!🔥\n기회를 놓치지 마세요!🔥\n여러분의 당첨을 기원합니다.🤗\n\n✅ 주의 사항 ✅\n✔️ 중복 응모 시 1회 응모로 처리됩니다.\n\n@sneakalarm.com⬅응모 링크\n\n\" 게시물 🛎알람 설정하고 한정판 신발 겟하세요! \"\n📈시세:　@sneakalarm_justdidit\n🚨뉴스:　@sneakalarm_news\n\n스알님들의 좋아요+댓글은 큰 도움이 됩니다👍🏻\n\n.\n.\n.\n.\n#응모하기 #스니커 #나이키매니아 #덩크로우 #조던 #나이키조던 #나이키조던1 #덩크하이 #나이키덩크하이 #조던1 #응모알림 #스알 #스니커응모");
  }
}