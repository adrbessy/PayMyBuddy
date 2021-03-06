package com.PayMyBuddy.service;

import com.PayMyBuddy.model.BankAccount;
import java.util.List;

public interface BankAccountService {

  /**
   * Check if the given iban exists for the given email.
   * 
   * @param emailAddressReceiver The given email
   * @param idBankAccount        The bank account id
   * @return true if the association of both exist, otherwise returns false
   */
  boolean bankAccountExist(String emailAddressReceiver, Long idBankAccount);

  /**
   * Get all bank accounts
   * 
   * @return all bank accounts
   */
  List<BankAccount> getBankAccounts();

  /**
   * Save a bank account
   * 
   * @param bankAccount A bank account to save
   * @return the saved bankAccount
   */
  BankAccount saveBankAccount(BankAccount bankAccount);

  /**
   * Get the bank accounts of one user
   * 
   * @param emailAddress An email address
   * @return the list of bank accounts
   */
  List<BankAccount> getMyBankAccounts(String emailAddress);

  /**
   * Delete a bank account of one user
   * 
   * @param emailAddress An email address
   * @param iban         An iban
   * @return the deleted bank account
   */
  BankAccount deleteMyBankAccount(String emailAddress, String iban);

  /**
   * Check if the bank account exists.
   * 
   * @param emailAddress The given email
   * @param iban         the given iban
   * @return true if the association of both exist, otherwise returns false
   */
  boolean bankAccountEmailAddressIbanExist(String emailAddress, String iban);

}
