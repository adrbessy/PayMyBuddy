package com.PayMyBuddy.service;

import com.PayMyBuddy.model.UserAccount;

public interface UserAccountService {

  /**
   * Save a UserAccount
   * 
   * @param userAccount A UserAccount to save
   * @return the saved userAccount
   */
  public UserAccount saveUserAccount(UserAccount userAccount);

}
