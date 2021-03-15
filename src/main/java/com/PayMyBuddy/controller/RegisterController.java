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
    UserAccount newUserAccount = userAccountController.createUserAccount(userAccount);
    if (newUserAccount != null) {
      return "register_success";
    } else {
      return "register_fail";
    }
  }

}
