package com.sneakalarm.product.controller;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;

@Controller
public class ProductController {

  @Autowired
  ProductService productServiceImpl;

  @GetMapping("/")
  public String getProductCardList(Model model) {
    ArrayList<ProductCardVO> list =
        (ArrayList<ProductCardVO>) productServiceImpl.getProductCardList();
    model.addAttribute("list", list);
    return "views/home";
  }

  @GetMapping("/product-modify")
  public String modifyProduct(@Param("id") String id, Model model) {
    ArrayList<ProductVO> list = (ArrayList<ProductVO>) productServiceImpl.getProductList(id);
    ProductVO productVO = list.get(0);
    model.addAttribute("productVO", productVO);
    return "views/product-modify";
  }

  @PostMapping("/product-modify")
  @ResponseBody
  public boolean modifyProduct(ProductInsertVO productInsertVO) {
    productServiceImpl.updateProduct(productInsertVO);
    return true;
  }

  @GetMapping("/product-insert")
  public String insertProductForm() {
    return "views/product-insert";
  }

  @PostMapping("/product-insert")
  @ResponseBody
  public boolean insertProduct(ProductInsertVO productInsertVO) throws Exception {
    productServiceImpl.insertProduct(productInsertVO);
    return true;
  }

  @GetMapping("/product-delete")
  @ResponseBody
  public boolean productDelete(String id) {
    return productServiceImpl.deleteProduct(id);
  }

  @GetMapping("/product-detail")
  public String getProductDetails(@Param("id") String id, Model model) {
    ArrayList<ProductVO> productVOList =
        (ArrayList<ProductVO>) productServiceImpl.getProductList(id);
    if (productVOList.size() == 0) {
      return "/";
    }
    ProductVO productVO = productVOList.get(0);

    String[] urlArray = productVO.getImgSrc_detail().split(",");
    model.addAttribute("productVO", productVO);
    model.addAttribute("urlList_detail", urlArray);
    return "views/product-detail";
  }

}
