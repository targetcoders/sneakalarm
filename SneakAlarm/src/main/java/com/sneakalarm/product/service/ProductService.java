package com.sneakalarm.product.service;

import java.util.List;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductVO;

public interface ProductService {
  List<ProductCardVO> getProductCardList();

  boolean insertProduct(ProductInsertVO productInsertVO) throws Exception;

  List<ProductVO> getProductList(String id);

  boolean deleteProduct(String id);

  void updateProduct(ProductInsertVO productInsertVO);

  void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO);

  void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO);

}
