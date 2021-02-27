package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.repository.FriendRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FriendServiceTest {

  @Autowired
  private FriendService friendService;

  @MockBean
  private FriendRepository friendRepositoryMock;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  private Friend friend;

  @BeforeEach
  private void setUp() {
    friend = new Friend();
  }

  /**
   * test to save a friend relationship.
   * 
   */
  @Test
  public void testSaveFriend() {
    when(friendRepositoryMock.save(friend)).thenReturn(friend);

    Friend result = friendService.saveFriend(friend);
    assertThat(result).isEqualTo(friend);
  }

  /**
   * test to get all friend relationships.
   * 
   */
  @Test
  public void testGetAllFriends() {

    List<Friend> friendList = Arrays.asList(friend);
    Iterable<Friend> it = friendList;
    when(friendRepositoryMock.findAll()).thenReturn(it);

    Iterable<Friend> result = friendService.getFriends();
    assertThat(result).isEqualTo(it);
  }

  /**
   * test to get all friend relationships.
   * 
   */
  @Test
  public void testFriendsExist() {
    when(userAccountRepositoryMock.existsByEmailAddress(anyString())).thenReturn(true);
    String result = friendService.friendsExist("abcde@mail.fr", "wxyz@mail.fr");
    assertThat(result).isEqualTo("yes");
  }

  @Test
  public void testFriendsDONTexist() {
    when(userAccountRepositoryMock.existsByEmailAddress(anyString())).thenReturn(false);
    String result = friendService.friendsExist("abcde@mail.fr", "wxyz@mail.fr");
    assertThat(result).isEqualTo("abcde@mail.fr and wxyz@mail.fr don't exist");
  }

  @Test
  public void testFriendsSecondDoesntExist() {
    when(userAccountRepositoryMock.existsByEmailAddress("abcde@mail.fr")).thenReturn(true);
    when(userAccountRepositoryMock.existsByEmailAddress("wxyz@mail.fr")).thenReturn(false);
    String result = friendService.friendsExist("abcde@mail.fr", "wxyz@mail.fr");
    assertThat(result).isEqualTo("wxyz@mail.fr doesn't exist");
  }

  @Test
  public void testFriendsFirstDoesntExist() {
    when(userAccountRepositoryMock.existsByEmailAddress("abcde@mail.fr")).thenReturn(false);
    when(userAccountRepositoryMock.existsByEmailAddress("wxyz@mail.fr")).thenReturn(true);
    String result = friendService.friendsExist("abcde@mail.fr", "wxyz@mail.fr");
    assertThat(result).isEqualTo("abcde@mail.fr doesn't exist");
  }

}
