package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.service.UserAccountService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
  public List<UserAccount> getUserAccounts() {
    List<UserAccount> userAccountList = new ArrayList<>();
    try {
      logger.info("Get request with the endpoint 'userAccounts'");
      userAccountList = (List<UserAccount>) userAccountService.getUserAccounts();
      logger.info(
          "response following the GET on the endpoint 'userAccounts'.");
    } catch (Exception exception) {
      logger.error("Error in the UserAccountController in the method getUserAccounts :"
          + exception.getMessage());
    }
    return userAccountList;
  }


  /**
   * Read - Get an user account
   * 
   * @param emailAddress An email address
   * @return - A user account
   */

  @GetMapping("/myUserAccount")
  public UserAccount getMyUserAccount(@RequestParam String emailAddress) {
    UserAccount userAccount = null;
    try {
      logger.info("Get request with the endpoint 'myUserAccount'");
      userAccount = userAccountService.getUserAccount(emailAddress);
      logger.info(
          "response following the GET on the endpoint 'myUserAccount'.");
    } catch (Exception exception) {
      logger.error("Error in the UserAccountController in the method getMyUserAccount :" +
          exception.getMessage());
    }
    return userAccount;
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
    logger.info(
        "POST request of the endpoint 'userAccount' with the user account : {" + userAccount + "}");
    if (userAccount.getEmailAddress() == null || userAccount.getPassword() == null || userAccount.getFirstName() == null
        || userAccount.getName() == null) {
      logger.error("The new user account has to get an email address with a password, a name and a first name.");
      throw new IsForbiddenException(
          "The new user account has to get an email address with a password.");
    }
    try {
      userAccount = userAccountService.encryptPassword(userAccount);
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


  /**
   * Delete a user account from a given email address
   * 
   * @param emailAddress The given email address
   */
  /*
   * @Transactional
   * 
   * @DeleteMapping("/userAccount") public void deletePerson(@RequestParam String
   * emailAddress) { boolean existingUserAccountEmail = false; try { logger
   * .info("Delete request with the endpoint 'userAccount' received with parameter emailAddress :"
   * + emailAddress); existingUserAccountEmail =
   * userAccountService.userAccountEmailExist(emailAddress); if
   * (existingUserAccountEmail) {
   * friendService.deleteFriendRelationships(emailAddress);
   * userAccountService.deleteUserAccount(emailAddress); logger.info(
   * "response following the Delete on the endpoint 'userAccount' with the given emailAddress : {"
   * + emailAddress + "}"); } } catch (Exception exception) { logger.
   * error("Error in the UserAccountController in the method deleteUserAccount :"
   * + exception.getMessage()); } if (!existingUserAccountEmail) {
   * logger.error("The user account with the email address " + emailAddress +
   * " doesn't exist."); throw new NonexistentException(
   * "The user account with the email address \" + emailAddress + \" doesn't exist."
   * ); } }
   */

}
