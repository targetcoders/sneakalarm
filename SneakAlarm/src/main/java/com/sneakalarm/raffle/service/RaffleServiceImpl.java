package com.sneakalarm.raffle.service;

import com.sneakalarm.product.ProductConst;
import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.raffle.RaffleConst;
import com.sneakalarm.raffle.dao.RaffleCardMapper;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.ActiveRafflesVO;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleInsertVO;
import com.sneakalarm.raffle.dto.RaffleListByDeliveryTypeVO;
import com.sneakalarm.raffle.dto.RaffleListByStatusVO;
import com.sneakalarm.raffle.dto.RaffleSearchCondition;
import com.sneakalarm.raffle.dto.RaffleUpdateStatusVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.DateTranslator;
import com.sneakalarm.util.StringUtil;
import com.sneakalarm.util.dto.BucketVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RaffleServiceImpl implements RaffleService {

  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;
  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Value("${cloud.aws.region.static}")
  private String region;
  @Value("${s3.folder-name.raffle}")
  private String folderName;

  @Autowired
  private StringUtil stringUtil;

  @Autowired
  ProductMapper productMapper;
  @Autowired
  RaffleMapper raffleMapper;

  @Autowired
  RaffleCardMapper raffleCardMapper;

  @Override
  public void raffleInsert(RaffleInsertVO raffleInsertVO) {
    RaffleVO raffleVO = new RaffleVO(raffleInsertVO);
    String fileName = raffleInsertVO.getFile().getOriginalFilename();
    String productId = raffleInsertVO.getProductId();
    BucketVO bucketVO = new BucketVO(region, bucket, folderName);
    String imgSrc = stringUtil.getDrawImgURL(bucketVO, productId, fileName);
    raffleVO.setImgSrc(imgSrc);
    raffleMapper.raffleInsert(raffleVO);
  }

  @Override
  public ArrayList<RaffleCardVO> getRaffleCardList(String productId) {
    return raffleCardMapper.getRaffleCardList(productId);
  }

  @Override
  public ArrayList<RaffleVO> getRaffleListAll() {
    return raffleMapper.getRaffleListAll();
  }

  public ArrayList<RaffleVO> getRaffleList(String raffleId) throws Exception {
    ArrayList<RaffleVO> result = raffleMapper.getRaffle(raffleId);
    for(RaffleVO raffleVO : result){
      raffleVO.setEndWeek(new DateTranslator(new SimpleDateFormat("yyyy-MM-dd").parse(raffleVO.getEndDate())).krWeek());
      raffleVO.setStartWeek(new DateTranslator(new SimpleDateFormat("yyyy-MM-dd").parse(raffleVO.getStartDate())).krWeek());
    }
    return result;
  }

  @Override
  public List<RaffleVO> getCheckedRaffleList(String[] splitMyRaffles) {
    return raffleMapper.getCheckedRaffleList(splitMyRaffles);
  }

  @Override
  public void updateRaffle(RaffleInsertVO raffleInsertVO) {
    ArrayList<RaffleVO> list = raffleMapper.getRaffle(raffleInsertVO.getId());
    RaffleVO raffleVO = list.get(0);

    raffleVO.setContent(raffleInsertVO.getContent());
    raffleVO.setCountry(raffleInsertVO.getCountry());
    raffleVO.setDelivery(raffleInsertVO.getDelivery());
    raffleVO.setEndDate(raffleInsertVO.getEndDate());
    raffleVO.setEndTime(raffleInsertVO.getEndTime());
    raffleVO.setPayType(raffleInsertVO.getPayType());
    raffleVO.setRaffleType(raffleInsertVO.getRaffleType());
    raffleVO.setReleasePrice(raffleInsertVO.getReleasePrice());
    raffleVO.setSpecialCase(raffleInsertVO.getSpecialCase());
    raffleVO.setStartDate(raffleInsertVO.getStartDate());
    raffleVO.setStartTime(raffleInsertVO.getStartTime());
    raffleVO.setStoreName(raffleInsertVO.getStoreName());
    raffleVO.setUrl(raffleInsertVO.getUrl());
    raffleVO.setModel_kr(raffleInsertVO.getModel_kr());
    String fileName = raffleInsertVO.getFile().getOriginalFilename();
    if (fileName != null && !fileName.equals("")) {
      BucketVO bucketVO = new BucketVO(region, bucket, folderName);
      String productId = raffleInsertVO.getProductId();
      raffleVO.setImgSrc(stringUtil.getDrawImgURL(bucketVO, productId, fileName));
    }
    raffleMapper.updateRaffle(raffleVO);
  }

  @Override
  public void deleteRaffle(String id) {
    raffleMapper.deleteRaffle(id);
  }


  @Override
  public String calcRaffleStatus(String startDate, String startTime, String endDate, String endTime)
      throws ParseException {
    String status;
    SimpleDateFormat sdf = new SimpleDateFormat(ProductConst.DATE_FORMAT.replaceAll("/", "-"));
    Date nowDateTime = new Date();
    Date startDateTime = sdf.parse(startDate + " " + startTime);
    Date endDateTime = sdf.parse(endDate + " " + endTime);
    
    int res = nowDateTime.compareTo(endDateTime);
    if (res > 0)
      status = ProductConst.STATUS_ENDED;
    else if (nowDateTime.compareTo(startDateTime) < 0) {
      status = ProductConst.STATUS_READY;
    } else {
      status = ProductConst.STATUS_GOING;
    }
    return status;
  }

  @Override
  public void updateDrawStatus(String id, String status) {
    raffleMapper.updateDrawStatus(new RaffleUpdateStatusVO(id,status));
  }

  @Override
  public String getDrawStatus(String id) {
    return raffleMapper.getDrawStatus(id);
  }

  @Override
  public ArrayList<RaffleVO> getRaffleListByStatus(RaffleListByStatusVO raffleListByStatusVO) {
    return raffleMapper.getRaffleListByStatus(raffleListByStatusVO);
  }

  @Override
  public ArrayList<RaffleVO> getRaffleListByDeliveryType(
      RaffleListByDeliveryTypeVO raffleListByDeliveryTypeVO) {
    return raffleMapper.getRaffleListByDeliveryType(raffleListByDeliveryTypeVO);
  }

  private String getEngDelivery(String delivery) {
    if("택배배송".equals(delivery)){
      return RaffleConst.DELIVERY_KOREA;
    } else if("방문수령".equals(delivery)){
      return RaffleConst.DELIVERY_KOREA;
    } else if("직배".equals(delivery)){
      return RaffleConst.DELIVERY_DIRECT;
    } else {
      return RaffleConst.DELIVERY_AGENT;
    }
  }

  @Override
  public List<RaffleVO> activeRaffles(ActiveRafflesVO activeRafflesVO) {
    return raffleMapper.activeRaffles(activeRafflesVO);
  }

  @Override
  public List<RaffleVO> duplicatedRaffles(RaffleSearchCondition raffleSearchCondition) {
    return raffleMapper.duplicatedRaffles(raffleSearchCondition);
  }
}
