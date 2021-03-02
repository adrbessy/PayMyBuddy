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

  /**
   * Get all user accounts
   * 
   * @return all user accounts
   */
  public Iterable<UserAccount> getUserAccounts();

  /**
   * Check if an user has enough money on his account when he makes a transaction.
   * 
   * @param emailAddress_emitter The User that makes the transaction
   * @param amount               The amount that he transfers
   * @return true if he has enough money
   */
  public boolean checkEnoughMoney(String emailAddress_emitter, double amount);

}
