package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.repository.FriendRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

  private static final Logger logger = LogManager.getLogger(FriendServiceImpl.class);

  @Autowired
  private FriendRepository friendRepository;

  @Override
  public Friend saveFriend(Friend friend) {
    logger.debug("in the method saveFriend in the class FriendServiceImpl");
    Friend savedFriend = null;
    try {
      savedFriend = friendRepository.save(friend);
    } catch (Exception exception) {
      logger.error("Error when we try to save a friend relationship :" + exception.getMessage());
    }
    return savedFriend;
  }

}
