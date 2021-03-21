package com.sneakalarm.util;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.sneakalarm.util.dto.BucketVO;

import ch.qos.logback.classic.Logger;
import groovy.util.logging.Slf4j;

/*
 * String 반환과 관련된 클래스
 */
@Component
@Slf4j
public class StringUtil {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
  public String getProductImgURL(BucketVO bucketVO, String code, String fileName) {
    String region = bucketVO.getRegion();
    String bucket = bucketVO.getBucket();
    String folderName = bucketVO.getFolderName();
    return "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/" + folderName + "/" + code
        + "/" + fileName;
  }

  public String getDrawImgURL(BucketVO bucketVO, Integer productId, String fileName) {
    String region = bucketVO.getRegion();
    String bucket = bucketVO.getBucket();
    String folderName = bucketVO.getFolderName();
    return "https://s3." + region + ".amazonaws.com/" + bucket + "/" + folderName + "/logo/"
        + fileName;
  }

  /**
   * 파일 이름에 'C:/fakepath/' 가 앞에 붙는 경우 해당 부분을 건너 뜀
   * 
   * @param file
   * @return
   */
  public String getInputFileName(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if(fileName.length() <= 2) return "";
    
    if (fileName.substring(0, 2).contentEquals("C:")) {
      fileName = fileName.substring(12);
    }
    return fileName;
  }

  /**
   * 입력된 파일들의 이름을 List로 반환
   * 
   * @param bucketVO: AWS S3 버킷에서 파일 url 가져올 때 필요한 정보를 가짐
   * @param code: 폴더 명으로 사용됨
   * @param fileList: 입력으로 들어온 파일 리스트
   * @return inputFileNameList:
   */
  public ArrayList<String> getInputFileNameList(BucketVO bucketVO, String code, ArrayList<MultipartFile> fileList) {
    ArrayList<String> inputFileNameList = new ArrayList<String>();
    for (MultipartFile file : fileList) {
      logger.debug("getInputFileNameList - fileName: " + file.getOriginalFilename());
      String fileName = getInputFileName(file);
      inputFileNameList.add(getProductImgURL(bucketVO, code, fileName));
    }
    return inputFileNameList;
  }

}
