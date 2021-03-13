package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

  boolean existsByEmailAddressAndIban(String emailAddressReceiver, String iban);

  BankAccount findByIban(String iban);

  BankAccount findById(String idBankAccount);

  boolean existsByEmailAddressAndId(String emailAddressReceiver, String id);

}
