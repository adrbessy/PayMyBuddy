package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import com.PayMyBuddy.repository.FriendRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FriendServiceTest {

  @Autowired
  private FriendService friendService;

  @MockBean
  private MapService mapServiceMock;

  @MockBean
  private FriendRepository friendRepositoryMock;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  private Friend friendrelationship;
  private Friend friendrelationship2;

  @BeforeEach
  private void setUp() {
    friendrelationship = new Friend();
    friendrelationship2 = new Friend();
  }

  /**
   * test to save a friend relationship.
   * 
   */
  @Test
  public void testSaveFriend() {
    when(friendRepositoryMock.save(friendrelationship)).thenReturn(friendrelationship);

    Friend result = friendService.saveFriend(friendrelationship);
    assertThat(result).isEqualTo(friendrelationship);
  }

  /**
   * test to get all friend relationships.
   * 
   */
  @Test
  public void testGetAllFriends() {
    List<Friend> friendList = Arrays.asList(friendrelationship);
    Iterable<Friend> it = friendList;

    when(friendRepositoryMock.findAll()).thenReturn(it);

    Iterable<Friend> result = friendService.getFriendRelationships();
    assertThat(result).isEqualTo(it);
  }

  @Test
  public void testGetFriendsOfOneUser() {
    String emailAddress = "adrien@mail.fr";
    friendrelationship.setEmailAddressUser1("adrien@mail.fr");
    friendrelationship.setEmailAddressUser2("isabelle@mail.fr");
    friendrelationship2.setEmailAddressUser1("marie@mail.fr");
    friendrelationship2.setEmailAddressUser2("adrien@mail.fr");
    List<Friend> friendList1 = new ArrayList<>();
    friendList1.add(friendrelationship);
    List<Friend> friendList2 = new ArrayList<>();
    friendList2.add(friendrelationship2);
    UserAccount userAccount = new UserAccount();
    userAccount.setEmailAddress("isabelle@mail.fr");
    UserAccount userAccount2 = new UserAccount();
    userAccount.setEmailAddress("marie@mail.fr");
    List<UserAccount> userList = new ArrayList<>();
    userList.add(userAccount);
    userList.add(userAccount2);
    UserAccountDto userAccountDto = new UserAccountDto("isabelle@mail.fr", "Isabelle", "Bernardin");
    UserAccountDto userAccountDto2 = new UserAccountDto("marie@mail.fr", "Marie", "Reigner");
    List<UserAccountDto> userAccountDtoList = new ArrayList<>();
    userAccountDtoList.add(userAccountDto);
    userAccountDtoList.add(userAccountDto2);

    when(friendRepositoryMock.findByEmailAddressUser1(emailAddress)).thenReturn(friendList1);
    when(friendRepositoryMock.findByEmailAddressUser2(emailAddress)).thenReturn(friendList2);
    when(userAccountRepositoryMock.findDistinctByEmailAddress("isabelle@mail.fr")).thenReturn(userAccount);
    when(userAccountRepositoryMock.findDistinctByEmailAddress("marie@mail.fr")).thenReturn(userAccount2);
    when(mapServiceMock.convertToUserAccountDtoList(userList)).thenReturn(userAccountDtoList);

    List<UserAccountDto> result = friendService.getFriendsOfOneUser(emailAddress);
    assertThat(result).isEqualTo(userAccountDtoList);
  }

  @Test
  public void testFriendRelationshipExist() {
    String emailAddress_user1 = "adrien@mail.fr";
    String emailAddress_user2 = "isabelle@mail.fr";

    when(friendRepositoryMock.existsByEmailAddressUser1AndEmailAddressUser2(
        emailAddress_user1,
        emailAddress_user2)).thenReturn(true);
    when(friendRepositoryMock.existsByEmailAddressUser1AndEmailAddressUser2(
        emailAddress_user2,
        emailAddress_user1)).thenReturn(false);

    boolean result = friendService.friendRelationshipExist(emailAddress_user1, emailAddress_user2);
    assertTrue(result);
  }

  @Test
  public void testDeleteFriendRelationships() {
    String emailAddress = "adrien@mail.fr";

    doNothing().when(friendRepositoryMock).deleteFriendByEmailAddressUser1(emailAddress);
    doNothing().when(friendRepositoryMock).deleteFriendByEmailAddressUser2(emailAddress);

    friendService.deleteFriendRelationships(emailAddress);
    verify(friendRepositoryMock,
        Mockito.times(1)).deleteFriendByEmailAddressUser1(emailAddress);
    verify(friendRepositoryMock,
        Mockito.times(1)).deleteFriendByEmailAddressUser2(emailAddress);
  }

}
