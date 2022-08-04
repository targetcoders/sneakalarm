package com.sneakalarm.product.controller;

import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

  @Autowired
  ProductService productService;

  @GetMapping("/products/{id}")
  public ProductVO getProductById(@PathVariable("id") String id){
    return productService.getProductList(id).get(0);
  }

}
