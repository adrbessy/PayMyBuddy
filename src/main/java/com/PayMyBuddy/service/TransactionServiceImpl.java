package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl {

  private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private UserAccountService userAccountService;

  /**
   * Make a transaction from a user to one of his friend
   * 
   * @param friendTransaction A transaction to make
   * @return the transaction made
   */
  public Transaction makeFriendTransaction(Transaction friendTransaction) {
    UserAccount emitter = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddress_emitter());
    double emitter_amount = emitter.getAmount();
    double final_emitter_amount = emitter_amount - friendTransaction.getAmount();
    if (final_emitter_amount >= 0.0) {
      emitter.setAmount(final_emitter_amount);
      userAccountService.saveUserAccount(emitter);
      UserAccount receiver = userAccountRepository.findByEmailAddress(friendTransaction.getEmailAddress_receiver());
      receiver.setAmount(receiver.getAmount() + friendTransaction.getAmount());
      userAccountService.saveUserAccount(receiver);
    }
    return null;
  }

}
