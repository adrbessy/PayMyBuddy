package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.FriendService;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserAccountService;
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

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAccountService userAccountService;

  @MockBean
  private FriendService friendService;

  @MockBean
  private TransactionService transactionService;

  private Transaction friendTransaction;

  @Test
  public void testCreateFriendTransaction() throws Exception {
    friendTransaction = new Transaction();

    when(friendService.friendRelationshipExist(friendTransaction.getEmailAddressEmitter(),
        friendTransaction.getEmailAddressReceiver())).thenReturn(true);
    when(userAccountService.checkEnoughMoney(friendTransaction.getEmailAddressEmitter(),
        friendTransaction.getAmount())).thenReturn(true);
    when(transactionService.makeFriendTransaction(friendTransaction)).thenReturn(friendTransaction);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friendTransaction")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friendTransaction));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testCreateFriendTransactionRelationshipNotExist() throws Exception {
    friendTransaction = new Transaction();

    when(friendService.friendRelationshipExist(friendTransaction.getEmailAddressEmitter(),
        friendTransaction.getEmailAddressReceiver())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friendTransaction")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friendTransaction));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateFriendTransactionNotEnoughMoney() throws Exception {
    friendTransaction = new Transaction();

    when(friendService.friendRelationshipExist(friendTransaction.getEmailAddressEmitter(),
        friendTransaction.getEmailAddressReceiver())).thenReturn(true);
    when(userAccountService.checkEnoughMoney(friendTransaction.getEmailAddressEmitter(),
        friendTransaction.getAmount())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/friendTransaction")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(friendTransaction));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

}
