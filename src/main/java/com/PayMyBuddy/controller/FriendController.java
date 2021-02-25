package com.PayMyBuddy.controller;

import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.service.FriendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

  private static final Logger logger = LogManager.getLogger(FriendController.class);

  @Autowired
  private FriendService friendService;

  /**
   * Add a new friend relationship
   * 
   * @param friend An object friend
   * @return The friend object saved
   */
  @PostMapping("/friend")
  public Friend createFriend(@RequestBody Friend friend) {
    Friend newFriend = null;
    try {
      newFriend = friendService.saveFriend(friend);
      logger.info(
          "response following the Post on the endpoint 'friend' with the given friend : {"
              + friend.toString() + "}");
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method createFriend :"
          + exception.getMessage());
    }
    return newFriend;
  }

}
