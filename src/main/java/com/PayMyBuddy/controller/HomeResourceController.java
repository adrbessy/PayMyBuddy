package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeResourceController {

  @Autowired
  private UserAccountController userAccountController;
  @Autowired
  private FriendController friendController;
  @Autowired
  private TransactionController transactionController;
  @Autowired
  private BankAccountController bankAccountController;

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
    model.addAttribute("user", userAccount);
    return "user";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/profile")
  public String profile(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    List<BankAccount> myBankAccounts = bankAccountController.getMyBankAccounts(username);
    BankAccount newBankAccount = new BankAccount();
    model.addAttribute("myBankAccounts", myBankAccounts);
    model.addAttribute("newBankAccount", newBankAccount);
    return "profile";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @PostMapping("/addNewBankAccount")
  public ModelAndView addNewBankAccount(@ModelAttribute BankAccount bankAccount,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    bankAccount.setEmailAddress(username);
    bankAccountController.createBankAccount(bankAccount);
    return new ModelAndView("redirect:/profile");
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/deleteBankAccount")
  public ModelAndView deleteBankAccount(@RequestParam String emailAddress, @RequestParam String iban) {
    bankAccountController.deleteBankAccount(emailAddress, iban);
    return new ModelAndView("redirect:/profile");
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/contact")
  public String contact(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    List<UserAccountDto> friendList = friendController.getMyFriends(username);
    UserAccount userAccount = new UserAccount();
    model.addAttribute("friends", friendList);
    model.addAttribute("userAccount", userAccount);
    return "friend";
  }

  /**
   * 
   * @return - The name of the html transaction page
   */
  @GetMapping("/transac")
  public String transac(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    List<TransactionDto> transactionList = transactionController.getMyTransactions(username);
    UserAccount userAccount = userAccountController.getMyUserAccount(username);
    List<UserAccountDto> friendList = friendController.getMyFriends(username);
    List<BankAccount> myBankAccounts = bankAccountController.getMyBankAccounts(username);
    Transaction newTransaction = new Transaction();
    model.addAttribute("transactions", transactionList);
    model.addAttribute("userAccount", userAccount);
    model.addAttribute("friends", friendList);
    model.addAttribute("newTransaction", newTransaction);
    model.addAttribute("myBankAccounts", myBankAccounts);
    return "transaction";
  }

  /**
   * 
   * @return - The name of the html transaction page
   */
  @PostMapping("/makeTransaction")
  public ModelAndView maketransac(@ModelAttribute Transaction newTransaction,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    newTransaction.setEmailAddressEmitter(username);
    transactionController.createFriendTransaction(newTransaction);
    return new ModelAndView("redirect:/transac");
  }

  /**
   * 
   * @return - The name of the html transaction page
   */
  @PostMapping("/ToBankAccount")
  public ModelAndView toBankAccount(@ModelAttribute Transaction newTransaction,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    newTransaction.setEmailAddressEmitter(username);
    transactionController.createTransactionToBankAccount(newTransaction);
    return new ModelAndView("redirect:/transac");
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  /**
   * 
   * @return - The name of the html page
   */
  @GetMapping("/deleteFriend")
  public ModelAndView deleteFriend(@RequestParam String emailAddress,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    friendController.deleteMyFriend(username, emailAddress);
    return new ModelAndView("redirect:/contact");
  }

  /**
   * 
   * @return - The name of the html page
   */
  @PostMapping("/addFriend")
  public ModelAndView addFriend(@ModelAttribute UserAccount userAccount,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    Friend friend = new Friend();
    friend.setEmailAddressUser1(username);
    friend.setEmailAddressUser2(userAccount.getEmailAddress());
    friendController.createFriend(friend);
    return new ModelAndView("redirect:/contact");
  }

}
