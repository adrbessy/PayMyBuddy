package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Friend;

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
  public Iterable<Friend> getFriends();

  /**
   * Check if the emails exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if they exist, otherwise returns false
   */
  public String friendsExist(String emailAddress_user1, String emailAddress_user2);

  /**
   * Check if the friend relationship exist.
   * 
   * @param emailAddress_user1 The given email
   * @param emailAddress_user2 The given email
   * @return true if it exists, otherwise returns false
   */
  public boolean friendRelationshipExist(String emailAddress_user1, String emailAddress_user2);

}
