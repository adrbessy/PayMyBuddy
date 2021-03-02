package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
      existingFriendRelationship = friendService.friendRelationshipExist(friendTransaction.getEmailAddress_emitter(),
          friendTransaction.getEmailAddress_receiver());
      if (existingFriendRelationship) {
        checkIfEnoughMoney = userAccountService.checkEnoughMoney(friendTransaction.getEmailAddress_emitter(),
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
      logger.error("The friend relationship between " + friendTransaction.getEmailAddress_emitter() + " and "
          + friendTransaction.getEmailAddress_receiver() + " doesn't exist.");
      throw new IllegalArgumentException(
          "The friend relationship between " + friendTransaction.getEmailAddress_emitter() + " and "
              + friendTransaction.getEmailAddress_receiver() + " doesn't exist.");
    }
    if (!checkIfEnoughMoney) {
      logger.error("The emitter has not enough money on his account to make a transaction.");
      throw new IllegalArgumentException(
          "The emitter has not enough money on his account to make a transaction.");
    }
    return newFriendTransaction;
  }

}
