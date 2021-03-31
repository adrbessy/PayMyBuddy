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
public class UserInterfaceController {

  @Autowired
  private UserAccountController userAccountController;
  @Autowired
  private FriendController friendController;
  @Autowired
  private TransactionController transactionController;
  @Autowired
  private BankAccountController bankAccountController;

  /**
   * Just calls the home page
   * 
   * @return - The name of the html page
   */
  @GetMapping("/")
  public String home() {
    return "index";
  }

  /**
   * Just calls the login page
   * 
   * @return - The name of the html page
   */
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  /**
   * Read - Get an user account and calls the user page
   * 
   * @param model    A model
   * @param username The username
   * @return - The name of the html page
   */
  @GetMapping("/user")
  public String user(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
    UserAccount userAccount = userAccountController.getMyUserAccount(username);
    model.addAttribute("user", userAccount);
    return "user";
  }

  /**
   * Get the information about the bank account of the user and calls the profile
   * page
   * 
   * @param model    A model
   * @param username The username
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
   * @param username    The username
   * @param bankAccount A bank account object
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
   * @param model        A model
   * @param emailAddress The email address
   * @param iban         An iban
   * @param username     The username
   * @return - The name of the html page
   */
  @GetMapping("/deleteBankAccount")
  public String deleteBankAccount(Model model, @RequestParam String emailAddress, @RequestParam String iban,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    bankAccountController.deleteBankAccount(emailAddress, iban);
    List<BankAccount> myBankAccounts = bankAccountController.getMyBankAccounts(username);
    BankAccount newBankAccount = new BankAccount();
    model.addAttribute("myBankAccounts", myBankAccounts);
    model.addAttribute("newBankAccount", newBankAccount);
    return "profile";
  }

  /**
   * @param model        A model
   * @param emailAddress The email address
   * @param username     The username
   * @return - The name of the html page
   */
  @GetMapping("/deleteFriend")
  public String deleteFriend(Model model, @RequestParam String emailAddress,
      @CurrentSecurityContext(expression = "authentication?.name") String username) {
    friendController.deleteMyFriend(username, emailAddress);
    List<UserAccountDto> friendList = friendController.getMyFriends(username);
    UserAccount userAccount = new UserAccount();
    model.addAttribute("friends", friendList);
    model.addAttribute("userAccount", userAccount);
    return "friend";
  }

  /**
   * @param model    A model
   * @param username The username
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
   * @param model    A model
   * @param username The username
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
   * @param username       The username
   * @param newTransaction A transaction object
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
   * @param username       The username
   * @param newTransaction A transaction object
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
   * @param username    The username
   * @param userAccount An user account
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

  /**
   * Read - Get all user accounts and calls the admin page
   * 
   * @param model A model
   * @return - The name of the html page
   */
  @GetMapping("/admin")
  public String admin(Model model) {
    List<UserAccount> userAccounts = userAccountController.getUserAccounts();
    Transaction newTransaction = new Transaction();
    model.addAttribute("userAccounts", userAccounts);
    model.addAttribute("newTransaction", newTransaction);
    return "admin";
  }

  /**
   * Deposit money, get all the transactions of the user that just received money,
   * get the information about the user accounts, get the userAccount of the user
   * that just received money.
   * 
   * @param model          A model
   * @param newTransaction A transaction object
   * @return - The name of the html admin-user page
   */
  @PostMapping("/deposit")
  public String deposit(Model model, @ModelAttribute Transaction newTransaction) {
    transactionController.createMoneyDeposit(newTransaction);
    List<TransactionDto> transactionList = transactionController
        .getMyTransactions(newTransaction.getEmailAddressReceiver());
    List<UserAccount> userAccounts = userAccountController.getUserAccounts();
    Transaction Transaction = new Transaction();
    UserAccount userAccount = userAccountController.getMyUserAccount(newTransaction.getEmailAddressReceiver());
    model.addAttribute("userAccounts", userAccounts);
    model.addAttribute("newTransaction", Transaction);
    model.addAttribute("transactionList", transactionList);
    model.addAttribute("userAccount", userAccount);
    return ("admin_user");
  }

}
