package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.UserAccount;
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

@WebMvcTest(controllers = UserAccountController.class)
public class UserAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAccountService userAccountService;

  private UserAccount userAccount;

  @Test
  public void testGetUserAccounts() throws Exception {
    mockMvc.perform(get("/userAccounts")).andExpect(status().isOk());
  }

  @Test
  public void testCreateUserAccount() throws Exception {
    userAccount = new UserAccount();
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");

    when(userAccountService.saveUserAccount(userAccount)).thenReturn(userAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/userAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(userAccount));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

}