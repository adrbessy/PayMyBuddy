package com.PayMyBuddy.service;

import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

  private static final Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);

  @Autowired
  private UserAccountRepository userAccountRepository;

  /**
   * Save a UserAccount
   * 
   * @param userAccount A UserAccount to save
   * @return the saved userAccount
   */
  @Override
  public UserAccount saveUserAccount(UserAccount userAccount) {
    logger.debug("in the method saveUserAccount in the class UserAccountServiceImpl");
    UserAccount savedUserAccount = null;
    try {
      savedUserAccount = userAccountRepository.save(userAccount);
    } catch (Exception exception) {
      logger.error("Error when we try to save a user account :" + exception.getMessage());
    }
    return savedUserAccount;
  }

  /**
   * Get all user accounts
   * 
   * @return all user accounts
   */
  @Override
  public Iterable<UserAccount> getUserAccounts() {
    return userAccountRepository.findAll();
  }

  /**
   * Check if an user has enough money on his account when he makes a transaction.
   * 
   * @param emailAddress_emitter The User that makes the transaction
   * @param amount               The amount that he transfers
   * @return true if he has enough money
   */
  @Override
  public boolean checkEnoughMoney(String emailAddress_emitter, double amount) {
    logger.debug("in the method checkEnoughMoney in the class UserAccountServiceImpl");
    UserAccount emitter = null;
    double final_emitter_amount = 0;
    try {
      emitter = userAccountRepository.findByEmailAddress(emailAddress_emitter);
      double emitter_amount = emitter.getAmount();
      final_emitter_amount = emitter_amount - amount;
    } catch (Exception exception) {
      logger.error("Error when we try to check if the user has enough money :" + exception.getMessage());
    }
    if (final_emitter_amount >= 0.0) {
      return true;
    } else {
      return false;
    }
  }

}
