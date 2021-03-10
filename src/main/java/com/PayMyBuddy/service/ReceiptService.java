package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;

public interface ReceiptService {

  String generateReceipt(Transaction transaction);

}
