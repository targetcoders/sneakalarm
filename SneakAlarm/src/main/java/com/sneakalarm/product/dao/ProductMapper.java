package com.sneakalarm.product.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductVO;

@Mapper
public interface ProductMapper {
  public void insertProduct(ProductVO productVO);

  public List<ProductVO> getProductList(String id);

  public void deleteProduct(String id);

  public void updateProduct(ProductVO productVO);

  public void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO);

  public void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO);
}
