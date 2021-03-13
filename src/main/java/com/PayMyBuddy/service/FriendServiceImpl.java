package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.repository.FriendRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

  private static final Logger logger = LogManager.getLogger(FriendServiceImpl.class);

  @Autowired
  private FriendRepository friendRepository;

  @Autowired
  private UserAccountRepository userAccountRepository;

  /**
   * Save a friend relationship
   * 
   * @param friend A friend relationship to save
   * @return the saved friend relationship
   */
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

  /**
   * Get all friend relationships
   * 
   * @return all friend relationships
   */
  @Override
  public Iterable<Friend> getFriendRelationships() {
    return friendRepository.findAll();
  }

  /**
   * Check if the emails exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if they exist, otherwise returns false
   */
  @Override
  public String friendsExist(String emailAddress_user1, String emailAddress_user2) {
    logger.debug("in the method friendsExist in the class FriendServiceImpl");
    boolean existingEmail1 = userAccountRepository.existsByEmailAddress(emailAddress_user1);
    boolean existingEmail2 = userAccountRepository.existsByEmailAddress(emailAddress_user2);
    if (existingEmail1 && existingEmail2) {
      return "yes";
    }
    if (existingEmail1 == false && existingEmail2 == false) {
      return emailAddress_user1 + " and " + emailAddress_user2 + " don't exist";
    }
    if (existingEmail1 == false && existingEmail2 == true) {
      return emailAddress_user1 + " doesn't exist";
    } else {
      return emailAddress_user2 + " doesn't exist";
    }
  }

  @Override
  public boolean friendRelationshipExist(String emailAddress_user1, String emailAddress_user2) {
    logger.debug("in the method friendRelationshipExist in the class FriendServiceImpl");
    boolean existingRelationshipSens1 = friendRepository.existsByEmailAddressUser1AndEmailAddressUser2(
        emailAddress_user1,
        emailAddress_user2);
    boolean existingRelationshipSens2 = friendRepository.existsByEmailAddressUser1AndEmailAddressUser2(
        emailAddress_user2,
        emailAddress_user1);
    if (existingRelationshipSens1 || existingRelationshipSens2) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Delete all friend relationships of an user account.
   * 
   * @param emailAddress The given email of the user account
   */
  @Override
  public void deleteFriendRelationships(String emailAddress) {
    friendRepository.deleteFriendByEmailAddressUser1(emailAddress);
    friendRepository.deleteFriendByEmailAddressUser2(emailAddress);
  }

  /**
   * Get all friend relationships of one user
   * 
   * @param emailAddress
   * 
   * @return all friend relationships of one user
   */
  @Override
  public List<String> getFriendsOfOneUser(String emailAddress) {
    List<String> friendList = new ArrayList<>();
    List<Friend> friendList1 = friendRepository.findByEmailAddressUser1(emailAddress);
    friendList1.forEach(friendIterator -> {
      friendList.add(friendIterator.getEmailAddressUser2());
    });
    List<Friend> friendList2 = friendRepository.findByEmailAddressUser2(emailAddress);
    friendList2.forEach(friendIterator -> {
      friendList.add(friendIterator.getEmailAddressUser1());
    });
    return friendList;
  }

}
