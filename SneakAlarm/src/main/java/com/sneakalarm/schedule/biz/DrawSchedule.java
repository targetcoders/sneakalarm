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
@Slf4j
public class DrawSchedule {
  
  @Autowired
  ProductService productService;
  @Autowired
  RaffleService raffleService;
  @Autowired
  DateUtil dateUtil;
  
  @Scheduled(fixedDelay = 20000)
  public void DrawStatusNumSynchronize() throws ParseException, Exception {
    log.debug("updateDrawNum");
    ArrayList<Integer> idListAll = (ArrayList<Integer>) productService.getProductIdListAll();
    InsertDrawVO insertDrawVO = new InsertDrawVO(0,0,0);
    
    for(Integer productId : idListAll) {
      ArrayList<RaffleCardVO> raffleCardList = raffleService.getRaffleCardList(productId.toString());
      
      for(RaffleCardVO raffleCardVO : raffleCardList) {
        String startDate = raffleCardVO.getStartDate();
        String endDate = raffleCardVO.getEndDate();
        String startTime = raffleCardVO.getStartTime();
        String endTime = raffleCardVO.getEndTime();
        String status = raffleService.getRaffleStatus(startDate, startTime, endDate, endTime);
        
        String country = raffleCardVO.getCountry();
        if(!status.equals(ProductConst.STATUS_ENDED)) {
          if(country.equals("한국"))
            insertDrawVO.setDrawNumKorea(insertDrawVO.getDrawNumKorea()+1);
          else
            insertDrawVO.setDrawNumOverseas(insertDrawVO.getDrawNumOverseas()+1);
        }
      }
      insertDrawVO.setProductId(productId);
      productService.updateDrawNum(insertDrawVO);
      if(insertDrawVO.getDrawNumKorea() == 0 && insertDrawVO.getDrawNumOverseas() == 0)
        productService.updateProductStatus(new ProductUpdateStatusVO(productId,ProductConst.STATUS_ENDED));
      else 
        productService.updateProductStatus(new ProductUpdateStatusVO(productId,ProductConst.STATUS_ACTIVE));
    }
    
    log.debug("updateDrawNum - end");
    return;
  }
}
