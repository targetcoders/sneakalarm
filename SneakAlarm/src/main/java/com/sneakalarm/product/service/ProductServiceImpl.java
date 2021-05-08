package com.sneakalarm.product.service;

import com.sneakalarm.product.dto.UpdateDeliveryTypesVO;
import com.sneakalarm.product.dto.UpdateDrawCountriesVO;
import com.sneakalarm.product.vo.ProductStatus;
import com.sneakalarm.today.dto.TodayProductResponseVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sneakalarm.product.ProductConst;
import com.sneakalarm.product.dao.ProductCardMapper;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.ProductUpdateDrawNumVO;
import com.sneakalarm.product.dto.ProductCardVO;
import com.sneakalarm.product.dto.ProductInsertVO;
import com.sneakalarm.product.dto.ProductUpdateEndDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStartDateTimeVO;
import com.sneakalarm.product.dto.ProductUpdateStatusVO;
import com.sneakalarm.product.dto.ProductVO;
import com.sneakalarm.raffle.service.RaffleService;
import com.sneakalarm.util.DateUtil;
import com.sneakalarm.util.StringUtil;
import com.sneakalarm.util.dto.BucketVO;

import ch.qos.logback.classic.Logger;
import groovy.util.logging.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  private ProductCardMapper productCardMapper;
  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private RaffleService reffleService;
  @Autowired
  private DateUtil dateUtil;

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

  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @Override
  public ArrayList<ProductCardVO> getProductCardList() {
    logger.info("Enter >> getProductCardList");
    logger.debug("Enter >> getProductCardList");
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

  public List<ProductCardVO> getProductCardListByKeyword(String keyword) {
    ArrayList<ProductCardVO> cardList = productCardMapper.getProductCardListByKeyword(keyword);

    ArrayList<ProductCardVO> settedCardList = null;
    try {
      settedCardList = setProductCardStatus(cardList);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return settedCardList;
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
    productVO.setStatus(new ProductStatus("해외/국내", "선착/응모", 0, 0));
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
    ProductVO productVO = list.get(0);

    productVO.setModel_kr(productInsertVO.getModel_kr());
    productVO.setModel_en(productInsertVO.getModel_en());
    productVO.setRetailPrice(productInsertVO.getRetailPrice());
    productVO.setBrand(productInsertVO.getBrand());
    productVO.setContent(productInsertVO.getContent());
    productVO.setReleaseStartDate(productInsertVO.getReleaseStartDate());
    productVO.setReleaseEndDate(productInsertVO.getReleaseEndDate());
    productVO.setPremiumPrice(productInsertVO.getPremiumPrice());
    productVO.setAverageSalePrice(productInsertVO.getAverageSalePrice());
    productVO.setLowestSoldPrice(productInsertVO.getLowestSoldPrice());
    productVO.setHighestSoldPrice(productInsertVO.getHighestSoldPrice());
    productVO.setSize(productInsertVO.getSize());
    productVO.setCode(productInsertVO.getCode());
    productVO.setReleaseDate(productInsertVO.getReleaseDate());
    if (productInsertVO.getIsChanged().equals("true")) {
      productVO.setLastUpdateDate(getNowDate());
    }

    ArrayList<MultipartFile> fileList_home =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_home();
    ArrayList<MultipartFile> fileList_detail =
        (ArrayList<MultipartFile>) productInsertVO.getFileList_detail();

    if (fileList_home != null && fileList_home.get(0).getOriginalFilename().equals("")) {
      fileList_home = null;
    }
    if (fileList_detail != null && fileList_detail.get(0).getOriginalFilename().equals("")) {
      fileList_detail = null;
    }

    String code = productInsertVO.getCode();
    BucketVO bucketVO = new BucketVO(region, bucket, productFolderName);
    ArrayList<String> urlList_home;
    ArrayList<String> urlList_detail;

    if (fileList_home != null && fileList_detail != null) {
      urlList_home = stringUtil.getInputFileNameList(bucketVO, code, fileList_home);
      urlList_detail = stringUtil.getInputFileNameList(bucketVO, code, fileList_detail);

      productVO.setImgSrc_home(String.join(",", urlList_home));
      productVO.setImgSrc_detail(String.join(",", urlList_detail));
    } else if (fileList_home != null && fileList_detail == null) {
      urlList_home = stringUtil.getInputFileNameList(bucketVO, code, fileList_home);
      productVO.setImgSrc_home(String.join(",", urlList_home));
    } else if (fileList_home == null && fileList_detail != null) {
      urlList_detail = stringUtil.getInputFileNameList(bucketVO, code, fileList_detail);
      productVO.setImgSrc_detail(String.join(",", urlList_detail));
    }

    productMapper.updateProduct(productVO);
  }
  
  public void updateDrawNum(ProductUpdateDrawNumVO productUpdateDrawNumVO) {
    productMapper.updateDrawNum(productUpdateDrawNumVO);
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

  @Override
  public List<Integer> getProductIdListAll() throws Exception {
    List<Integer> IdListAll = productMapper.getProductIdListAll();
    return IdListAll;
  }

  @Override
  public void updateProductStatus(ProductUpdateStatusVO productUpdateStatusVO) {
    productMapper.updateProductStatus(productUpdateStatusVO);
  }

  @Override
  public void updateDrawCountries(UpdateDrawCountriesVO updateDrawCountriesVO) {
    productMapper.updateDrawCountries(updateDrawCountriesVO);
  }

  @Override
  public void updateDeliveryTypes(UpdateDeliveryTypesVO updateDeliveryTypesVO) {
    productMapper.updateDeliveryTypes(updateDeliveryTypesVO);
  }

  @Override
  public ArrayList<ProductVO> getProductByDeliveryType(String delivery) {
    return productMapper.getProductByDeliveryType(delivery);
  }

  @Override
  public ArrayList<TodayProductResponseVO> getTodayProductResponseVO() {
    return productMapper.getTodayProductResponseVO();
  }

  @Override
  public List<String> getProductIdListByStatus(String status) {
    return productMapper.getProductIdListByStatus(status);
  }
}
