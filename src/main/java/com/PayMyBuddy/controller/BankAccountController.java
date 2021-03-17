package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.service.BankAccountService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

  private static final Logger logger = LogManager.getLogger(BankAccountController.class);

  @Autowired
  private BankAccountService bankAccountService;

  /**
   * Read - Get all bank accounts
   * 
   * @return - An Iterable object of bank accounts full filled
   */
  @GetMapping("/bankAccounts")
  public List<BankAccount> getBankAccounts() {
    List<BankAccount> bankAccountsList = new ArrayList<>();
    try {
      logger.info("Get request with the endpoint 'bankAccounts'");
      bankAccountsList = (List<BankAccount>) bankAccountService.getBankAccounts();
      logger.info(
          "response following the GET on the endpoint 'bankAccounts'.");
    } catch (Exception exception) {
      logger.error("Error in the BankAccountController in the method getBankAccounts :"
          + exception.getMessage());
    }
    return bankAccountsList;
  }

  /**
   * Add a new bank account
   * 
   * @param bankAccount An object bankAccount
   * @return The bankAccount object saved
   */
  @PostMapping("/bankAccount")
  public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {
    BankAccount newBankAccount = null;
    if (bankAccount.getEmailAddress() == null || bankAccount.getIban() == null) {
      logger.error("The new bank account has to get an email address with an iban.");
      throw new IsForbiddenException(
          "The new bank account has to get an email address with an iban.");
    }
    try {
      logger.info("Post request with the endpoint 'bankAccount'");
      newBankAccount = bankAccountService.saveBankAccount(bankAccount);
      logger.info(
          "response following the Post on the endpoint 'bankAccount' with the given bankAccount : {"
              + bankAccount.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the BankAccountController in the method createBankAccount :"
          + exception.getMessage());
    }
    return newBankAccount;
  }

}
