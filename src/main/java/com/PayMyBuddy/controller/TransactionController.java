package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.FriendTransaction;
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

  /**
   * Create a friend transaction
   * 
   * @param friendTransaction An object friendTransaction
   * @return The friendTransaction object saved
   */
  @PostMapping("/friendTransaction")
  public FriendTransaction createFriendTransaction(@RequestBody FriendTransaction friendTransaction) {
    FriendTransaction newFriendTransaction = null;
    try {
      newFriendTransaction = transactionService.saveFriendTransaction(friendTransaction);
      logger.info(
          "response following the Post on the endpoint 'friendTransaction' with the given friendTransaction : {"
              + friendTransaction.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the TransactionController in the method createFriendTransaction :"
          + exception.getMessage());
    }
    return newFriendTransaction;
  }

}
