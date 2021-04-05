package com.PayMyBuddy.service;

import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  public List<UserAccount> getUserAccounts() {
    logger.debug("in the method getUserAccounts in the class UserAccountServiceImpl");
    List<UserAccount> userAccountList = new ArrayList<>();
    try {
      userAccountList = (List<UserAccount>) userAccountRepository.findAll();
    } catch (Exception exception) {
      logger.error("Error in the method getUserAccounts :" + exception.getMessage());
    }
    return userAccountList;
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
      double tax_amount = amount * Tax.TAX100 / 100;
      final_emitter_amount = emitter_amount - (amount + tax_amount);
    } catch (Exception exception) {
      logger.error("Error when we try to check if the user has enough money :" + exception.getMessage());
    }
    if (final_emitter_amount >= 0.0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if an email address exist in the userAccount DB.
   * 
   * @param emailAddress The email address to look for
   * @return true if it exists
   */
  @Override
  public boolean userAccountEmailExist(String emailAddress) {
    logger.debug("in the method userAccountEmailExist in the class UserAccountServiceImpl");
    boolean userAccountEmailExist = false;
    try {
      userAccountEmailExist = userAccountRepository.existsByEmailAddress(emailAddress);
    } catch (Exception exception) {
      logger.error("Error in the method userAccountEmailExist :" + exception.getMessage());
    }
    return userAccountEmailExist;
  }

  /**
   * Get a user account from an email address
   * 
   * @param emailAddress The emailAddress of the user account in the UserAccounts
   *                     table
   * @return The user account
   */
  @Override
  public UserAccount getUserAccount(final String emailAddress) {
    logger.debug("in the method getUserAccount in the class UserAccountServiceImpl");
    UserAccount userAccount = null;
    try {
      userAccount = userAccountRepository.findByEmailAddress(emailAddress);
    } catch (Exception exception) {
      logger.error("Error in the method getUserAccount :" + exception.getMessage());
    }
    return userAccount;
  }

  /**
   * Delete a user account
   * 
   * @param emailAddress The email address of the user account
   * 
   */
  @Override
  public void deleteUserAccount(String emailAddress) {
    logger.debug("in the method deleteUserAccount in the class UserAccountServiceImpl");
    try {
      userAccountRepository.deleteUserAccountByEmailAddress(emailAddress);
    } catch (Exception exception) {
      logger.error("Error in the method deleteUserAccount :" + exception.getMessage());
    }
  }

  /**
   * Encrypt the password of the user before storing it in the database
   * 
   * @param userAccount The userAccount of the user
   * @return The userAccount
   */
  @Override
  public UserAccount encryptPassword(UserAccount userAccount) {
    logger.debug("in the method encryptPassword in the class UserAccountServiceImpl");
    try {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      String encodedPassword = encoder.encode(userAccount.getPassword());
      userAccount.setPassword(encodedPassword);
    } catch (Exception exception) {
      logger.error("Error in the method encryptPassword :" + exception.getMessage());
    }
    return userAccount;
  }

  /**
   * Check the validity of the password
   * 
   * @param inputPassword     The password entered by the user
   * @param encryptedPassword The password encrypted
   * @return true if there is a correspondance between both
   */
  /*
   * @Override public boolean checkPassword(String inputPassword, String
   * encryptedPassword) { boolean checkPassword = false; logger.
   * debug("in the method checkPassword in the class UserAccountServiceImpl");
   * BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); try
   * { checkPassword = passwordEncryptor.checkPassword(inputPassword,
   * encryptedPassword); } catch (Exception exception) {
   * logger.error("Error in the method checkPassword :" + exception.getMessage());
   * } return checkPassword; }
   */

  /**
   * Check if the user accounts exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if they exist, otherwise returns false
   */
  @Override
  public String usersExist(String emailAddress_user1, String emailAddress_user2) {
    logger.debug("in the method usersExist in the class UserAccountServiceImpl");
    boolean existingEmail1 = false;
    boolean existingEmail2 = false;
    try {
      existingEmail1 = userAccountRepository.existsByEmailAddress(emailAddress_user1);
      existingEmail2 = userAccountRepository.existsByEmailAddress(emailAddress_user2);
    } catch (Exception exception) {
      logger.error("Error in the method usersExist :" + exception.getMessage());
    }
    if (existingEmail1 && existingEmail2) {
      return "yes";
    }
    if (existingEmail1 == false && existingEmail2 == false) {
      return emailAddress_user1 + " and " + emailAddress_user2 + " don't exist";
    }
    if (existingEmail1 == false && existingEmail2 == true) {
      return emailAddress_user1 + " doesn't exist";
    } else {
      return emailAddress_user2 + " doesn't exist";
    }
  }

}
