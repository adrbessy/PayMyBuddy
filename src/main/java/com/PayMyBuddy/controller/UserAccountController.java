package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public UserAccount createPerson(@RequestBody UserAccount userAccount) {
    UserAccount newUserAccount = null;
    try {
      newUserAccount = userAccountService.saveUserAccount(userAccount);
      logger.info(
          "response following the Post on the endpoint 'userAccount' with the given userAccount : {"
              + userAccount.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the UserAccountController in the method createuserAccount :"
          + exception.getMessage());
    }
    return newUserAccount;
  }

}
