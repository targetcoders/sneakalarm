package com.sneakalarm.raffle.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.sneakalarm.raffle.dto.RaffleVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstaFeedTest {

  @Test
  public void testGetTextWhenTodayRaffle() throws ParseException {
    RaffleVO raffleVO = RaffleVO.builder()
        .productId("100")
        .url("textUrl")
        .raffleType("응모")
        .releasePrice("123,000")
        .storeName("나이키 수유")
        .delivery("방문 수령")
        .payType("당첨 직접 결제")
        .endDate("2021-07-12")
        .endTime("18:00")
        .content("▶ 응모방법\n"
            + "카카오톡 채널에서 '나이키 강남' 친구 추가 후 채팅창 메뉴에서 'THE DRAW' 버튼을 눌러 링크로 접속(버튼은 드로우 시간이 되면 노출됩니다).\n"
            + "(나이키강남 카카오톡 채널 링크 pf.kakao.com/_xclMzK)\n"
            + "-\n"
            + "▶ 응모기간\n"
            + "02/10(수) 15:00PM ~ 18:00PM\n"
            + "이외 시간에는 응모 불가능합니다.\n"
            + "-\n"
            + "▶ 당첨발표\n"
            + "02/10(수) 20:00PM 이후 문자를 통한 개별 안내 예정\n"
            + "(미당첨자의 경우 문자는 발송되지 않습니다.)\n"
            + "-\n"
            + "▶ 구매기간 및 방법\n"
            + "02/11 ~ 02/13\n"
            + "나이키 강남 매장 방문하여 구매\n"
            + "1. 신분증, 당첨문자\n"
            + "2. NIKE.COM 멤버 QR\n"
            + "위 2가지를 모두 보여주셔야 하며, 구매기간 외에는 구매 불가합니다.\n"
            + "-\n"
            + "✔️ 중복 응모 시 1회 응모로 처리됩니다.\n"
            + "✔ 제품 설명, 사이즈, 수량 등 드로우와 관련된 어떤 문의도 받고 있지 않습니다.\n"
            + "✔️ 본인 수령만 가능하며, 대리 수령 절대 불가합니다.\n"
            + "✔️ 본인 명의 카드 및 현금 결제 가능합니다.")
        .build();
    DateTime dateTime = Mockito.mock(DateTime.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Mockito.doReturn(sdf.parse("2021-07-12")).when(dateTime).getDate();
    InstaFeed instaFeed = new InstaFeed(raffleVO, dateTime);

    String result = instaFeed.getText();

    Assert.assertThat(result, is("⏰나이키 수유 응모: ~ 오늘 18:00"));
  }

}