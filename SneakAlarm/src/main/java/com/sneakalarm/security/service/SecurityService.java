package com.sneakalarm.security.service;

import java.util.List;
import com.sneakalarm.security.dto.AuthRequestVO;

public interface SecurityService {
  public List<AuthRequestVO> getAuthRequestList();
}
