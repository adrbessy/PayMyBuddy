package com.PayMyBuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable();

    http.authorizeRequests()
        .antMatchers("/admin").hasRole("ADMIN")
        .antMatchers("/user").hasAnyRole("ADMIN", "USER")
        .antMatchers("/").permitAll()
        .and().formLogin().loginPage("/login");
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // super.configure(auth);

    auth.inMemoryAuthentication()
        .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("USER", "ADMIN").and()
        .withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN");

  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }



}