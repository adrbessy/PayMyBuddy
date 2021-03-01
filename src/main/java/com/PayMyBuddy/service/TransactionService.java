package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;

public interface TransactionService {

  /**
   * Make a transaction from a user to one of his friend
   * 
   * @param friendTransaction A transaction to make
   * @return the transaction made
   */
  Transaction makeFriendTransaction(Transaction friendTransaction);

}
