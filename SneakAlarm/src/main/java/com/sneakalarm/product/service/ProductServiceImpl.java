package com.sneakalarm.product.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.util.StringUtils;
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

    for (ProductCardVO productCard : list) {
      SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      try {
        Date nowDate = new Date();
        Date endDate = f.parse(productCard.getEndDate());
        String timeLeft = getTimeLeft(endDate, nowDate);
        productCard.setTimeLeft(timeLeft);
        String imgSrcOrigin = productCard.getImgSrc();
        String[] imgSrcList = imgSrcOrigin.split(",");
        productCard.setImgSrc(imgSrcList[0]);

      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  @Override
  public void insertProduct(ProductInsertVO productInsertVO) throws Exception {
    ArrayList<ProductVO> prodList =
        (ArrayList<ProductVO>) productMapper.getProductList(productInsertVO.getCode());
    if (prodList.size() != 0) {
      throw new Exception();
    }

    ArrayList<File> fileList = (ArrayList<File>) productInsertVO.getFileList();
    String[] urlList = new String[fileList.size()];
    String code = productInsertVO.getCode();

    int idx = 0;
    for (File file : fileList) {
      String url = "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/"
          + productFolderName + code + "/" + file.getName();
      urlList[idx++] = url;
    }

    String now = getNowDate();
    String imgSrc = StringUtils.join(",", urlList);

    ProductVO productVO = new ProductVO(productInsertVO);
    productVO.setImgSrc(imgSrc);
    productVO.setFilterCode("000");
    productVO.setStartDate(now);
    productVO.setEndDate(now);
    productVO.setPopularity("0");
    productVO.setInsertDate(now);
    productVO.setFlag_show(true);
    productVO.setFlag_del(false);
    productMapper.insertProduct(productVO);
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
}
