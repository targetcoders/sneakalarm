package com.sneakalarm.raffle.domain;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SiteCardParserTest {

  private Jsoup jsoup;

  private RaffleElementsParserForLuckD siteCardParser;
  private Document testDocument;

  @Before
  public void init() throws IOException {
    this.jsoup = Mockito.mock(JsoupImplForLuckD.class);
    siteCardParser = new RaffleElementsParserForLuckD("testUrl",jsoup);
    testDocument = new Document("testUrl");
    testDocument.append("<div class=\"site_card_layer\">\n"
        + "                    <h3 class=\"site_info_title\">온라인 선착순</h3>\n"
        + "                    \n"
        + "                    \n"
        + "                        <h5 class=\"empty_text\">온라인 선착순 발매 정보가 없습니다</h5>\n"
        + "                    \n"
        + "                    <h3 class=\"site_info_title\">온라인 응모</h3>\n"
        + "                    \n"
        + "                        <div class=\"site_card_wrap raffle kor \">\n"
        + "                            <div class=\"site_card\">\n"
        + "                                <img src=\"https://luckydraw-media.s3.amazonaws.com/agent_site/컨버스_코리아.png\">\n"
        + "                                \n"
        + "                                    <h4 class=\"agent_site_title\">컨버스 코리아</h4>\n"
        + "                                <p>한국 \n"
        + "                                </p>\n"
        + "                                        <p class=\"release_date_time\">07월 18일 18:00 까지</p>\n"
        + "                                <p class=\"additional_info\" onclick=\"javascript:additional_info_click(&quot;5777&quot;);\">[추가정보]</p>\n"
        + "                                <a target=\"_blank\" href=\"https://www.converse.co.kr/limited/moncler_teasing.html?utm_source=luck-d\" class=\"btn btn-dark btn-raffle\" onclick=\"javascript:additional_info_click(&quot;5777&quot;);\">\n"
        + "                                    바로가기\n"
        + "                                </a>\n"
        + "                                    <div class=\"btn btn-dark user_product_on user_product_5777\" onclick=\"javascript:change_apply(5777);\">\n"
        + "                                        <img style=\"width:20px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/check_on.png\">\n"
        + "                                    </div>\n"
        + "                                \n"
        + "                            </div>\n"
        + "                            <div class=\"modal fade\" id=\"modal_5777\" style=\"display: none;\">\n"
        + "                                <div class=\"modal-dialog\">\n"
        + "                                    <div class=\"modal-content\">\n"
        + "                                        <div class=\"modal-body\">\n"
        + "                                            <p><b>가격 :</b> 190,000 KRW </p>\n"
        + "                                            \n"
        + "                                            <p><b>진행 방식 :</b> 당첨 알림 발송</p>\n"
        + "                                            \n"
        + "                                                <p><b>온라인 채널 :</b>\n"
        + "                                                    \n"
        + "                                                        <a class=\"shape-square\" rel=\"nofollow\" target=\"_blank\" href=\"https://www.instagram.com/converse_kr/\"><img style=\"width:25px;height:25px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/instagram_logo.png\"></a>\n"
        + "                                                    \n"
        + "                                                    \n"
        + "                                                </p>\n"
        + "                                            \n"
        + "                                            <p><b>응모 기간</b><br>07월 16일 10:00 ~ \n"
        + "                                                07월 18일 18:00 까지</p>\n"
        + "                                            <p><b>당첨자 발표</b><br>07월 19일 14:00</p>\n"
        + "                                            <p><b>구매 기간</b><br>07월 19일 14:00 ~ 18:00</p>\n"
        + "                                            \n"
        + "                                            \n"
        + "                                            <p><b>메모</b></p>\n"
        + "                                            <textarea class=\"form-control\" rows=\"3\" id=\"memo_5777\" maxlength=\"64\" tabindex=\"20\"></textarea>\n"
        + "                                            <span style=\"color:red;font-size:.8em\">* 응모한 사이즈, 이용한 배대지 등 메모를 남겨보세요!</span>\n"
        + "                                            <div style=\"text-align: center;padding-top:20px\">\n"
        + "                                                \n"
        + "                                                <div class=\"btn btn-dark\" id=\"memo_save_5777\" tabindex=\"21\" onclick=\"javascript:change_memo(5777);\">\n"
        + "                                                    저장\n"
        + "                                                </div>\n"
        + "                                                <div class=\"btn btn-dark\" tabindex=\"22\" onclick=\"javascript:modal_close(5777);\">\n"
        + "                                                    닫기\n"
        + "                                                </div>\n"
        + "                                            </div>\n"
        + "                                        </div>\n"
        + "                                    </div>\n"
        + "                                </div>\n"
        + "                            </div>\n"
        + "                        </div>\n"
        + "                    \n"
        + "                        <div class=\"site_card_wrap raffle kor \">\n"
        + "                            <div class=\"site_card\">\n"
        + "                                <img src=\"https://luckydraw-media.s3.amazonaws.com/agent_site/케이스스터디.jpg\">\n"
        + "                                    <del><h4 class=\"agent_site_title\">케이스스터디</h4></del>\n"
        + "                                <p>한국 \n"
        + "                                        <img class=\"offline_map\" src=\"https://luckydraw-media.s3.amazonaws.com/img/map.png\" onclick=\"javascript:window.location.href='/offline/5770';\">\n"
        + "                                </p>\n"
        + "                                    <p class=\"release_date_time\" style=\"color:red;\">종료</p>\n"
        + "                                <p class=\"additional_info\" onclick=\"javascript:additional_info_click(&quot;5770&quot;);\">[추가정보]</p>\n"
        + "                                <a target=\"_blank\" href=\"https://m.sivillage.com/event/initEventDetail.siv?event_no=E210706514&amp;utm_source=luck-d\" class=\"btn btn-dark btn-raffle\" onclick=\"javascript:additional_info_click(&quot;5770&quot;);\">\n"
        + "                                    바로가기\n"
        + "                                </a>\n"
        + "                                    <div class=\"btn btn-dark user_product_on user_product_5770\" onclick=\"javascript:change_apply(5770);\">\n"
        + "                                        <img style=\"width:20px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/check_on.png\">\n"
        + "                                    </div>\n"
        + "                            </div>\n"
        + "                            <div class=\"modal fade\" id=\"modal_5770\" style=\"display: none;\">\n"
        + "                                <div class=\"modal-dialog\">\n"
        + "                                    <div class=\"modal-content\">\n"
        + "                                        <div class=\"modal-body\">\n"
        + "                                            <p><b>가격 :</b> 190,000 KRW </p>\n"
        + "                                            \n"
        + "                                            <p><b>진행 방식 :</b> 당첨 알림 발송</p>\n"
        + "                                            \n"
        + "                                                <p><b>온라인 채널 :</b>\n"
        + "                                                    \n"
        + "                                                        <a class=\"shape-square\" rel=\"nofollow\" target=\"_blank\" href=\"https://www.instagram.com/casestudy_official\"><img style=\"width:25px;height:25px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/instagram_logo.png\"></a>\n"
        + "                                                    \n"
        + "                                                    \n"
        + "                                                </p>\n"
        + "                                            \n"
        + "                                            <p><b>응모 기간</b><br>07월 14일 10:00 ~ \n"
        + "                                                07월 14일 16:00 까지</p>\n"
        + "                                            <p><b>당첨자 발표</b><br>07월 16일 오전</p>\n"
        + "                                            <p><b>구매 기간</b><br>당첨 문자 수신 ~ 07월 18일</p>\n"
        + "                                            \n"
        + "                                            \n"
        + "                                            <p><b>메모</b></p>\n"
        + "                                            <textarea class=\"form-control\" rows=\"3\" id=\"memo_5770\" maxlength=\"64\" tabindex=\"20\"></textarea>\n"
        + "                                            <span style=\"color:red;font-size:.8em\">* 응모한 사이즈, 이용한 배대지 등 메모를 남겨보세요!</span>\n"
        + "                                            <div style=\"text-align: center;padding-top:20px\">\n"
        + "                                                \n"
        + "                                                    \n"
        + "                                                \n"
        + "                                                <div class=\"btn btn-dark\" id=\"memo_save_5770\" tabindex=\"21\" onclick=\"javascript:change_memo(5770);\">\n"
        + "                                                    저장\n"
        + "                                                </div>\n"
        + "                                                <div class=\"btn btn-dark\" tabindex=\"22\" onclick=\"javascript:modal_close(5770);\">\n"
        + "                                                    닫기\n"
        + "                                                </div>\n"
        + "                                            </div>\n"
        + "                                        </div>\n"
        + "                                    </div>\n"
        + "                                </div>\n"
        + "                            </div>\n"
        + "                        </div>\n"
        + "                    \n"
        + "                        <div class=\"site_card_wrap raffle for \">\n"
        + "                            <div class=\"site_card\">\n"
        + "                                <img src=\"https://luckydraw-media.s3.amazonaws.com/agent_site/END_CLOTHING.jpg\">\n"
        + "                                    <del><h4 class=\"agent_site_title\">END CLOTHING</h4></del>\n"
        + "                                <p>영국 / 직배\n"
        + "                                </p>\n"
        + "                                    <p class=\"release_date_time\" style=\"color:red;\">종료</p>\n"
        + "                                <p class=\"additional_info\" onclick=\"javascript:additional_info_click(&quot;5691&quot;);\">[추가정보]</p>\n"
        + "                                <a target=\"_blank\" href=\"https://launches.endclothing.com/product/converse-x-moncler-genius-7-moncler-frgmt-hiroshi-fujiwara-fraylor-ii-chuck-taylor-womens-4m706-00-02slq-999?utm_source=luck-d\" class=\"btn btn-dark btn-raffle\" onclick=\"javascript:additional_info_click(&quot;5691&quot;);\">\n"
        + "                                    바로가기\n"
        + "                                </a>\n"
        + "                                \n"
        + "                                    <div class=\"btn btn-dark user_product_on user_product_5691\" onclick=\"javascript:change_apply(5691);\">\n"
        + "                                        <img style=\"width:20px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/check_on.png\">\n"
        + "                                    </div>\n"
        + "                                \n"
        + "                            </div>\n"
        + "                            <div class=\"modal fade\" id=\"modal_5691\">\n"
        + "                                <div class=\"modal-dialog\">\n"
        + "                                    <div class=\"modal-content\">\n"
        + "                                        <div class=\"modal-body\">\n"
        + "                                            <p><b>가격 :</b> 205 USD  ( 약 233,000 KRW )</p>\n"
        + "                                            <p style=\"color:grey\">( 150 USD 환율 : 약 170,700 KRW )</p>\n"
        + "                                            <p><b>진행 방식 :</b> 당첨시 자동 결제</p>\n"
        + "                                            \n"
        + "                                                <p><b>온라인 채널 :</b>\n"
        + "                                                    \n"
        + "                                                        <a class=\"shape-square\" rel=\"nofollow\" target=\"_blank\" href=\"https://www.instagram.com/endclothing/\"><img style=\"width:25px;height:25px;\" src=\"https://luckydraw-media.s3.amazonaws.com/img/instagram_logo.png\"></a>\n"
        + "                                                    \n"
        + "                                                    \n"
        + "                                                </p>\n"
        + "                                            \n"
        + "                                            <p><b>응모 기간</b><br>\n"
        + "                                                07월 15일 08:00 까지</p>\n"
        + "                                            \n"
        + "                                            \n"
        + "                                            \n"
        + "                                            \n"
        + "                                            <p><b>메모</b></p>\n"
        + "                                            <textarea class=\"form-control\" rows=\"3\" id=\"memo_5691\" maxlength=\"64\" tabindex=\"20\"></textarea>\n"
        + "                                            <span style=\"color:red;font-size:.8em\">* 응모한 사이즈, 이용한 배대지 등 메모를 남겨보세요!</span>\n"
        + "                                            <div style=\"text-align: center;padding-top:20px\">\n"
        + "                                                \n"
        + "                                                    \n"
        + "                                                \n"
        + "                                                <div class=\"btn btn-dark\" id=\"memo_save_5691\" tabindex=\"21\" onclick=\"javascript:change_memo(5691);\">\n"
        + "                                                    저장\n"
        + "                                                </div>\n"
        + "                                                <div class=\"btn btn-dark\" tabindex=\"22\" onclick=\"javascript:modal_close(5691);\">\n"
        + "                                                    닫기\n"
        + "                                                </div>\n"
        + "                                            </div>\n"
        + "                                        </div>\n"
        + "                                    </div>\n"
        + "                                </div>\n"
        + "                            </div>\n"
        + "                        </div>\n"
        + "                    \n"
        + "                    \n"
        + "                    <div class=\"ln_solid\"></div>\n"
        + "                    <h3 class=\"site_info_title\">오프라인 선착순</h3>\n"
        + "                    \n"
        + "                    \n"
        + "                        <h5 class=\"empty_text\">오프라인 선착순 발매 정보가 없습니다</h5>\n"
        + "                    \n"
        + "                    <h3 class=\"site_info_title\">오프라인 응모</h3>\n"
        + "                    \n"
        + "                    \n"
        + "                        <h5 class=\"empty_text\">오프라인 응모 발매 정보가 없습니다</h5>\n"
        + "                    \n"
        + "                </div>");
  }

  @Test
  public void testConstructor() throws IOException {
    String url = "testUrl";
    Document expectedDocument = new Document(url);
    Mockito.doReturn(expectedDocument).when(jsoup).getDocument(url);
    Document result = siteCardParser.getDoc();

    Assert.assertEquals(result, expectedDocument);
  }
  @Test
  public void testConstructorWhenIllegalArgumentUrl() throws IOException {
    String result = "";
    try {
      siteCardParser = new RaffleElementsParserForLuckD("",jsoup);
    } catch(IllegalArgumentException iae) {
      result = iae.getMessage();
    }

    Assert.assertEquals(result, "url은 빈 문자열 또는 null이 될 수 없습니다.");
  }

  @Test
  public void testConstructorWhenIllegalArgumentJsoup() throws IOException {
    String result = "";
    try {
      siteCardParser = new RaffleElementsParserForLuckD("testUrl",null);
    } catch(IllegalArgumentException iae) {
      result = iae.getMessage();
    }

    Assert.assertEquals(result, "jsoup은 null이 될 수 없습니다.");
  }

  @Test
  public void testGetActiveSiteCards() throws IOException {
    String url = "testUrl";
    Mockito.doReturn(testDocument).when(jsoup).getDocument(url);
    Elements elements = siteCardParser.getTargetStoreElements(url, "컨버스 코리아");

    System.out.println(elements);
    int result = elements.size();

    Assert.assertEquals(1, result);
  }
/*
  @Test
  public void testGetCountry() throws IOException {
    String result = siteCardParser.getTargetStoreElements(new Element("div").append("<p>한국 </p>"));
    Assert.assertEquals("한국",result);

    result = siteCardParser.getCountry(new Element("div").append("<p>프랑스 / 배대지 </p>"));
    Assert.assertEquals("프랑스",result);
  }

  @Test
  public void testGetDelivery() throws IOException {
    String result = siteCardParser.getDelivery(new Element("div").append("<p>프랑스 / 배대지 </p>"));
    Assert.assertEquals("배대지",result);

    result = siteCardParser.getDelivery(new Element("div").append("<p>프랑스 / 방문 구매 </p>"));
    Assert.assertEquals("방문 구매",result);
  }

  @Test
  public void testGetRaffleUrl(){
    String result = siteCardParser.getRaffleUrl(new Element("div").append("<a target=\"_blank\" href=\"https://www.converse.co.kr/limited/moncler_teasing.html?utm_source=luck-d\" class=\"btn btn-dark btn-raffle\" onclick=\"javascript:additional_info_click(&quot;5772&quot;);\"> 바로가기 </a>"));
    Assert.assertEquals("https://www.converse.co.kr/limited/moncler_teasing.html?utm_source=sneakalarm", result);

    result = siteCardParser.getRaffleUrl(new Element("div").append("<a target=\"_blank\" href=\"https://www.converse.co.kr/limited/moncler_teasing.html\" class=\"btn btn-dark btn-raffle\" onclick=\"javascript:additional_info_click(&quot;5772&quot;);\"> 바로가기 </a>"));
    Assert.assertEquals("https://www.converse.co.kr/limited/moncler_teasing.html", result);
  }

  @Test
  public void testGetRaffleEndDateTime(){
    String result = siteCardParser.getRaffleEndDateTime(new Element("div").append("<p class=\"release_date_time\">07월 18일 18:00 까지</p>"));
    Assert.assertEquals("07-18 18:00", result);
  }

 */
}