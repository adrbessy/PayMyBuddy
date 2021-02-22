package com.PayMyBuddy.repository;

import com.PayMyBuddy.model.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {

}
