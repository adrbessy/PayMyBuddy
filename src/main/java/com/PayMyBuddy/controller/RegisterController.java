package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.Data;

@Data
@Controller
public class RegisterController {

  private static final Logger logger = LogManager.getLogger(RegisterController.class);

  @Autowired
  private UserAccountService userAccountService;

  @GetMapping("/register")
  public String register(Model model) {
    UserAccount user = new UserAccount();
    model.addAttribute("userAccount", user);
    return "signup_form";
  }

  /**
   * Add a new user account
   * 
   * @param userAccount An object userAccount
   * @return The name of the html file
   */
  @PostMapping("/saveUserAccount")
  public String createUserAccount(UserAccount userAccount) {
    UserAccount newUserAccount = null;
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
    return "register_success";
  }

}
