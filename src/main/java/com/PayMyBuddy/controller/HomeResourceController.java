package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.UserAccount;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResourceController {

  @Autowired
  private UserAccountController userAccountController;

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/")
  public String home() {
    return "index";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/user")
  public String user(Model model) {
    List<UserAccount> userList = userAccountController.getUserAccounts();
    model.addAttribute("user", userList);
    return "user";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

}
