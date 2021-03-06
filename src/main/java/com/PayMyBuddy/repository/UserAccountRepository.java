package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  boolean existsByEmailAddress(String emailAddress);

  UserAccount findByEmailAddress(String emailAddress);

  void deleteUserAccountByEmailAddress(String emailAddress);

  UserAccount findDistinctByEmailAddress(String stationIterator);

}
