package com.sneakalarm.schedule.biz;

import com.sneakalarm.product.ProductConst;
import com.sneakalarm.product.dto.ProductUpdateDrawNumVO;
import com.sneakalarm.product.dto.ProductUpdateStatusVO;
import com.sneakalarm.product.dto.UpdateDeliveryTypesVO;
import com.sneakalarm.product.dto.UpdateDrawCountriesVO;
import com.sneakalarm.product.service.ProductService;
import com.sneakalarm.raffle.RaffleConst;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.raffle.service.RaffleService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSchedule {

  @Autowired
  ProductService productService;
  @Autowired
  RaffleService raffleService;

  public void DrawStatusNumSynchronize() throws Exception {
    ProductUpdateDrawNumVO productUpdateDrawNumVO = new ProductUpdateDrawNumVO(0,0,0,0);
    ArrayList<Integer> idListAll = (ArrayList<Integer>) productService.getProductIdListAll();

    for (Integer productId : idListAll) {
      productUpdateDrawNumVO = getProductUpdateDrawNumVO(productUpdateDrawNumVO, productId);
      productService.updateDrawNum(productUpdateDrawNumVO);
      updateProductStatus(productUpdateDrawNumVO, productId);
    }
  }

  private ProductUpdateDrawNumVO getProductUpdateDrawNumVO(
      ProductUpdateDrawNumVO productUpdateDrawNumVO, Integer productId) {
    productUpdateDrawNumVO.initProductUpdateDrawNumVO(0, 0, 0, productId);
    ArrayList<RaffleCardVO> raffleCardList = raffleService
        .getRaffleCardList(productId.toString());

    for (RaffleCardVO raffleCardVO : raffleCardList) {
      String status = raffleService.getDrawStatus(raffleCardVO.getId());
      String country = raffleCardVO.getCountry();
      String raffleType = raffleCardVO.getRaffleType();

      if (!status.equals(ProductConst.STATUS_ENDED) && raffleType.equals("선착순")) {
        productUpdateDrawNumVO
            .setDrawNumFirstcome(productUpdateDrawNumVO.getDrawNumFirstcome() + 1);
      } else if (status.equals(ProductConst.STATUS_ACTIVE) && raffleType.equals("응모")) {
        productUpdateDrawNumVO = setGoingDrawNum(productUpdateDrawNumVO, country);
      }
    }
    return productUpdateDrawNumVO;
  }

  private ProductUpdateDrawNumVO setGoingDrawNum(ProductUpdateDrawNumVO productUpdateDrawNumVO, String country) {
    if(country.equals("한국"))
      productUpdateDrawNumVO.setDrawNumKorea(productUpdateDrawNumVO.getDrawNumKorea()+1);
    else
      productUpdateDrawNumVO.setDrawNumOverseas(productUpdateDrawNumVO.getDrawNumOverseas()+1);
    return productUpdateDrawNumVO;
  }

  private void updateProductStatus(ProductUpdateDrawNumVO productUpdateDrawNumVO, Integer productId) {
    if(productUpdateDrawNumVO.getDrawNumKorea() == 0 && productUpdateDrawNumVO.getDrawNumOverseas() == 0)
      productService.updateProductStatus(new ProductUpdateStatusVO(productId, ProductConst.STATUS_ENDED));
    else
      productService.updateProductStatus(new ProductUpdateStatusVO(productId,ProductConst.STATUS_ACTIVE));
  }


  public void updateDeliveryTypes() {
    List<String> activeProductIdList = productService
        .getProductIdListByStatus(ProductConst.STATUS_ACTIVE);

    for(String activeProductId : activeProductIdList){
      ArrayList<RaffleVO> activeRaffleList = raffleService
          .getRaffleListByStatus(new RaffleListByStatusVO(activeProductId, ProductConst.STATUS_ACTIVE));
      Set<String> deliveryTypeSet = getDeliveryTypeSet(activeRaffleList);
      String deliveryTypes = String.join(",",deliveryTypeSet);
      productService.updateDeliveryTypes(new UpdateDeliveryTypesVO(activeProductId,deliveryTypes));
    }
  }

  private Set<String> getDeliveryTypeSet(ArrayList<RaffleVO> activeRaffleList) {
    Set<String> deliveryTypeSet = new HashSet<>();
    for(RaffleVO raffle : activeRaffleList){
      String deliveryType = getDeliveryType(raffle.getDelivery());
      deliveryTypeSet.add(deliveryType);
    }

    return deliveryTypeSet;
  }

  private String getDeliveryType(String deliveryType) {
    return (RaffleConst.DELIVERY_PACKAGE.equals(deliveryType) || RaffleConst.DELIVERY_VISIT
        .equals(deliveryType)) ? RaffleConst.DELIVERY_KOREA
        : deliveryType;
  }
}
