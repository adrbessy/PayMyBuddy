package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.model.UserAccountDto;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FriendController.class)
public class FriendControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FriendService friendService;

  @MockBean
  private UserAccountService userAccountServiceMock;

  private Friend friend;

  @Test
  public void testGetFriends() throws Exception {
    mockMvc.perform(get("/friends")).andExpect(status().isOk());
  }

  @Test
  public void testGetMyFriends() throws Exception {
    mockMvc.perform(get("/myFriends?emailAddress=adrien@mail.fr")).andExpect(status().isOk());
  }

  @Test
  public void testCreateUserAccount() throws Exception {
    friend = new Friend();
    friend.setEmailAddressUser1("someone@mail.fr");
    friend.setEmailAddressUser2("someoneElse@mail.fr");

    when(userAccountServiceMock.usersExist(friend.getEmailAddressUser1(), friend.getEmailAddressUser2()))
        .thenReturn("yes");
    when(friendService.friendRelationshipExist(friend.getEmailAddressUser1(),
        friend.getEmailAddressUser2())).thenReturn(false);
    when(friendService.saveFriend(friend)).thenReturn(friend);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friend")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friend));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testCreateUserAccountIfUsersDontExist() throws Exception {
    friend = new Friend();
    friend.setEmailAddressUser1("someone@mail.fr");
    friend.setEmailAddressUser2("someoneElse@mail.fr");

    when(userAccountServiceMock.usersExist(friend.getEmailAddressUser1(), friend.getEmailAddressUser2()))
        .thenReturn("no");

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friend")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friend));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateUserAccountIfRelationshipsAlreadyExist() throws Exception {
    friend = new Friend();
    friend.setEmailAddressUser1("someone@mail.fr");
    friend.setEmailAddressUser2("someoneElse@mail.fr");

    when(userAccountServiceMock.usersExist(friend.getEmailAddressUser1(), friend.getEmailAddressUser2()))
        .thenReturn("yes");
    when(friendService.friendRelationshipExist(friend.getEmailAddressUser1(),
        friend.getEmailAddressUser2())).thenReturn(true);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friend")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friend));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testDeletefriendRealationship() throws Exception {
    String emailAddress = "jean@mail.fr";
    String emailAddress2 = "helene@mail.fr";
    UserAccountDto userAccountDto = new UserAccountDto("helene@mail.fr", "Hélène", "Bessy");

    when(friendService.friendRelationshipExist(emailAddress, emailAddress2)).thenReturn(true);
    when(friendService.deleteFriendOfOneUser(emailAddress, emailAddress2)).thenReturn(userAccountDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/myFriend?emailAddress=jean@mail.fr&emailAddressToDelete=helene@mail.fr"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testDeleteBankAccountIfFriendRelationshipDoesntExist() throws Exception {
    String emailAddress = "jean@mail.fr";
    String emailAddress2 = "helene@mail.fr";

    when(friendService.friendRelationshipExist(emailAddress, emailAddress2)).thenReturn(false);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/myFriend?emailAddress=jean@mail.fr&emailAddressToDelete=helene@mail.fr"))
        .andExpect(status().isNotFound());
  }

}
