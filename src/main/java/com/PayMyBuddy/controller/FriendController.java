package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccountDto;
import com.PayMyBuddy.service.FriendService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

  private static final Logger logger = LogManager.getLogger(FriendController.class);

  @Autowired
  private FriendService friendService;

  /**
   * Read - Get all friend relationships
   * 
   * @return - An Iterable object of friend relationships full filled
   */
  @GetMapping("/friends")
  public Iterable<Friend> getFriends() {
    return friendService.getFriendRelationships();
  }

  /**
   * Read - Get friend relationships of one user
   * 
   * @return - An Iterable object of friend relationships full filled
   */
  @GetMapping("/myFriends")
  public List<UserAccountDto> getFriends(@RequestParam String emailAddress) {
    return friendService.getFriendsOfOneUser(emailAddress);
  }

  /**
   * Add a new friend relationship
   * 
   * @param friend An object friend
   * @return The friend object saved
   */
  @PostMapping("/friend")
  public Friend createFriend(@RequestBody Friend friend) {
    Friend newFriend = null;
    String existingFriends = null;
    boolean existingFriendRelationship = false;
    try {
      logger.info("Post request with the endpoint 'friend'");
      existingFriends = friendService.friendsExist(friend.getEmailAddressUser1(), friend.getEmailAddressUser2());
      existingFriendRelationship = friendService.friendRelationshipExist(friend.getEmailAddressUser1(),
          friend.getEmailAddressUser2());
      if (existingFriends == "yes" && existingFriendRelationship == false) {
        newFriend = friendService.saveFriend(friend);
        logger.info(
            "response following the Post on the endpoint 'friend' with the given friend : {"
                + friend.toString() + "}");
      }
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method createFriend :"
          + exception.getMessage());
    }
    if (existingFriends != "yes") {
      logger.error(existingFriends);
      throw new NonexistentException(existingFriends);
    }
    if (existingFriendRelationship) {
      logger.error("The friend relationship between " + friend.getEmailAddressUser1() + " and "
          + friend.getEmailAddressUser2() + " already exist.");
      throw new IllegalArgumentException("The friend relationship between " + friend.getEmailAddressUser1() + " and "
          + friend.getEmailAddressUser2() + " already exist.");
    }
    return newFriend;
  }

}
