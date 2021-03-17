package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;

public interface ReceiptService {

  /**
   * Get the receipt of a transaction
   * 
   * @param transaction A transaction
   * @return the receipt
   */
  String generateReceipt(Transaction transaction);

}
