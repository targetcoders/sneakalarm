package com.sneakalarm.product.dto;

import java.io.File;
import java.util.List;
import lombok.Data;

@Data
public class ProductInsertVO {
  private String name;
  private String code;
  private String price;
  private String brand;
  private String content;
  private List<File> fileList;

  public ProductInsertVO() {}
}
