package com.sneakalarm.security.config;

import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.sneakalarm.security.dto.AuthRequestVO;
import com.sneakalarm.security.service.SecurityService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private DataSource dataSource;
  @Autowired
  private SecurityService securityService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    setAntMatchers(http, "ROLE_");
    http.headers().disable();

    http.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
        .defaultSuccessUrl("/home").and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .addLogoutHandler(new TaskImplementingLogoutHandler()).permitAll()
        .logoutSuccessUrl("/home");
  }

  protected void setAntMatchers(HttpSecurity http, String rolePrefix) throws Exception {
    ArrayList<AuthRequestVO> authRequestList =
        (ArrayList<AuthRequestVO>) securityService.getAuthRequestList();

    for (AuthRequestVO authRequest : authRequestList) {
      String[] roles = authRequest.getHas_authority().split(",");
      String url = authRequest.getUrl();

      for (int i = 0; i < roles.length; i++) {
        roles[i] = rolePrefix + roles[i].toUpperCase();
      }
      if (url.charAt(0) != '/') {
        url = "/" + url;
      }
      http.authorizeRequests().antMatchers(url).hasAnyAuthority(roles);
    }
    http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource).rolePrefix("ROLE_")
        .usersByUsernameQuery(
            "select id, replace(password, '$2y', '$2a'), true from users where id = ?")
        .authoritiesByUsernameQuery("select id, role from users where id = ?");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
