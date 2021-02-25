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

}
