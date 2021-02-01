package com.sneakalarm.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.service.RaffleService;

@Controller
public class ProductController {
  @Autowired
  ProductService productServiceImpl;
  @Autowired
  RaffleService raffleService;

  @GetMapping("/")
  public String getProductCardList(Model model) throws ParseException {
    ArrayList<ProductCardVO> list =
        (ArrayList<ProductCardVO>) productServiceImpl.getProductCardList();
    ArrayList<ProductCardVO> readyCardList = new ArrayList<ProductCardVO>();
    ArrayList<ProductCardVO> goingCardList = new ArrayList<ProductCardVO>();
    ArrayList<ProductCardVO> endedCardList = new ArrayList<ProductCardVO>();

    for (ProductCardVO card : list) {
      String status = card.getStatus();
      if (status.equals("ready")) {
        readyCardList.add(card);
      }
      if (status.equals("going")) {
        goingCardList.add(card);
      }
      if (status.equals("ended")) {
        endedCardList.add(card);
      }
    }
    Collections.sort(readyCardList, new ProductAscending());
    Collections.sort(goingCardList, new ProductAscending());
    Collections.sort(endedCardList, new ProductAscending());

    model.addAttribute("readyCardList", readyCardList);
    model.addAttribute("goingCardList", goingCardList);
    model.addAttribute("endedCardList", endedCardList);
    return "views/home";
  }

  @GetMapping("/product-modify")
  public String modifyProduct(@Param("id") String id, Model model) {
    ArrayList<ProductVO> list = (ArrayList<ProductVO>) productServiceImpl.getProductList(id);
    ProductVO productVO = list.get(0);
    model.addAttribute("productVO", productVO);
    return "views/product-modify";
  }

  @PostMapping("/product-modify")
  @ResponseBody
  public boolean modifyProduct(ProductInsertVO productInsertVO) {
    productServiceImpl.updateProduct(productInsertVO);
    return true;
  }

  @GetMapping("/product-insert")
  public String insertProductForm() {
    return "views/product-insert";
  }

  @PostMapping("/product-insert")
  @ResponseBody
  public boolean insertProduct(ProductInsertVO productInsertVO) throws Exception {
    productServiceImpl.insertProduct(productInsertVO);
    return true;
  }

  @GetMapping("/product-delete")
  @ResponseBody
  public boolean productDelete(String id) {
    return productServiceImpl.deleteProduct(id);
  }

  @GetMapping("/product-detail")
  public String getProductDetails(@Param("id") String id, Model model) throws Exception {
    ArrayList<ProductVO> productVOList =
        (ArrayList<ProductVO>) productServiceImpl.getProductList(id);
    if (productVOList.size() == 0) {
      return "/";
    }

    ProductVO productVO = productVOList.get(0);
    String lastUpdateDate = productVO.getLastUpdateDate();
    String[] lastUpdateDateList = lastUpdateDate.split(" ");
    productVO.setLastUpdateDate(lastUpdateDateList[0]);
    String[] urlArray = productVO.getImgSrc_detail().split(",");
    ArrayList<RaffleCardVO> raffleCardList = raffleService.getRaffleCardList(id);
    ArrayList<RaffleCardVO> endedRaffleRet = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> readyRaffleRet = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingRaffleRet = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> endedFirstcomeRet = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> readyFirstcomeRet = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingFirstcomeRet = new ArrayList<RaffleCardVO>();

    for (RaffleCardVO raffleCardVO : raffleCardList) {
      String startDate = raffleCardVO.getStartDate();
      String endDate = raffleCardVO.getEndDate();
      String startTime = raffleCardVO.getStartTime();
      String endTime = raffleCardVO.getEndTime();
      String status = getRaffleStatus(startDate, startTime, endDate, endTime);
      String startWeek = getWeek(startDate, "yyyy-MM-dd");
      String endWeek = getWeek(endDate, "yyyy-MM-dd");

      raffleCardVO.setStartDate(startDate.replaceAll("-", "/") + " " + startWeek);
      raffleCardVO.setEndDate(endDate.replaceAll("-", "/") + " " + endWeek);
      raffleCardVO.setStartTime(startTime.substring(0, 5));
      raffleCardVO.setEndTime(endTime.substring(0, 5));
      raffleCardVO.setStatus(status);

      if (raffleCardVO.getRaffleType().equals("응모")) {
        if (status.equals("종료")) {
          endedRaffleRet.add(raffleCardVO);
        } else if (status.equals("시작전")) {
          readyRaffleRet.add(raffleCardVO);
        } else {
          goingRaffleRet.add(raffleCardVO);
        }
      } else {
        if (status.equals("종료")) {
          endedFirstcomeRet.add(raffleCardVO);
        } else if (status.equals("시작전")) {
          readyFirstcomeRet.add(raffleCardVO);
        } else {
          goingFirstcomeRet.add(raffleCardVO);
        }
      }
    }

    String drawStartDateTime = getDrawStartDateTime(raffleCardList);
    String drawEndDateTime = getDrawEndDateTime(raffleCardList);

    ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO = new ProductUpdateStartDateTimeVO();
    ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO = new ProductUpdateEndDateTimeVO();

    productUpdateStartDateTimeVO.setProductId(productVO.getId());
    productUpdateStartDateTimeVO.setStartDateTime(drawStartDateTime);
    productUpdateEndDateTimeVO.setProductId(productVO.getId());
    productUpdateEndDateTimeVO.setEndDateTime(drawEndDateTime);

    productServiceImpl.updateStartDateTime(productUpdateStartDateTimeVO);
    productServiceImpl.updateEndDateTime(productUpdateEndDateTimeVO);

    Collections.sort(goingRaffleRet, new DrawAscending());
    Collections.sort(readyRaffleRet, new DrawAscending());
    Collections.sort(endedRaffleRet, new DrawAscending());
    Collections.sort(goingFirstcomeRet, new DrawAscending());
    Collections.sort(readyFirstcomeRet, new DrawAscending());
    Collections.sort(endedFirstcomeRet, new DrawAscending());

    model.addAttribute("productVO", productVO);
    model.addAttribute("urlList_detail", urlArray);

    model.addAttribute("goingRaffleList", goingRaffleRet);
    model.addAttribute("readyRaffleList", readyRaffleRet);
    model.addAttribute("endedRaffleList", endedRaffleRet);
    model.addAttribute("goingFirstcomeList", goingFirstcomeRet);
    model.addAttribute("readyFirstcomeList", readyFirstcomeRet);
    model.addAttribute("endedFirstcomeList", endedFirstcomeRet);

    model.addAttribute("goingRaffleNum", goingRaffleRet.size());
    model.addAttribute("readyRaffleNum", readyRaffleRet.size());
    model.addAttribute("endedRaffleNum", endedRaffleRet.size());
    model.addAttribute("goingFirstcomeNum", goingFirstcomeRet.size());
    model.addAttribute("readyFirstcomeNum", readyFirstcomeRet.size());
    model.addAttribute("endedFirstcomeNum", endedFirstcomeRet.size());

    model.addAttribute("drawStartDateTime", drawStartDateTime);
    model.addAttribute("drawEndDateTime", drawEndDateTime);
    return "views/product-detail";
  }

  public String getDrawStartDateTime(ArrayList<RaffleCardVO> raffleCardVOList) {
    String drawStartDateTime = "9999-99-99";
    for (RaffleCardVO raffleCardVO : raffleCardVOList) {
      if (raffleCardVO.getStatus().equals("종료"))
        continue;
      String startDate = raffleCardVO.getStartDate();
      String startTime = raffleCardVO.getStartTime();
      String startDateTime = startDate + " " + startTime;
      if (startDateTime.compareTo(drawStartDateTime) < 0) {
        drawStartDateTime = startDateTime;
      }
    }
    if (drawStartDateTime.equals("9999-99-99")) {
      drawStartDateTime = "";
    }
    return drawStartDateTime;
  }

  public String getDrawEndDateTime(ArrayList<RaffleCardVO> raffleCardVOList) {
    String drawEndDateTime = "";
    for (RaffleCardVO raffleCardVO : raffleCardVOList) {
      if (raffleCardVO.getStatus().equals("종료"))
        continue;
      String endDate = raffleCardVO.getEndDate();
      String endTime = raffleCardVO.getEndTime();
      String endDateTime = endDate + " " + endTime;
      if (endDateTime.compareTo(drawEndDateTime) >= 0) {
        drawEndDateTime = endDateTime;
      }
    }

    if (drawEndDateTime.equals("")) {
      drawEndDateTime = "RELEASING SOON";
    }
    return drawEndDateTime;
  }

  public String getRaffleStatus(String startDate, String startTime, String endDate, String endTime)
      throws ParseException {
    String status = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date nowDateTime = new Date();
    Date startDateTime = sdf.parse(startDate + " " + startTime);
    Date endDateTime = sdf.parse(endDate + " " + endTime);

    int res = nowDateTime.compareTo(endDateTime);
    if (res > 0) {
      status = "종료";
    } else if (res <= 0) {
      if (nowDateTime.compareTo(startDateTime) < 0) {
        status = "시작전";
      } else {
        status = "진행중";
      }
    }
    return status;
  }

  public boolean isEndedProductCard(String endDate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date nowDateTime = new Date();
    Date endDateTime = sdf.parse(endDate);

    int res = nowDateTime.compareTo(endDateTime);
    if (res >= 0) {
      return true;
    }
    return false;
  }

  public String getWeek(String date, String dateType) throws Exception {

    String day = "";

    SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
    Date nDate = dateFormat.parse(date);

    Calendar cal = Calendar.getInstance();
    cal.setTime(nDate);

    int dayNum = cal.get(Calendar.DAY_OF_WEEK);

    switch (dayNum) {
      case 1:
        day = "일";
        break;
      case 2:
        day = "월";
        break;
      case 3:
        day = "화";
        break;
      case 4:
        day = "수";
        break;
      case 5:
        day = "목";
        break;
      case 6:
        day = "금";
        break;
      case 7:
        day = "토";
        break;
    }

    return day;
  }

  public class DrawAscending implements Comparator<RaffleCardVO> {
    @Override
    public int compare(RaffleCardVO o1, RaffleCardVO o2) {
      String endDateTime1 = o1.getEndDate() + o1.getEndTime();
      String endDateTime2 = o2.getEndDate() + o2.getEndTime();
      return endDateTime1.compareTo(endDateTime2);
    }
  }

  public class ProductAscending implements Comparator<ProductCardVO> {
    @Override
    public int compare(ProductCardVO o1, ProductCardVO o2) {
      String endDateTime1 = o1.getReleaseEndDate() + o1.getReleaseEndDate();
      String endDateTime2 = o2.getReleaseEndDate() + o2.getReleaseEndDate();
      return endDateTime1.compareTo(endDateTime2);
    }
  }
}
