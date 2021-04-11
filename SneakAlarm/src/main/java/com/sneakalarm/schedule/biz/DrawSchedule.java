package com.sneakalarm.schedule.biz;

import java.text.ParseException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sneakalarm.product.ProductConst;
import com.sneakalarm.product.dto.InsertDrawVO;
import com.sneakalarm.product.dto.ProductUpdateStatusVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.service.RaffleService;
import com.sneakalarm.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Component
public class DrawSchedule {
  
  @Autowired
  ProductService productService;
  @Autowired
  RaffleService raffleService;
  @Autowired
  DateUtil dateUtil;
  
  @Scheduled(fixedDelay = 100000)
  public void DrawStatusNumSynchronize() throws ParseException, Exception {
    ArrayList<Integer> idListAll = (ArrayList<Integer>) productService.getProductIdListAll();
    InsertDrawVO insertDrawVO = new InsertDrawVO(0,0,0,0);
    
    for(Integer productId : idListAll) {
      insertDrawVO.setDrawNumKorea(0);
      insertDrawVO.setDrawNumOverseas(0);
      insertDrawVO.setDrawNumFirstcome(0);
      ArrayList<RaffleCardVO> raffleCardList = raffleService.getRaffleCardList(productId.toString());
      
      for(RaffleCardVO raffleCardVO : raffleCardList) {
        String startDate = raffleCardVO.getStartDate();
        String endDate = raffleCardVO.getEndDate();
        String startTime = raffleCardVO.getStartTime();
        String endTime = raffleCardVO.getEndTime();
        String status = raffleService.getRaffleStatus(startDate, startTime, endDate, endTime);
        String country = raffleCardVO.getCountry();
        String raffleType = raffleCardVO.getRaffleType();
        
        if(!status.equals(ProductConst.STATUS_ENDED) && raffleType.equals("선착순"))
          insertDrawVO.setDrawNumFirstcome(insertDrawVO.getDrawNumFirstcome()+1);
        else if(status.equals(ProductConst.STATUS_GOING) && raffleType.equals("응모")) 
            insertDrawVO = setGoingDrawNum(insertDrawVO, country);
      }
      insertDrawVO.setProductId(productId);
      productService.updateDrawNum(insertDrawVO);
      if(insertDrawVO.getDrawNumKorea() == 0 && insertDrawVO.getDrawNumOverseas() == 0)
        productService.updateProductStatus(new ProductUpdateStatusVO(productId,ProductConst.STATUS_ENDED));
      else 
        productService.updateProductStatus(new ProductUpdateStatusVO(productId,ProductConst.STATUS_ACTIVE));
    }
  }
  
  private InsertDrawVO setGoingDrawNum(InsertDrawVO insertDrawVO, String country) {
    if(country.equals("한국"))
      insertDrawVO.setDrawNumKorea(insertDrawVO.getDrawNumKorea()+1);
    else
      insertDrawVO.setDrawNumOverseas(insertDrawVO.getDrawNumOverseas()+1);
    return insertDrawVO;
  }
}
