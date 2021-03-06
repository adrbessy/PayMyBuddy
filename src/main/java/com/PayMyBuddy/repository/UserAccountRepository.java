package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

  boolean existsByEmailAddress(String emailAddress_user1);

  UserAccount findByEmailAddress(String emailAddress_emitter);

  void deleteUserAccountByEmailAddress(String emailAddress);

}
