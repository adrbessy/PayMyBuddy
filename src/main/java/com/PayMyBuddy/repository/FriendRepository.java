package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.Friend;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {

  boolean existsByEmailAddressUser1AndEmailAddressUser2(String emailAddress_user1, String emailAddress_user2);

  void deleteFriendByEmailAddressUser1(String emailAddress);

  void deleteFriendByEmailAddressUser2(String emailAddress);

  List<Friend> findByEmailAddressUser1(String emailAddress);

  List<Friend> findByEmailAddressUser2(String emailAddress);

}
