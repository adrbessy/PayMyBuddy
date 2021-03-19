package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResourceController {

  @Autowired
  private UserAccountController userAccountController;
  @Autowired
  private FriendController friendController;
  @Autowired
  private TransactionController transactionController;


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
  public String user(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    UserAccount userAccount = userAccountController.getMyUserAccount(username);
    model.addAttribute("userAccount", userAccount);
    return "user";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/contact")
  public String contact(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    List<UserAccountDto> friendList = friendController.getMyFriends(username);
    model.addAttribute("friends", friendList);
    return "friend";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/transac")
  public String transac(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    List<TransactionDto> transactionList = transactionController.getMyTransactions(username);
    UserAccount userAccount = userAccountController.getMyUserAccount(username);
    model.addAttribute("transactions", transactionList);
    model.addAttribute("userAccount", userAccount);
    return "transaction";
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
