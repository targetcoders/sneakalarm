package com.sneakalarm.rafflesetting.service;

import com.sneakalarm.raffle.RaffleConst;
import com.sneakalarm.raffle.dto.RaffleVO;
import com.sneakalarm.rafflesetting.dao.RaffleSettingMapper;
import com.sneakalarm.rafflesetting.domain.RaffleSetting;
import com.sneakalarm.util.StringUtil;
import com.sneakalarm.util.dto.BucketVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RaffleSettingServiceImpl implements RaffleSettingService {

  @Autowired
  RaffleSettingMapper raffleSettingMapper;
  @Autowired
  StringUtil stringUtil;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Value("${cloud.aws.region.static}")
  private String region;
  @Value("${s3.folder-name.raffle-setting}")
  private String folderName;

  @Override
  public void createRaffleSetting(RaffleSetting raffleSetting) {
    String fileName = raffleSetting.getImgSrc();
    BucketVO bucketVO = new BucketVO(region, bucket, folderName);
    String imgSrc = stringUtil.getDrawImgURL(bucketVO, null, fileName);
    raffleSetting.setImgSrc(imgSrc);
    raffleSettingMapper.createRaffleSetting(raffleSetting);
  }

  @Override
  public RaffleSetting getRaffleSetting(Long id) {
    return raffleSettingMapper.getRaffleSetting(id);
  }

  @Override
  public List<RaffleSetting> getRaffleSettingAll() {
    return raffleSettingMapper.getRaffleSettingAll();
  }

  @Override
  public List<RaffleSetting> getRaffleSettingByKeyword(String keyword) {
    return raffleSettingMapper.getRaffleSettingByKeyword(keyword);
  }

  @Override
  public long insertRaffle(RaffleVO raffleVO) {
    if (raffleVO.getDelivery().equals(RaffleConst.DELIVERY_PACKAGE) || raffleVO
        .getDelivery().equals(RaffleConst.DELIVERY_VISIT)) {
      raffleVO.setDelivery(RaffleConst.DELIVERY_KOREA);
    }
    return raffleSettingMapper.insertRaffle(raffleVO);
  }

  @Override
  public void updateRaffleSetting(RaffleSetting raffleSetting) {
    String[] splitUrl = raffleSetting.getImgSrc().split("/");
    String fileName = splitUrl[splitUrl.length-1];
    BucketVO bucketVO = new BucketVO(region, bucket, folderName);
    String imgSrc = stringUtil.getDrawImgURL(bucketVO, null, fileName);
    raffleSetting.setImgSrc(imgSrc);
    raffleSettingMapper.updateRaffleSetting(raffleSetting);
  }

  @Override
  public void deleteRaffleSetting(Long id) {
    raffleSettingMapper.deleteRaffleSetting(id);
  }
}
