package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.TransactionService;
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
    try {
      existingFriendRelationship = friendService.friendRelationshipExist(friendTransaction.getEmailAddress_emitter(),
          friendTransaction.getEmailAddress_receiver());
      if (existingFriendRelationship == false) {
        newFriendTransaction = transactionService.makeFriendTransaction(friendTransaction);
        logger.info(
            "response following the Post on the endpoint 'friendTransaction' with the given friendTransaction : {"
                + friendTransaction.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the TransactionController in the method createFriendTransaction :"
          + exception.getMessage());
    }
    if (existingFriendRelationship) {
      logger.error("The friend relationship between " + friendTransaction.getEmailAddress_emitter() + " and "
          + friendTransaction.getEmailAddress_receiver() + " already exist.");
      throw new IllegalArgumentException(
          "The friend relationship between " + friendTransaction.getEmailAddress_emitter() + " and "
              + friendTransaction.getEmailAddress_receiver() + " already exist.");
    }
    return newFriendTransaction;
  }

}
