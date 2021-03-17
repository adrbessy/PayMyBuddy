package com.PayMyBuddy.service;

import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.repository.BankAccountRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

  private static final Logger logger = LogManager.getLogger(BankAccountServiceImpl.class);

  @Autowired
  private BankAccountRepository bankAccountRepository;

  /**
   * Check if the given iban exists for the given email.
   * 
   * @param emailAddressReceiver The given email
   * @param emailAddress_user2   The given email
   * @return true if the association of both exist, otherwise returns false
   */
  @Override
  public boolean bankAccountExist(String emailAddressReceiver, Long idBankAccount) {
    logger.debug("in the method bankAccountExist in the class BankAccountServiceImpl");
    boolean bankAccountExist = false;
    try {
      bankAccountExist = bankAccountRepository.existsByEmailAddressAndId(
          emailAddressReceiver,
          idBankAccount);
    } catch (Exception exception) {
      logger.error("Error in the method bankAccountExist :" + exception.getMessage());
    }
    return bankAccountExist;
  }

  /**
   * Get all bank accounts
   * 
   * @return all bank accounts
   */
  @Override
  public List<BankAccount> getBankAccounts() {
    logger.debug("in the method getBankAccounts in the class BankAccountServiceImpl");
    List<BankAccount> bankAccountList = new ArrayList<>();
    try {
      bankAccountList = (List<BankAccount>) bankAccountRepository.findAll();
    } catch (Exception exception) {
      logger.error("Error in the method getBankAccounts :" + exception.getMessage());
    }
    return bankAccountList;
  }

  /**
   * Save a bank account
   * 
   * @param bankAccount A bank account to save
   * @return the saved bankAccount
   */
  @Override
  public BankAccount saveBankAccount(BankAccount bankAccount) {
    logger.debug("in the method saveBankAccount in the class BankAccountServiceImpl");
    BankAccount savedBankAccount = null;
    try {
      savedBankAccount = bankAccountRepository.save(bankAccount);
    } catch (Exception exception) {
      logger.error("Error when we try to save a bank account :" + exception.getMessage());
    }
    return savedBankAccount;
  }

  /**
   * Get the bank accounts of one user
   * 
   * @param emailAddress An email address
   * @return the list of bank accounts
   */
  @Override
  public List<BankAccount> getMyBankAccounts(String emailAddress) {
    logger.debug("in the method getMyBankAccounts in the class BankAccountServiceImpl");
    List<BankAccount> bankAccountList = null;
    try {
      bankAccountList = bankAccountRepository.findByEmailAddress(emailAddress);
    } catch (Exception exception) {
      logger.error("Error in th method getMyBankAccounts :" + exception.getMessage());
    }
    return bankAccountList;
  }

  /**
   * Delete a bank account of one user
   * 
   * @param emailAddress An email address
   * @param iban         An iban
   * @return the deleted bank account
   */
  @Override
  @Transactional
  public BankAccount deleteMyBankAccount(String emailAddress, String iban) {
    logger.debug("in the method deleteMyBankAccount in the class BankAccountServiceImpl");
    BankAccount bankAccount = null;
    try {
      bankAccount = bankAccountRepository.findByEmailAddressAndIban(emailAddress, iban);
      bankAccountRepository.deleteByEmailAddressAndIban(emailAddress, iban);
    } catch (Exception exception) {
      logger.error("Error in the method deleteMyBankAccount :" + exception.getMessage());
    }
    return bankAccount;
  }

  @Override
  public boolean bankAccountEmailAddressIbanExist(String emailAddress, String iban) {
    logger.debug("in the method bankAccountEmailAddressIbanExist in the class BankAccountServiceImpl");
    boolean bankAccountExist = false;
    try {
      bankAccountExist = bankAccountRepository.existsByEmailAddressAndIban(
          emailAddress,
          iban);
    } catch (Exception exception) {
      logger.error("Error in the method bankAccountEmailAddressIbanExist :" + exception.getMessage());
    }
    return bankAccountExist;
  }

}
