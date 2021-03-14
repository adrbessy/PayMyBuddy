package com.PayMyBuddy.service;

import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private UserAccountService userAccountService;


  /**
   * Make a transaction from a user to one of his friend
   * 
   * @param friendTransaction A transaction to make
   * @return the transaction made
   */
  @Override
  @Transactional(rollbackOn = { Exception.class })
  public Transaction makeFriendTransaction(Transaction friendTransaction) {
    logger.debug("in the method makeFriendTransaction in the class TransactionServiceImpl");
    try {
      UserAccount emitter = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddressEmitter());
      double emitterAmount = emitter.getAmount();
      double taxAmount = friendTransaction.getAmount() * Tax.TAX100 / 100;
      // add here the transaction of tax_amount towards the account of the app
      double finalEmitterAmount = emitterAmount - (friendTransaction.getAmount() + taxAmount);
      emitter.setAmount(finalEmitterAmount);
      userAccountService.saveUserAccount(emitter);
      UserAccount receiver = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddressReceiver());
      receiver.setAmount(receiver.getAmount() + friendTransaction.getAmount());
      userAccountService.saveUserAccount(receiver);
      friendTransaction.setMyDate(new Date());
      saveTransaction(friendTransaction);
    } catch (Exception exception) {
      logger.error("Error when we try to make the transaction :" + exception.getMessage());
    }
    return friendTransaction;
  }

  private Transaction saveTransaction(Transaction friendTransaction) {
    logger.debug("in the method saveTransaction in the class TransactionServiceImpl");
    Transaction savedTransaction = null;
    try {
      savedTransaction = transactionRepository.save(friendTransaction);
    } catch (Exception exception) {
      logger.error("Error when we try to save a friend transaction :" + exception.getMessage());
    }
    return savedTransaction;
  }

  @Override
  public Iterable<Transaction> getTransactions() {
    return transactionRepository.findAll();
  }

  /**
   * Make a transaction from a bank account of an user to the count of the app of
   * this user
   * 
   * @param moneyDeposit A transaction to make
   * @return the transaction made
   */
  @Override
  @Transactional(rollbackOn = { Exception.class })
  public Transaction makeMoneyDeposit(Transaction moneyDeposit) {
    logger.debug("in the method makeFriendTransaction in the class TransactionServiceImpl");
    try {
      UserAccount receiver = userAccountRepository.findByEmailAddress(moneyDeposit.getEmailAddressReceiver());
      receiver.setAmount(receiver.getAmount() + moneyDeposit.getAmount());
      userAccountService.saveUserAccount(receiver);
      moneyDeposit.setMyDate(new Date());
      saveTransaction(moneyDeposit);
    } catch (Exception exception) {
      logger.error("Error when we try to make the transaction :" + exception.getMessage());
    }
    return moneyDeposit;
  }

  /**
   * Make a transaction from an user to one of his bank account
   * 
   * @param transactionToBankAccount A transaction to make
   * @return the transaction made
   */
  @Override
  @Transactional(rollbackOn = { Exception.class })
  public Transaction makeTransactionToBankAccount(Transaction transactionToBankAccount) {
    logger.debug("in the method makeFriendTransaction in the class TransactionServiceImpl");
    try {
      double taxAmount = transactionToBankAccount.getAmount() * Tax.TAX100 / 100;
      UserAccount emitter = userAccountRepository.findByEmailAddress(transactionToBankAccount.getEmailAddressEmitter());
      emitter.setAmount(emitter.getAmount() - (transactionToBankAccount.getAmount() + taxAmount));
      userAccountService.saveUserAccount(emitter);
      // add here the transaction to the bank account via the api of the bank
      transactionToBankAccount.setMyDate(new Date());
      saveTransaction(transactionToBankAccount);
    } catch (Exception exception) {
      logger.error("Error when we try to make the transaction :" + exception.getMessage());
    }
    return transactionToBankAccount;
  }

  /**
   * Get all transactions of one user
   * 
   * @param emailAddress
   * 
   * @return a list of the transactions of one user
   */
  @Override
  public List<Transaction> getTransactionsOfOneUser(String emailAddress) {
    List<Transaction> transactionList1 = transactionRepository.findByEmailAddressEmitter(emailAddress);
    List<Transaction> transactionList2 = transactionRepository.findByEmailAddressReceiver(emailAddress);
    List<Transaction> transactionList = Stream.concat(transactionList1.stream(), transactionList2.stream())
        .collect(Collectors.toList());
    return transactionList;
  }

}
