package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {

  private static final Logger logger = LogManager.getLogger(RegisterController.class);

  @Autowired
  private UserAccountController userAccountController;

  /**
   * Retrieve the model to sign up
   * 
   * @return - The name of the html page
   */
  @GetMapping("/register")
  public String register(Model model) {
    try {
      logger.info(
          "GET request of the endpoint 'register'");
      UserAccount user = new UserAccount();
      model.addAttribute("userAccount", user);
    } catch (Exception exception) {
      logger.error("Error in the RegisterController in the method register :"
          + exception.getMessage());
    }
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
    try {
      logger.info(
          "POST request of the endpoint 'saveUserAccount'");
      userAccountController.createUserAccount(userAccount);
    } catch (Exception exception) {
      logger.error("Error in the RegisterController in the method createUserAccount :"
          + exception.getMessage());
    }
    return "register_success";

  }

}
