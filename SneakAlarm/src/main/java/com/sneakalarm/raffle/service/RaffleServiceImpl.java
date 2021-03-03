package com.sneakalarm.raffle.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sneakalarm.product.dao.ProductMapper;
import com.sneakalarm.product.dto.InsertDrawVO;
import com.sneakalarm.raffle.dao.RaffleMapper;
import com.sneakalarm.raffle.dto.RaffleCardVO;
import com.sneakalarm.raffle.dto.RaffleInsertVO;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.util.StringUtil;
import com.sneakalarm.util.dto.BucketVO;

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

  @Override
  public void raffleInsert(RaffleInsertVO raffleInsertVO) {
    RaffleVO raffleVO = new RaffleVO(raffleInsertVO);
    String fileName = raffleInsertVO.getFile().getOriginalFilename();
    Integer productId = raffleInsertVO.getProductId();
    BucketVO bucketVO = new BucketVO(region, bucket, folderName);
    String imgSrc = stringUtil.getDrawImgURL(bucketVO, productId, fileName);
    raffleVO.setImgSrc(imgSrc);
    raffleMapper.raffleInsert(raffleVO);
    InsertDrawVO insertDrawVO = new InsertDrawVO(raffleInsertVO.getRaffleType(), productId);
    productMapper.updateDrawNum(insertDrawVO);
  }


  @Override
  public ArrayList<RaffleCardVO> getRaffleCardList(String productId) {
    ArrayList<RaffleVO> raffleList = raffleMapper.getRaffleList(productId);
    ArrayList<RaffleCardVO> ret = new ArrayList<RaffleCardVO>();
    for (RaffleVO raffleVO : raffleList) {
      RaffleCardVO rafflaCardVO = new RaffleCardVO(raffleVO);
      ret.add(rafflaCardVO);
    }

    return ret;
  }

  public ArrayList<RaffleVO> getRaffleList(Integer raffleId) {
    ArrayList<RaffleVO> raffleList = raffleMapper.getRaffle(raffleId);
    return raffleList;
  }

  @Override
  public void updateRaffle(RaffleInsertVO raffleInsertVO) {
    ArrayList<RaffleVO> list = (ArrayList<RaffleVO>) raffleMapper.getRaffle(raffleInsertVO.getId());
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
    String fileName = raffleInsertVO.getFile().getOriginalFilename();
    if (!fileName.equals("")) {
      BucketVO bucketVO = new BucketVO(region, bucket, folderName);
      Integer productId = raffleInsertVO.getProductId();
      raffleVO.setImgSrc(stringUtil.getDrawImgURL(bucketVO, productId, fileName));
    }
    raffleMapper.updateRaffle(raffleVO);
  }

  @Override
  public void deleteRaffle(Integer id) {
    raffleMapper.deleteRaffle(id);
  }

}
