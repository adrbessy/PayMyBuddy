package com.PayMyBuddy.service;

import com.PayMyBuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

}
