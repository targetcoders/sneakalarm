package com.sneakalarm.product.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sneakalarm.product.dto.ProductVO;

@Mapper
public interface ProductMapper {
  public void insertProduct(ProductVO productVO);

  public List<ProductVO> getProductList(String code);

  public void deleteProduct(String code);

  public void updateProduct(ProductVO productVO);
}
