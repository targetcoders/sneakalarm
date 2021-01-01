package com.sneakalarm.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sneakalarm.product.dao.ProductCardMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductVO;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductCardMapper productCardMapper;
  @Autowired
  private ProductMapper productMapper;

  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${s3.folder-name.product}")
  private String productFolderName;

  @Override
  public ArrayList<ProductCardVO> getProductCardList() {

    ArrayList<ProductCardVO> list =
        (ArrayList<ProductCardVO>) productCardMapper.getProductCardList();
    ArrayList<ProductCardVO> ret = new ArrayList<ProductCardVO>();

    for (int i = 0; i < list.size(); i++) {
      SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      try {
        ProductCardVO prod = list.get(i);
        Date nowDate = new Date();
        Date endDate = f.parse(prod.getEndDate());
        String timeLeft = getTimeLeft(endDate, nowDate);
        prod.setTimeLeft(timeLeft);
        String imgSrcOrigin = prod.getImgSrc_home();
        String[] imgSrcList = imgSrcOrigin.split(",");
        prod.setImgSrc_home(imgSrcList[0]);
        ret.add(prod);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return ret;
  }

  @Override
  public boolean insertProduct(ProductInsertVO productInsertVO) {
    ArrayList<ProductVO> prodList =
        (ArrayList<ProductVO>) productMapper.getProductList(productInsertVO.getCode());
    if (prodList.size() != 0) {
      return false;
    }

    ArrayList<MultipartFile> fileList_home =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_home();
    System.out.println("fileList_home: " + fileList_home + " size:" + fileList_home.size());
    ArrayList<MultipartFile> fileList_detail =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_detail();
    System.out.println("fileList_detail:" + fileList_detail + " size:" + fileList_detail.size());
    ArrayList<String> urlList_home = new ArrayList<String>();
    String code = productInsertVO.getCode();

    for (MultipartFile file : fileList_home) {
      String fileName = file.getOriginalFilename();
      System.out.println(fileName);
      if (fileName.substring(0, 2).contentEquals("C:")) {
        fileName = fileName.substring(12);
      }
      System.out.println(fileName);

      String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
          + productFolderName + code + "/" + fileName;
      urlList_home.add(url);
    }

    ArrayList<String> urlList_detail = new ArrayList<String>();
    for (MultipartFile file : fileList_detail) {
      String fileName = file.getOriginalFilename();
      System.out.println(fileName);
      if (fileName.substring(0, 2).contentEquals("C:")) {
        fileName = fileName.substring(12);
      }
      System.out.println("detail:" + fileName);

      String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
          + productFolderName + code + "/" + fileName;
      urlList_detail.add(url);
    }

    String now = getNowDate();
    String imgSrc_home = String.join(",", urlList_home);
    String imgSrc_detail = String.join(",", urlList_detail);

    ProductVO productVO = new ProductVO(productInsertVO);
    productVO.setImgSrc_home(imgSrc_home);
    productVO.setImgSrc_detail(imgSrc_detail);
    productVO.setFilterCode("000");
    productVO.setStartDate(now);
    productVO.setEndDate(now);
    productVO.setPopularity("0");
    productVO.setInsertDate(now);
    productVO.setFlag_show(true);
    productVO.setFlag_del(false);
    productMapper.insertProduct(productVO);
    return true;
  }

  public String getNowDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    return sdf.format(date);
  }

  public String getTimeLeft(Date endDate, Date nowDate) {
    long diff = endDate.getTime() - nowDate.getTime();
    long min = diff / 60000;
    long day = min / 1440;
    min %= 1440;
    long hour = min / 60;
    min %= 60;
    return day + "일 " + hour + "시간 " + min + "분";
  }

  public List<ProductVO> getProductList(String code) {
    return productMapper.getProductList(code);
  }

  @Override
  public boolean deleteProduct(String code) {
    productMapper.deleteProduct(code);
    return true;
  }

  @Override
  public void updateProduct(ProductInsertVO productInsertVO) {
    ArrayList<ProductVO> list =
        (ArrayList<ProductVO>) productMapper.getProductList(productInsertVO.getCode());
    ProductVO p = list.get(0);
    p.setName(productInsertVO.getName());
    p.setPrice(productInsertVO.getPrice());
    p.setBrand(productInsertVO.getBrand());
    p.setContent(productInsertVO.getContent());

    ArrayList<MultipartFile> fileList_home =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_home();
    ArrayList<MultipartFile> fileList_detail =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_detail();
    if (fileList_home != null && fileList_detail != null) {
      ArrayList<String> urlList_home = new ArrayList<String>();
      ArrayList<String> urlList_detail = new ArrayList<String>();
      String code = productInsertVO.getCode();

      for (MultipartFile file : fileList_home) {
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        if (fileName.substring(0, 2).contentEquals("C:")) {
          fileName = fileName.substring(12);
        }
        System.out.println(fileName);
        String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
            + productFolderName + code + "/" + fileName;
        urlList_home.add(url);
      }
      String imgSrc_home = String.join(",", urlList_home);
      p.setImgSrc_home(imgSrc_home);

      for (MultipartFile file : fileList_detail) {
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        if (fileName.substring(0, 2).contentEquals("C:")) {
          fileName = fileName.substring(12);
        }
        System.out.println(fileName);
        String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
            + productFolderName + code + "/" + fileName;
        urlList_detail.add(url);
      }
      String imgSrc_detail = String.join(",", urlList_detail);
      p.setImgSrc_detail(imgSrc_detail);
    }
    productMapper.updateProduct(p);
  }
}
