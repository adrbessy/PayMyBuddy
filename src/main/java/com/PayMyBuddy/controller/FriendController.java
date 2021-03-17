package com.PayMyBuddy.controller;

import com.PayMyBuddy.exceptions.IsForbiddenException;
import com.PayMyBuddy.exceptions.NonexistentException;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccountDto;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.UserAccountService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @Autowired
  private UserAccountService userAccountService;

  /**
   * Read - Get all friend relationships
   * 
   * @return - An Iterable object of friend relationships full filled
   */
  @GetMapping("/friends")
  public List<Friend> getFriends() {
    List<Friend> friendsList = new ArrayList<>();
    try {
      logger.info("GET request with the endpoint 'friends'");
      friendsList = (List<Friend>) friendService.getFriendRelationships();
      logger.info(
          "response following the GET on the endpoint 'friends'.");
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method getFriends :"
          + exception.getMessage());
    }
    return friendsList;
  }

  /**
   * Read - Get friend relationships of one user
   * 
   * @param emailAddress The email address of the user
   * @return - A List of his friend relationships
   */
  @GetMapping("/myFriends")
  public List<UserAccountDto> getMyFriends(@RequestParam String emailAddress) {
    List<UserAccountDto> userAccountDtoList = new ArrayList<>();
    try {
      logger.info("GET request with the endpoint 'myFriends' with the given email " + emailAddress);
      userAccountDtoList = friendService.getFriendsOfOneUser(emailAddress);
      logger.info(
          "response following the GET on the endpoint 'myFriends'.");
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method getMyFriends :"
          + exception.getMessage());
    }
    return userAccountDtoList;
  }

  /**
   * Delete - Delete a friend relationship of one user
   * 
   * @param emailAddress The email address of the user
   * @param emailAddress The email address of the friend to delete
   * @return - The deleted friend relationship
   */
  @DeleteMapping("/myFriend")
  public UserAccountDto deleteMyFriend(@RequestParam String emailAddress,
      @RequestParam String emailAddressToDelete) {
    UserAccountDto userAccountDto = null;
    boolean existingFriendRelationship = false;
    try {
      logger.info("DELETE request with the endpoint 'myFriend' with the given email " + emailAddress
          + "and the email fo the friend to delete :" + emailAddressToDelete);
      existingFriendRelationship = friendService.friendRelationshipExist(emailAddress, emailAddressToDelete);
      if (existingFriendRelationship) {
        userAccountDto = friendService.deleteFriendOfOneUser(emailAddress, emailAddressToDelete);
        logger.info(
            "response following the DELETE on the endpoint 'myFriend'.");
      }
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method deleteMyFriend :"
          + exception.getMessage());
    }
    if (!existingFriendRelationship) {
      logger.error("The friend relationship with the email addresses " + emailAddress + " and " + emailAddressToDelete
          + " don't exist.");
      throw new NonexistentException(
          "The friend relationship with the email addresses " + emailAddress + " and " + emailAddressToDelete
              + " don't exist.");
    }
    return userAccountDto;
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
    String existingUsers = null;
    boolean existingFriendRelationship = false;
    try {
      logger.info("Post request with the endpoint 'friend'");
      existingUsers = userAccountService.usersExist(friend.getEmailAddressUser1(), friend.getEmailAddressUser2());
      if (existingUsers == "yes") {
        existingFriendRelationship = friendService.friendRelationshipExist(friend.getEmailAddressUser1(),
            friend.getEmailAddressUser2());
        if (existingFriendRelationship == false) {
          newFriend = friendService.saveFriend(friend);
          logger.info(
              "response following the Post on the endpoint 'friend' with the given friend : {"
                  + friend.toString() + "}");
        }
      }
    } catch (Exception exception) {
      logger.error("Error in the FriendController in the method createFriend :"
          + exception.getMessage());
    }
    if (existingUsers != "yes") {
      logger.error(existingUsers);
      throw new NonexistentException(existingUsers);
    }
    if (existingFriendRelationship) {
      logger.error("The friend relationship between " + friend.getEmailAddressUser1() + " and "
          + friend.getEmailAddressUser2() + " already exist.");
      throw new IsForbiddenException("The friend relationship between " + friend.getEmailAddressUser1() + " and "
          + friend.getEmailAddressUser2() + " already exist.");
    }
    return newFriend;
  }

}
