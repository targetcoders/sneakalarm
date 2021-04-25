package com.sneakalarm.product.service;

import com.sneakalarm.product.dto.UpdateDeliveryTypesVO;
import com.sneakalarm.product.dto.UpdateDrawCountriesVO;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.sneakalarm.product.dto.ProductUpdateDrawNumVO;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStatusVO;
import com.sneakalarm.product.dto.ProductVO;

public interface ProductService {
  List<ProductCardVO> getProductCardList();
  
  boolean insertProduct(ProductInsertVO productInsertVO) throws Exception;

  List<ProductVO> getProductList(String id);

  List<Integer> getProductIdListAll() throws ParseException, Exception;
  
  List<ProductCardVO> getProductCardListByKeyword(String keyword);
  
  List<String> getProductIdListByStatus(String stat);
  
  boolean deleteProduct(String id);

  void updateProduct(ProductInsertVO productInsertVO);

  void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO);

  void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO);
  
  void updateDrawNum(ProductUpdateDrawNumVO productUpdateDrawNumVO);
  
  void updateProductStatus(ProductUpdateStatusVO productUpdateStatusVO);

  void updateDrawCountries(UpdateDrawCountriesVO updateDrawCountriesVO);

  void updateDeliveryTypes(UpdateDeliveryTypesVO updateDeliveryTypesVO);

  ArrayList<ProductVO> getProductByDeliveryType(String deliveryType);

  ArrayList<TodayProductResponseVO> getTodayProductResponseVO();
}
