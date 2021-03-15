package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
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
  private MapService mapService;

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
    logger.debug("in the method getFriendRelationships in the class FriendServiceImpl");
    return friendRepository.findAll();
  }

  /**
   * Check if the friend relationship exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if it exists, otherwise returns false
   */
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
    logger.debug("in the method deleteFriendRelationships in the class FriendServiceImpl");
    friendRepository.deleteFriendByEmailAddressUser1(emailAddress);
    friendRepository.deleteFriendByEmailAddressUser2(emailAddress);
  }

  /**
   * Get all friend relationships of one user
   * 
   * @param emailAddress
   * 
   * @return a list of the friend relationships of one user
   */
  @Override
  public List<UserAccountDto> getFriendsOfOneUser(String emailAddress) {
    logger.debug("in the method getFriendsOfOneUser in the class FriendServiceImpl");
    List<String> emailList = new ArrayList<>();
    List<Friend> friendList1 = friendRepository.findByEmailAddressUser1(emailAddress);
    friendList1.forEach(friendIterator -> {
      emailList.add(friendIterator.getEmailAddressUser2());
    });
    List<Friend> friendList2 = friendRepository.findByEmailAddressUser2(emailAddress);
    friendList2.forEach(friendIterator -> {
      emailList.add(friendIterator.getEmailAddressUser1());
    });
    List<UserAccount> userList = new ArrayList<>();
    emailList.forEach(stationIterator -> {
      // we retrieve the list of stations corresponding to the stationNumber
      userList.add(userAccountRepository.findDistinctByEmailAddress(stationIterator));
    });
    List<UserAccountDto> userAccountDtoList = mapService.convertToUserAccountDtoList(userList);
    return userAccountDtoList;
  }

}
