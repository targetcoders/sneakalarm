package com.sneakalarm.product.service;

import java.util.List;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductVO;

public interface ProductService {
  List<ProductCardVO> getProductCardList();

  void insertProduct(ProductInsertVO productInsertVO) throws Exception;

  List<ProductVO> getProductList(String code);
}
