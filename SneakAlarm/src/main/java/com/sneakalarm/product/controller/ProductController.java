package com.sneakalarm.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sneakalarm.product.ProductConst;
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

  @Autowired
  ProductConst productConst;
  @Autowired
  ProductAscending productAscendingInstance;
  @Autowired
  FirstcomeAscending firstcomeAscendingInstance;
  @Autowired
  RaffleAscending raffleAscendingInstance;

  @GetMapping("/")
  public String getProductCardList(Model model) throws ParseException {
    ArrayList<ProductCardVO> list =
        (ArrayList<ProductCardVO>) productServiceImpl.getProductCardList();
    ArrayList<ProductCardVO> readyCardList = new ArrayList<ProductCardVO>();
    ArrayList<ProductCardVO> goingCardList = new ArrayList<ProductCardVO>();
    ArrayList<ProductCardVO> endedCardList = new ArrayList<ProductCardVO>();

    for (ProductCardVO card : list) {
      String status = card.getStatus();
      switch (status) {
        case ProductConst.STATUS_READY:
          readyCardList.add(card);
          break;
        case ProductConst.STATUS_GOING:
          goingCardList.add(card);
          break;
        case ProductConst.STATUS_ENDED:
          endedCardList.add(card);
          break;
        default:
          break;
      }
    }
    Collections.sort(readyCardList, productAscendingInstance);
    Collections.sort(goingCardList, productAscendingInstance);
    
    model.addAttribute("goingCardListNum", goingCardList.size());
    model.addAttribute("readyCardListNum", readyCardList.size());
    model.addAttribute("readyCardList", readyCardList);
    model.addAttribute("goingCardList", goingCardList);
    model.addAttribute("endedCardList", endedCardList);
    return "views/home";
  }
  
	@GetMapping("/getEndedProductCardList")
	@ResponseBody
	public List<ProductCardVO> getProductCardListByPage(@Param("page") Integer page, Model model) {
		ArrayList<ProductCardVO> list = (ArrayList<ProductCardVO>) productServiceImpl.getProductCardList();
		ArrayList<ProductCardVO> endedCardList = new ArrayList<ProductCardVO>();
		for (ProductCardVO card : list) {
			String status = card.getStatus();
			if (status.equals(ProductConst.STATUS_ENDED))
				endedCardList.add(card);
		}
		
		int startIndex = page * 9;
		int endIndex = endedCardList.size();
		
		if (endIndex <= startIndex) {
			return Collections.emptyList();
		} else {
			List<ProductCardVO> subList = endedCardList.subList(startIndex, endIndex);
			if (endIndex-startIndex <= 9)
				return subList;
			else
				return subList.subList(0, 9);
		}
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

    ArrayList<RaffleCardVO> readyRaffleRet_agent = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingRaffleRet_agent = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> readyFirstcomeRet_agent = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingFirstcomeRet_agent = new ArrayList<RaffleCardVO>();

    ArrayList<RaffleCardVO> readyRaffleRet_direct = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingRaffleRet_direct = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> readyFirstcomeRet_direct = new ArrayList<RaffleCardVO>();
    ArrayList<RaffleCardVO> goingFirstcomeRet_direct = new ArrayList<RaffleCardVO>();

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

      String delivery = raffleCardVO.getDelivery();
      if (raffleCardVO.getRaffleType().equals("응모")) {
        if (status.equals(ProductConst.STATUS_ENDED)) {
          endedRaffleRet.add(raffleCardVO);
        } else if (status.equals(ProductConst.STATUS_READY)) {
          if (delivery.equals("직배"))
            readyRaffleRet_direct.add(raffleCardVO);
          else if (delivery.equals("배대지"))
            readyRaffleRet_agent.add(raffleCardVO);
          else
            readyRaffleRet.add(raffleCardVO);
        } else {
          if (raffleCardVO.getDelivery().equals("직배"))
            goingRaffleRet_direct.add(raffleCardVO);
          else if (delivery.equals("배대지"))
            goingRaffleRet_agent.add(raffleCardVO);
          else
            goingRaffleRet.add(raffleCardVO);
        }
      } else {
        if (status.equals(ProductConst.STATUS_ENDED)) {
          endedFirstcomeRet.add(raffleCardVO);
        } else if (status.equals(ProductConst.STATUS_READY)) {
          if (delivery.equals("직배"))
            readyFirstcomeRet_direct.add(raffleCardVO);
          else if (delivery.equals("배대지"))
            readyFirstcomeRet_agent.add(raffleCardVO);
          else
            readyFirstcomeRet.add(raffleCardVO);
        } else {
          if (delivery.equals("직배"))
            goingFirstcomeRet_direct.add(raffleCardVO);
          else if (delivery.equals("배대지"))
            goingFirstcomeRet_agent.add(raffleCardVO);
          else
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

    Collections.sort(goingRaffleRet, raffleAscendingInstance);
    Collections.sort(goingRaffleRet_agent, raffleAscendingInstance);
    Collections.sort(goingRaffleRet_direct, raffleAscendingInstance);
    Collections.sort(readyRaffleRet, raffleAscendingInstance);
    Collections.sort(readyRaffleRet_agent, raffleAscendingInstance);
    Collections.sort(readyRaffleRet_direct, raffleAscendingInstance);
    Collections.sort(endedRaffleRet, raffleAscendingInstance);
    Collections.sort(goingFirstcomeRet, firstcomeAscendingInstance);
    Collections.sort(goingFirstcomeRet_agent, firstcomeAscendingInstance);
    Collections.sort(goingFirstcomeRet_direct, firstcomeAscendingInstance);
    Collections.sort(readyFirstcomeRet, firstcomeAscendingInstance);
    Collections.sort(readyFirstcomeRet_agent, firstcomeAscendingInstance);
    Collections.sort(readyFirstcomeRet_direct, firstcomeAscendingInstance);
    Collections.sort(endedFirstcomeRet, firstcomeAscendingInstance);

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


    model.addAttribute("goingRaffleList_direct", goingRaffleRet_direct);
    model.addAttribute("goingRaffleList_agent", goingRaffleRet_agent);
    model.addAttribute("readyRaffleList_direct", readyRaffleRet_direct);
    model.addAttribute("readyRaffleList_agent", readyRaffleRet_agent);
    model.addAttribute("goingFirstcomeList_direct", goingFirstcomeRet_direct);
    model.addAttribute("goingFirstcomeList_agent", goingFirstcomeRet_agent);
    model.addAttribute("readyFirstcomeList_direct", readyFirstcomeRet_direct);
    model.addAttribute("readyFirstcomeList_agent", readyFirstcomeRet_agent);

    model.addAttribute("goingRaffleNum_direct", goingRaffleRet_direct.size());
    model.addAttribute("goingRaffleNum_agent", goingRaffleRet_agent.size());
    model.addAttribute("readyRaffleNum_direct", readyRaffleRet_direct.size());
    model.addAttribute("readyRaffleNum_agent", readyRaffleRet_agent.size());
    model.addAttribute("goingFirstcomeNum_direct", goingFirstcomeRet_direct.size());
    model.addAttribute("goingFirstcomeNum_agent", goingFirstcomeRet_agent.size());
    model.addAttribute("readyFirstcomeNum_direct", readyFirstcomeRet_direct.size());
    model.addAttribute("readyFirstcomeNum_agent", readyFirstcomeRet_agent.size());

    model.addAttribute("drawStartDateTime", drawStartDateTime);
    model.addAttribute("drawEndDateTime", drawEndDateTime);
    return "views/product-detail";
  }

  public String getDrawStartDateTime(ArrayList<RaffleCardVO> raffleCardVOList) {
    String drawStartDateTime = "9999-99-99";
    for (RaffleCardVO raffleCardVO : raffleCardVOList) {
      if (raffleCardVO.getStatus().equals("ended"))
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
      if (raffleCardVO.getStatus().equals("ended"))
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
    SimpleDateFormat sdf = new SimpleDateFormat(ProductConst.DATE_FORMAT.replaceAll("/", "-"));
    Date nowDateTime = new Date();
    Date startDateTime = sdf.parse(startDate + " " + startTime);
    Date endDateTime = sdf.parse(endDate + " " + endTime);
    
    int res = nowDateTime.compareTo(endDateTime);
    if (res > 0) {
      status = ProductConst.STATUS_ENDED;
    } else if (res <= 0) {
      if (nowDateTime.compareTo(startDateTime) < 0) {
        status = ProductConst.STATUS_READY;
      } else {
        status = ProductConst.STATUS_GOING;
      }
    }
    return status;
  }

  public String getWeek(String date, String dateType) throws Exception {
    String[] day = {"일", "월", "화", "수", "목", "금", "토"};

    SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
    Date inputDate = dateFormat.parse(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(inputDate);

    int dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
    return day[dayNum];
  }
  
  @GetMapping("/getProductCardListByKeyword")
  @ResponseBody
  public List<ProductCardVO> searchProduct(@Param("keyword") String keyword, Model model) {
	  if(keyword.equals("")) {
		  return Collections.emptyList();
	  }
	  ArrayList<ProductCardVO> productCardVOList =
		        (ArrayList<ProductCardVO>) productServiceImpl.getProductCardListByKeyword(keyword);
	  productCardVOList.sort(productAscendingInstance);
	  return productCardVOList;
  }

  @Component
  public class RaffleAscending implements Comparator<RaffleCardVO> {
    @Override
    public int compare(RaffleCardVO o1, RaffleCardVO o2) {
      String endDateTime1 = o1.getEndDate() + o1.getEndTime();
      String endDateTime2 = o2.getEndDate() + o2.getEndTime();
      return endDateTime1.compareTo(endDateTime2);
    }
  }
  @Component
  public class FirstcomeAscending implements Comparator<RaffleCardVO> {
	@Override
	public int compare(RaffleCardVO o1, RaffleCardVO o2) {
		String startDateTime1 = o1.getStartDate() + o1.getStartTime();
		String startDateTime2 = o2.getStartDate() + o2.getStartTime();
		return startDateTime1.compareTo(startDateTime2);
	}
  }
  @Component
  public class ProductAscending implements Comparator<ProductCardVO> {
    @Override
    public int compare(ProductCardVO o1, ProductCardVO o2) {
      String endDateTime1 = o1.getReleaseEndDate() + o1.getReleaseEndDate();
      String endDateTime2 = o2.getReleaseEndDate() + o2.getReleaseEndDate();
      return endDateTime1.compareTo(endDateTime2);
    }
  }
}
