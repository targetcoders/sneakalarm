package com.sneakalarm.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;

@Controller
public class ProductController {

  @Autowired
  ProductService productServiceImpl;

  @GetMapping("/home")
  public String getProductCardList(Model model) {
    ArrayList<ProductCardVO> list =
        (ArrayList<ProductCardVO>) productServiceImpl.getProductCardList();
    model.addAttribute("list", list);
    return "views/home";
  }

  @GetMapping("/product-insert")
  public String insertProductForm() {
    return "views/product-insert";
  }

  @PostMapping("/product-insert")
  public String insertProduct(ProductInsertVO productInsertVO) throws IOException {
    try {
      productServiceImpl.insertProduct(productInsertVO);
    } catch (Exception e) {
      e.printStackTrace();
      return "views/home";
    }
    return "views/home";
  }

  @GetMapping("/product-details")
  public String getProductDetails(@Param("code") String code, Model model) {
    ArrayList<ProductVO> productVOList =
        (ArrayList<ProductVO>) productServiceImpl.getProductList(code);
    ProductVO productVO = productVOList.get(0);
    String[] urlArray = productVO.getImgSrc().split(",");
    ArrayList<String> urlList = new ArrayList<String>();
    for (String url : urlArray) {
      urlList.add(url);
    }
    model.addAttribute("productVO", productVO);
    model.addAttribute("urlList", urlList);
    return "views/product-details";
  }

}
