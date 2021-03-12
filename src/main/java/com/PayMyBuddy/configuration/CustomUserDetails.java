package com.PayMyBuddy.configuration;

import com.PayMyBuddy.model.UserAccount;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private UserAccount userAccount;

  public CustomUserDetails(UserAccount userAccount) {
    this.userAccount = userAccount;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return userAccount.getPassword();
  }

  @Override
  public String getUsername() {
    return userAccount.getEmailAddress();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getFullName() {
    return userAccount.getFirstName() + " " + userAccount.getName();
  }

}
