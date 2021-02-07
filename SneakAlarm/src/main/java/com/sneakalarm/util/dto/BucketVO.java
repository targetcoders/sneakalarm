package com.sneakalarm.util.dto;

import lombok.Data;

@Data
public class BucketVO {
  private String region;
  private String bucket;
  private String folderName;

  public BucketVO() {

  }

  public BucketVO(String region, String bucket, String folderName) {
    this.region = region;
    this.bucket = bucket;
    this.folderName = folderName;
  }
}
