package com.sneakalarm.security.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sneakalarm.security.dao.AuthRequestMapper;
import com.sneakalarm.security.dto.AuthRequestVO;

@Service
public class SecurityServiceImpl implements SecurityService {
  @Autowired
  private AuthRequestMapper authRequestMapper;

  @Override
  public List<AuthRequestVO> getAuthRequestList() {
    return authRequestMapper.getAuthRequestList();
  }

}
