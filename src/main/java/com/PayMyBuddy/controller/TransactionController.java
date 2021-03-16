package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.BankAccountService;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserAccountService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private static final Logger logger = LogManager.getLogger(TransactionController.class);

  @Autowired
  private TransactionService transactionService;
  @Autowired
  private FriendService friendService;
  @Autowired
  private UserAccountService userAccountService;

  @Autowired
  private BankAccountService bankAccountService;

  /**
   * Read - Get all transactions
   * 
   * @return - An Iterable object of userAccounts full filled
   */
  @GetMapping("/transactions")
  public Iterable<Transaction> getTransactions() {
    return transactionService.getTransactions();
  }

  /**
   * Read - Get all transactions of one user
   * 
   * @param emailAddress The email address of the user
   * @return - A List of transactions
   */
  @GetMapping("/myTransactions")
  public List<Transaction> getMyTransactions(@RequestParam String emailAddress) {
    return transactionService.getTransactionsOfOneUser(emailAddress);
  }

  /**
   * Create a friend transaction
   * 
   * @param friendTransaction An object friendTransaction
   * @return The friendTransaction object saved
   */
  @PostMapping("/friendTransaction")
  public Transaction createFriendTransaction(@RequestBody Transaction friendTransaction) {
    Transaction newFriendTransaction = null;
    boolean existingFriendRelationship = false;
    boolean checkIfEnoughMoney = false;
    try {
      logger.info("Post request with the endpoint 'friendTransaction'");
      existingFriendRelationship = friendService.friendRelationshipExist(friendTransaction.getEmailAddressEmitter(),
          friendTransaction.getEmailAddressReceiver());
      if (existingFriendRelationship) {
        checkIfEnoughMoney = userAccountService.checkEnoughMoney(friendTransaction.getEmailAddressEmitter(),
            friendTransaction.getAmount());
        if (checkIfEnoughMoney) {
          newFriendTransaction = transactionService.makeFriendTransaction(friendTransaction);
          logger.info(
              "response following the Post on the endpoint 'friendTransaction' with the given friendTransaction : {"
                  + friendTransaction.toString() + "}");
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the TransactionController in the method createFriendTransaction :"
          + exception.getMessage());
    }
    if (existingFriendRelationship == false) {
      logger.error("The friend relationship between " + friendTransaction.getEmailAddressEmitter() + " and "
          + friendTransaction.getEmailAddressReceiver() + " doesn't exist.");
      throw new NonexistentException(
          "The friend relationship between " + friendTransaction.getEmailAddressEmitter() + " and "
              + friendTransaction.getEmailAddressReceiver() + " doesn't exist.");
    }
    if (!checkIfEnoughMoney) {
      logger.error("The emitter has not enough money on his account to make a transaction.");
      throw new IsForbiddenException(
          "The emitter has not enough money on his account to make a transaction.");
    }
    return newFriendTransaction;
  }

  /**
   * Create a transaction from the bank account to the app account
   * 
   * @param moneyDeposit An Transaction object
   * @return The moneyDeposit object saved
   */
  @PostMapping("/moneyDeposit")
  public Transaction createMoneyDeposit(@RequestBody Transaction moneyDeposit) {
    Transaction newMoneyDeposit = null;
    boolean existingUserAccount = false;
    try {
      logger.info("Post request with the endpoint 'moneyDeposit'");
      existingUserAccount = userAccountService.userAccountEmailExist(moneyDeposit.getEmailAddressReceiver());
      if (existingUserAccount) {
        newMoneyDeposit = transactionService.makeMoneyDeposit(moneyDeposit);
        logger.info(
            "response following the Post on the endpoint 'moneyDeposit' with the given moneyDeposit : {"
                + moneyDeposit.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the TransactionController in the method createMoneyDeposit :"
          + exception.getMessage());
    }
    if (existingUserAccount == false) {
      logger.error("The user account "
          + moneyDeposit.getEmailAddressReceiver() + " doesn't exist.");
      throw new NonexistentException(
          "The user account "
              + moneyDeposit.getEmailAddressReceiver() + " doesn't exist.");
    }
    return newMoneyDeposit;
  }

  /**
   * Create a transaction from the bank account to the app account
   * 
   * @param moneyDeposit An Transaction object
   * @return The moneyDeposit object saved
   */
  @PostMapping("/transactionToBankAccount")
  public Transaction createTransactionToBankAccount(@RequestBody Transaction transactionToBankAccount) {
    Transaction newTransactionToBankAccount = null;
    boolean existingUserAccount = false;
    boolean existingBankAccount = false;
    boolean checkIfEnoughMoney = false;
    try {
      logger.info("Post request with the endpoint 'TransactionToBankAccount'");
      existingUserAccount = userAccountService
          .userAccountEmailExist(transactionToBankAccount.getEmailAddressEmitter());
      if (existingUserAccount) {
        existingBankAccount = bankAccountService.bankAccountExist(transactionToBankAccount.getEmailAddressEmitter(),
            transactionToBankAccount.getIdBankAccount());
        if (existingBankAccount) {
          checkIfEnoughMoney = userAccountService.checkEnoughMoney(transactionToBankAccount.getEmailAddressEmitter(),
              transactionToBankAccount.getAmount());
          if (checkIfEnoughMoney) {
            newTransactionToBankAccount = transactionService.makeTransactionToBankAccount(transactionToBankAccount);
            logger.info(
                "response following the Post on the endpoint 'transactionToBankAccount' with the given transactionToBankAccount : {"
                    + transactionToBankAccount.toString() + "}");
          }
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the TransactionController in the method createTransactionToBankAccount :"
          + exception.getMessage());
    }
    if (existingUserAccount == false) {
      logger.error("The user account "
          + transactionToBankAccount.getEmailAddressEmitter() + " doesn't exist.");
      throw new NonexistentException(
          "The user account "
              + transactionToBankAccount.getEmailAddressEmitter() + " doesn't exist.");
    }
    if (!existingBankAccount) {
      logger.error("The emitter has not this bank account.");
      throw new NonexistentException(
          "The emitter has not this bank account.");
    }
    if (!checkIfEnoughMoney) {
      logger.error("The emitter has not enough money on his account to make a transaction.");
      throw new IsForbiddenException(
          "The emitter has not enough money on his account to make a transaction.");
    }
    return newTransactionToBankAccount;
  }

}
