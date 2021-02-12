package com.sneakalarm.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sneakalarm.product.ProductConst;
import com.sneakalarm.product.dao.ProductCardMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.util.StringUtil;
import com.sneakalarm.util.dto.BucketVO;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private StringUtil stringUtil;

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

    ArrayList<ProductCardVO> settedCardList = null;
    try {
      settedCardList = setProductCardStatus(cardList);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < settedCardList.size(); i++) {
      ProductCardVO productCard = settedCardList.get(i);
      String imgSrcOrigin = productCard.getImgSrc_home();
      String[] imgSrcList = imgSrcOrigin.split(",");
      productCard.setImgSrc_home(imgSrcList[0]);
      ret.add(productCard);
    }

    return ret;
  }

  public ArrayList<ProductCardVO> setProductCardStatus(ArrayList<ProductCardVO> productCardList)
      throws ParseException {
    ArrayList<ProductCardVO> ret = new ArrayList<ProductCardVO>();
    for (ProductCardVO p : productCardList) {
      if (p.getReleaseEndDate().equals("RELEASING SOON")) {
        p.setStatus(ProductConst.STATUS_ENDED);
        ret.add(p);
        continue;
      }
      // TODO: DB 테이블 변경-ReleaseStartDate, ReleaseStartTime 으로 나누기
      SimpleDateFormat sdf = new SimpleDateFormat(ProductConst.DATE_FORMAT);
      Date nowDate = new Date();
      Date startDate = sdf.parse(
          p.getReleaseStartDate().substring(0, 11) + p.getReleaseStartDate().substring(13, 18));
      Date endDate = sdf
          .parse(p.getReleaseEndDate().substring(0, 11) + p.getReleaseEndDate().substring(13, 18));

      String status = "";
      int res = nowDate.compareTo(endDate);
      if (res >= 0) {
        status = ProductConst.STATUS_ENDED;
      } else {
        if (nowDate.compareTo(startDate) < 0) {
          status = ProductConst.STATUS_READY;
        } else {
          status = ProductConst.STATUS_GOING;
        }
      }
      p.setStatus(status);

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

    String code = productInsertVO.getCode();
    BucketVO bucketVO = new BucketVO(region, bucket, productFolderName);

    ArrayList<String> urlList_home = stringUtil.getInputFileNameList(bucketVO, code, fileList_home);
    ArrayList<String> urlList_detail =
        stringUtil.getInputFileNameList(bucketVO, code, fileList_detail);

    String now = getNowDate();
    String imgSrc_home = String.join(",", urlList_home);
    String imgSrc_detail = String.join(",", urlList_detail);

    ProductVO productVO = new ProductVO(productInsertVO);
    productVO.setImgSrc_home(imgSrc_home);
    productVO.setImgSrc_detail(imgSrc_detail);
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
    SimpleDateFormat sdf = new SimpleDateFormat(ProductConst.DATE_FORMAT);
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
      String code = productInsertVO.getCode();
      BucketVO bucketVO = new BucketVO(region, bucket, productFolderName);
      ArrayList<String> urlList_home =
          stringUtil.getInputFileNameList(bucketVO, code, fileList_home);
      ArrayList<String> urlList_detail =
          stringUtil.getInputFileNameList(bucketVO, code, fileList_detail);

      product.setImgSrc_home(String.join(",", urlList_home));
      product.setImgSrc_detail(String.join(",", urlList_detail));
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
