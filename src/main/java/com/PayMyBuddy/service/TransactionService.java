package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import java.util.List;

public interface TransactionService {

  /**
   * Make a transaction from a user to one of his friend
   * 
   * @param friendTransaction A transaction to make
   * @return the transaction made
   */
  Transaction makeFriendTransaction(Transaction friendTransaction);

  /**
   * Get all transactions
   * 
   * @return all transactions
   */
  List<Transaction> getTransactions();

  /**
   * Make a transaction from a bank account of an user to the count of the app of
   * this user
   * 
   * @param moneyDeposit A transaction to make
   * @return the transaction made
   */
  Transaction makeMoneyDeposit(Transaction moneyDeposit);

  /**
   * Make a transaction from an user to one of his bank account
   * 
   * @param transactionToBankAccount A transaction to make
   * @return the transaction made
   */
  Transaction makeTransactionToBankAccount(Transaction transactionToBankAccount);

  /**
   * Get all transactions of one user
   * 
   * @param emailAddress An email address
   * 
   * @return a list of the transactions of one user
   */
  List<TransactionDto> getTransactionsOfOneUser(String emailAddress);

}
