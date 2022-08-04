package com.sneakalarm.product.dao;

import com.sneakalarm.product.dto.UpdateDeliveryTypesVO;
import com.sneakalarm.product.dto.UpdateDrawCountriesVO;
import com.sneakalarm.today.dto.TodayGetProductByDeliveryTypeVO;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.sneakalarm.product.dto.ProductUpdateDrawNumVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStatusVO;
import com.sneakalarm.product.dto.ProductVO;

@Mapper
public interface ProductMapper {
  void insertProduct(ProductVO productVO);

  List<ProductVO> getProductList(String id);
  
  List<Integer> getProductIdListAll();
  
  List<String> getProductIdListByStatus(String status);

  void deleteProduct(String id);

  void updateProduct(ProductVO productVO);
  
  void updateProductStatus(ProductUpdateStatusVO productUpdateStatusVO);

  void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO);

  void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO);
  
  void updateDrawNum(ProductUpdateDrawNumVO productUpdateDrawNumVO);

  void updateDrawCountries(UpdateDrawCountriesVO countries);

  void updateDeliveryTypes(UpdateDeliveryTypesVO updateDeliveryTypesVO);

  ArrayList<ProductVO> getProductByDeliveryType(String delivery);

  ArrayList<TodayProductResponseVO> getTodayProductResponseVO();
}
