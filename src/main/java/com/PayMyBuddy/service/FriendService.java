package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.List;

public interface FriendService {

  /**
   * Save a friend relationship
   * 
   * @param friend A friend relationship to save
   * @return the saved friend relationship
   */
  public Friend saveFriend(Friend friend);

  /**
   * Get all friend relationships
   * 
   * @return all friend relationships
   */
  public Iterable<Friend> getFriendRelationships();

  /**
   * Check if the friend relationship exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if it exists, otherwise returns false
   */
  public boolean friendRelationshipExist(String emailAddress_user1, String emailAddress_user2);

  /**
   * Delete all friend relationships of an user account.
   * 
   * @param emailAddress The given email of the user account
   */
  public void deleteFriendRelationships(String emailAddress);

  /**
   * Get all friend relationships of one user
   * 
   * @param emailAddress An email address
   * 
   * @return a list of the friend relationships of one user
   */
  public List<UserAccountDto> getFriendsOfOneUser(String emailAddress);

  /**
   * Delete a friend relationship of one user
   * 
   * @param emailAddress         An email address
   * @param emailAddressToDelete The email address of the friend
   * 
   * @return the deleted friend
   */
  public UserAccountDto deleteFriendOfOneUser(String emailAddress, String emailAddressToDelete);

}
