package com.sneakalarm.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sneakalarm.security.dto.AuthRequestVO;

@Mapper
public interface AuthRequestMapper {
  public List<AuthRequestVO> getAuthRequestList();
}
