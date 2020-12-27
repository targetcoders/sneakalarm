package com.sneakalarm.product.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sneakalarm.product.dto.ProductCardVO;

@Mapper
public interface ProductCardMapper {
  List<ProductCardVO> getProductCardList();
}
