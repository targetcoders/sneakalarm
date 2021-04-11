package com.sneakalarm.product.service;

import java.text.ParseException;
import java.util.List;
import com.sneakalarm.product.dto.InsertDrawVO;
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
  
  List<Integer> getProductIdListByStatus(String stat);
  
  boolean deleteProduct(String id);

  void updateProduct(ProductInsertVO productInsertVO);

  void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO);

  void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO);
  
  void updateDrawNum(InsertDrawVO insertDrawVO);
  
  void updateProductStatus(ProductUpdateStatusVO productUpdateStatusVO);
}
