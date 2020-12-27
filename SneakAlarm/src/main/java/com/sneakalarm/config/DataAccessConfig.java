package com.sneakalarm.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import lombok.Data;

@Configuration
@MapperScan(basePackages = "com.sneakalarm.product, co m.sneakalarm.security")
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DataAccessConfig {
  private String driverClassName;
  private String url;
  private String username;
  private String password;

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username)
        .password(password).build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

    sessionFactory.setDataSource(dataSource);
    sessionFactory.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
    return sessionFactory.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
