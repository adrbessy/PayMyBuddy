package com.PayMyBuddy.service;

import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.Date;
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
      UserAccount emitter = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddress_emitter());
      double emitter_amount = emitter.getAmount();
      double tax_amount = friendTransaction.getAmount() * Tax.TAX100 / 100;
      // add here the transaction of tax_amount towards the account of the app
      double final_emitter_amount = emitter_amount - (friendTransaction.getAmount() + tax_amount);
      emitter.setAmount(final_emitter_amount);
      userAccountService.saveUserAccount(emitter);
      UserAccount receiver = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddress_receiver());
      receiver.setAmount(receiver.getAmount() + friendTransaction.getAmount());
      userAccountService.saveUserAccount(receiver);
      friendTransaction.setMy_date(new Date());
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

}
