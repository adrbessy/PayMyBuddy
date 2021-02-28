package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {

  boolean existsByEmailAddressUser1AndEmailAddressUser2(String emailAddress_user1, String emailAddress_user2);

}
