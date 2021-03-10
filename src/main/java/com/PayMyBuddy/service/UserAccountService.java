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

  /**
   * Check if an email address exist in the userAccount DB.
   * 
   * @param emailAddress The email address to look for
   * @return true if it exists
   */
  public boolean userAccountEmailExist(String emailAddress);

  /**
   * Get a UserAccount from an email address
   * 
   * @param emailAddress The email address of the user account in the UserAccount
   *                     table
   * @return The userAccount
   */
  UserAccount getUserAccount(String emailAddress);

  /**
   * Delete a user account
   * 
   * @param emailAddress The email address of the user account
   * 
   */
  public void deleteUserAccount(String emailAddress);

  /**
   * Encrypt the password of the user before storing it in the database
   * 
   * @param userAccount The userAccount of the user
   * @return The userAccount
   */
  public UserAccount encryptPassword(UserAccount userAccount);

  /**
   * Check the validity of the password
   * 
   * @param inputPassword     The password entered by the user
   * @param encryptedPassword The password encrypted
   * @return true if there is a correspondance between both
   */
  public boolean checkPassword(String inputPassword, String encryptedPassword);

}
