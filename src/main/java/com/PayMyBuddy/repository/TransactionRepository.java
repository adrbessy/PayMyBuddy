package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.Transaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  List<Transaction> findByEmailAddressEmitter(String emailAddress);

  List<Transaction> findByEmailAddressReceiver(String emailAddress);

}
