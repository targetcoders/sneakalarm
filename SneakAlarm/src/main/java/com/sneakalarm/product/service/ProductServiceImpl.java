package com.sneakalarm.product.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
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

    ArrayList<ProductCardVO> cardList =
        (ArrayList<ProductCardVO>) productCardMapper.getProductCardList();
    ArrayList<ProductCardVO> ret = new ArrayList<ProductCardVO>();

    ArrayList<ProductCardVO> settedCardList = setProductCardStatus(cardList);

    for (int i = 0; i < settedCardList.size(); i++) {
      ProductCardVO productCard = settedCardList.get(i);
      String imgSrcOrigin = productCard.getImgSrc_home();
      String[] imgSrcList = imgSrcOrigin.split(",");
      productCard.setImgSrc_home(imgSrcList[0]);
      ret.add(productCard);
    }

    return ret;
  }

  public ArrayList<ProductCardVO> setProductCardStatus(ArrayList<ProductCardVO> productCardList) {
    ArrayList<ProductCardVO> ret = new ArrayList<ProductCardVO>();
    for (ProductCardVO p : productCardList) {
      String endDate = p.getReleaseEndDate();
      if (endDate.equals("RELEASING SOON")) {
        p.setStatus("ended");
        ret.add(p);
        continue;
      }
      endDate = endDate.substring(0, 11) + endDate.substring(13, 18);
      String startDate = p.getReleaseStartDate();
      startDate = startDate.substring(0, 11) + startDate.substring(13, 18);
      Date now = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      String nowDate = sdf.format(now);
      System.out.println(p.getModel_kr());
      System.out.println("nowDate: " + nowDate);
      System.out.println("startDate: " + startDate);
      System.out.println("endDate: " + endDate);
      String status = "";


      int res = nowDate.compareTo(endDate);
      System.out.println("res: " + res);
      if (res >= 0) {
        status = "ended";
      } else {
        if (nowDate.compareTo(startDate) < 0) {
          status = "ready";
        } else {
          status = "going";
        }
      }
      p.setStatus(status);
      System.out.println("status: " + status);

      ret.add(p);
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
    ArrayList<MultipartFile> fileList_detail =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_detail();
    ArrayList<String> urlList_home = new ArrayList<String>();
    String code = productInsertVO.getCode();

    for (MultipartFile file : fileList_home) {
      String fileName = file.getOriginalFilename();
      System.out.println(fileName);
      if (fileName.substring(0, 2).contentEquals("C:")) {
        fileName = fileName.substring(12);
      }

      String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
          + productFolderName + "/" + code + "/" + fileName;
      urlList_home.add(url);
    }

    ArrayList<String> urlList_detail = new ArrayList<String>();
    for (MultipartFile file : fileList_detail) {
      String fileName = file.getOriginalFilename();
      if (fileName.substring(0, 2).contentEquals("C:")) {
        fileName = fileName.substring(12);
      }

      String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
          + productFolderName + "/" + code + "/" + fileName;
      urlList_detail.add(url);
    }

    String now = getNowDate();
    String imgSrc_home = String.join(",", urlList_home);
    String imgSrc_detail = String.join(",", urlList_detail);

    ProductVO productVO = new ProductVO(productInsertVO);
    productVO.setImgSrc_home(imgSrc_home);
    productVO.setImgSrc_detail(imgSrc_detail);
    // productVO.setReleaseStartDate(now);
    // productVO.setReleaseEndDate(now);
    productVO.setLastUpdateDate(now);
    productVO.setPopularity("0");
    productVO.setInsertDate(now);
    productVO.setReleaseDate(now);
    productVO.setCountry("모두/해외/국내");
    productVO.setDraw("선착/응모");
    productVO.setIsDeleted(0);
    productMapper.insertProduct(productVO);
    return true;
  }

  public String getNowDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
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
        (ArrayList<ProductVO>) productMapper.getProductList(productInsertVO.getId());
    ProductVO product = list.get(0);

    product.setModel_kr(productInsertVO.getModel_kr());
    product.setModel_en(productInsertVO.getModel_en());
    product.setRetailPrice(productInsertVO.getRetailPrice());
    product.setBrand(productInsertVO.getBrand());
    product.setContent(productInsertVO.getContent());
    product.setReleaseStartDate(productInsertVO.getReleaseStartDate());
    product.setReleaseEndDate(productInsertVO.getReleaseEndDate());
    product.setPremiumPrice(productInsertVO.getPremiumPrice());
    product.setAverageSalePrice(productInsertVO.getAverageSalePrice());
    product.setLowestSoldPrice(productInsertVO.getLowestSoldPrice());
    product.setHighestSoldPrice(productInsertVO.getHighestSoldPrice());
    product.setSize(productInsertVO.getSize());
    product.setCode(productInsertVO.getCode());
    if (productInsertVO.getIsChanged().equals("true")) {
      product.setLastUpdateDate(getNowDate());
    }

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
        String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
            + productFolderName + "/" + code + "/" + fileName;
        urlList_home.add(url);
      }
      String imgSrc_home = String.join(",", urlList_home);
      product.setImgSrc_home(imgSrc_home);

      for (MultipartFile file : fileList_detail) {
        String fileName = file.getOriginalFilename();
        if (fileName.substring(0, 2).contentEquals("C:")) {
          fileName = fileName.substring(12);
        }
        String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
            + productFolderName + "/" + code + "/" + fileName;
        urlList_detail.add(url);
      }
      String imgSrc_detail = String.join(",", urlList_detail);
      product.setImgSrc_detail(imgSrc_detail);
    }
    productMapper.updateProduct(product);
  }

  public class Assending implements Comparator<ProductCardVO> {

    @Override
    public int compare(ProductCardVO o1, ProductCardVO o2) {
      if (o1.getStatus().equals(o2.getStatus())) {
        return o1.getReleaseEndDate().compareTo(o2.getReleaseEndDate());
      } else if (o1.getStatus().compareTo(o2.getStatus()) == -1) {
        return 1;
      } else
        return -1;
    }
  }

  @Override
  public void updateStartDateTime(ProductUpdateStartDateTimeVO productUpdateStartDateTimeVO) {
    productMapper.updateStartDateTime(productUpdateStartDateTimeVO);
  }


  @Override
  public void updateEndDateTime(ProductUpdateEndDateTimeVO productUpdateEndDateTimeVO) {
    productMapper.updateEndDateTime(productUpdateEndDateTimeVO);
  }

}
