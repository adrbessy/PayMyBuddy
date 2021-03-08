package com.PayMyBuddy.service;

import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.repository.BankAccountRepository;
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
  public boolean bankAccountExist(String emailAddressReceiver, String iban) {
    logger.debug("in the method bankAccountExist in the class BankAccountServiceImpl");
    return bankAccountRepository.existsByEmailAddressAndIban(
        emailAddressReceiver,
        iban);
  }

  /**
   * Get all bank accounts
   * 
   * @return all bank accounts
   */
  @Override
  public Iterable<BankAccount> getBankAccounts() {
    return bankAccountRepository.findAll();
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

}
