package com.sneakalarm.security.dto;

import lombok.Data;

@Data
public class AuthRequestVO {
  private int id;
  private String url;
  private String has_authority;
  private String insertdate;

  public AuthRequestVO() {}
}
