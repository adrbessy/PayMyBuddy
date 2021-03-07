package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

}
