package com.PayMyBuddy.service;

import com.PayMyBuddy.model.BankAccount;

public interface BankAccountService {

  /**
   * Check if the given iban exists for the given email.
   * 
   * @param emailAddressReceiver The given email
   * @param emailAddress_user2   The given email
   * @return true if the association of both exist, otherwise returns false
   */
  boolean bankAccountExist(String emailAddressReceiver, String iban);

  /**
   * Get all bank accounts
   * 
   * @return all bank accounts
   */
  Iterable<BankAccount> getBankAccounts();

  /**
   * Save a bank account
   * 
   * @param bankAccount A bank account to save
   * @return the saved bankAccount
   */
  BankAccount saveBankAccount(BankAccount bankAccount);

}
