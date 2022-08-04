package com.sneakalarm.product.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sneakalarm.product.dto.ProductCardVO;

@Mapper
public interface ProductCardMapper {
  List<ProductCardVO> getProductCardList();
  ArrayList<ProductCardVO> getProductCardListByKeyword(String keyword);
  ArrayList<ProductCardVO> getProductCardListByModelKeyword(String keyword);
}
