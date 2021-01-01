package com.sneakalarm.product.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class ProductInsertVO {
  private String name;
  private String code;
  private String price;
  private String brand;
  private String content;
  private List<MultipartFile> fileList_home;
  private List<MultipartFile> fileList_detail;

  public ProductInsertVO() {}
}
