package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.Friend;
import com.PayMyBuddy.service.FriendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

  private Friend friend;

  @Test
  public void testGetFriends() throws Exception {
    mockMvc.perform(get("/friends")).andExpect(status().isOk());
  }

  @Test
  public void testCreateUserAccount() throws Exception {
    friend = new Friend();
    friend.setEmailAddress_user1("someone@mail.fr");
    friend.setEmailAddress_user2("someoneElse@mail.fr");

    when(friendService.saveFriend(friend)).thenReturn(friend);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friend")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friend));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

}
