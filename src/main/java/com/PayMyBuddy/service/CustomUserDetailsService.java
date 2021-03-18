package com.PayMyBuddy.service;

import com.PayMyBuddy.model.CustomUserDetails;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
    UserAccount userAccount = userAccountRepository.findByEmailAddress(emailAddress);
    if (userAccount == null) {
      throw new UsernameNotFoundException("User account not found");
    }
    return new CustomUserDetails(userAccount);
  }

}
