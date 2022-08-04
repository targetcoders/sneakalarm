package com.sneakalarm.s3.service;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class S3Service {
  private AmazonS3 s3Client;

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

  @PostConstruct
  public void setS3Client() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

    s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(this.region)
        .build();
  }

  public String upload(File file, String code) throws IOException {
    s3Client.putObject(bucket, productFolderName + code + "/" + file.getName(), file);

    return "https://" + "s3." + region + ".amazonaws.com/" + bucket + "/" + productFolderName + code
        + "/" + file.getName();
  }
}
