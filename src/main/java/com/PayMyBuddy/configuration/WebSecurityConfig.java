package com.PayMyBuddy.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable();

    http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll();
  }

  /*
   * @Override protected void configure(AuthenticationManagerBuilder auth) throws
   * Exception { auth.inMemoryAuthentication()
   * .withUser("springuser").password(passwordEncoder().encode("spring123")).roles
   * ("USER") .and()
   * .withUser("springadmin").password(passwordEncoder().encode("admin123")).roles
   * ("ADMIN", "USER"); }
   * 
   * @Bean public PasswordEncoder passwordEncoder() { return new
   * BCryptPasswordEncoder(); }
   */

}