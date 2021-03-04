package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

  private static final Logger logger = LogManager.getLogger(UserAccountController.class);

  @Autowired
  private UserAccountService userAccountService;

  /**
   * Read - Get all user accounts
   * 
   * @return - An Iterable object of userAccounts full filled
   */
  @GetMapping("/userAccounts")
  public Iterable<UserAccount> getUserAccounts() {
    return userAccountService.getUserAccounts();
  }

  /**
   * Add a new user account
   * 
   * @param userAccount An object userAccount
   * @return The userAccount object saved
   */
  @PostMapping("/userAccount")
  public UserAccount createUserAccount(@RequestBody UserAccount userAccount) {
    UserAccount newUserAccount = null;
    if (userAccount.getEmailAddress() == null || userAccount.getPassword() == null || userAccount.getFirstName() == null
        || userAccount.getName() == null) {
      logger.error("The new user account has to get an email address with a password, a name and a first name.");
      throw new IsForbiddenException(
          "The new user account has to get an email address with a password.");
    }
    try {
      newUserAccount = userAccountService.saveUserAccount(userAccount);
      logger.info(
          "response following the Post on the endpoint 'userAccount' with the given userAccount : {"
              + userAccount.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the UserAccountController in the method createUserAccount :"
          + exception.getMessage());
    }
    return newUserAccount;
  }

  /**
   * Update an existing user account from a given email address
   * 
   * @param emailAddress The given email address
   * @param userAccount  An userAccount object with modifications
   * @return The updated userAccount object
   */
  @PutMapping("/userAccount/{emailAddress}")
  public UserAccount updateUserAccount(@PathVariable("emailAddress") final String emailAddress,
      @RequestBody UserAccount userAccount) {
    UserAccount userAccountToUpdate = null;
    boolean existingUserAccountEmailAddress = false;
    try {
      logger.info(
          "Put request of the endpoint 'userAccount' with the emailAddress : {" + emailAddress + "}");
      existingUserAccountEmailAddress = userAccountService.userAccountEmailExist(emailAddress);
      if (existingUserAccountEmailAddress) {
        userAccountToUpdate = userAccountService.getUserAccount(emailAddress);
        logger.info(
            "response following the Put on the endpoint 'userAccount' with the given email address : {"
                + emailAddress + "}");
        if (userAccountToUpdate != null) {
          String firstName = userAccount.getFirstName();
          if (firstName != null) {
            userAccountToUpdate.setFirstName(firstName);
          }
          String name = userAccount.getName();
          if (name != null) {
            userAccountToUpdate.setName(name);
          }
          String password = userAccount.getPassword();
          if (password != null) {
            userAccountToUpdate.setPassword(password);
          }
          double amount = userAccount.getAmount();
          if (amount != 0) {
            userAccountToUpdate.setAmount(amount);
          }
          userAccountService.saveUserAccount(userAccountToUpdate);
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the UserAccountController in the method updateUserAccount :"
          + exception.getMessage());
    }
    if (!existingUserAccountEmailAddress) {
      logger.error("The user account with the email address " + emailAddress + " doesn't exist.");
      throw new NonexistentException("The user account with the email address " + emailAddress + " doesn't exist.");
    }
    return userAccountToUpdate;
  }

}
