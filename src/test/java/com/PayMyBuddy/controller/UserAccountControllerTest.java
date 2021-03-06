package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAccountService userAccountService;

  private UserAccount userAccount;
  private UserAccount userAccount2;

  @WithMockUser(value = "springuser")
  @Test
  public void testGetUserAccounts() throws Exception {
    mockMvc.perform(get("/userAccounts")).andExpect(status().isOk());
  }

  @WithMockUser(value = "springuser")
  @Test
  public void testCreateUserAccount() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");

    when(userAccountService.saveUserAccount(userAccount)).thenReturn(userAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/userAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(userAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithMockUser(value = "springuser")
  @Test
  public void testCreateUserAccountIfNameIsNull() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName(null);

    when(userAccountService.saveUserAccount(userAccount)).thenReturn(userAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/userAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(userAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @WithMockUser(value = "springuser")
  @Test
  public void testUpdateUserAccount() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");
    userAccount2 = new UserAccount();
    userAccount2.setEmailAddress("adrien@mail.fr");
    userAccount2.setPassword("xyz");
    userAccount2.setFirstName("Isabelle");
    userAccount2.setName("Bernardin");
    userAccount2.setAmount(100);

    when(userAccountService.userAccountEmailExist("adrien@mail.fr")).thenReturn(true);
    when(userAccountService.getUserAccount("adrien@mail.fr")).thenReturn(userAccount);
    when(userAccountService.saveUserAccount(userAccount)).thenReturn(userAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/userAccount/adrien@mail.fr")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(userAccount2));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithMockUser(value = "springuser")
  @Test
  public void testUpdateUserAccountIfUserDoesntExist() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");
    userAccount2 = new UserAccount();
    userAccount2.setEmailAddress("adrien@mail.fr");
    userAccount2.setPassword("xyz");

    when(userAccountService.userAccountEmailExist("adrien@mail.fr")).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/userAccount/adrien@mail.fr")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(userAccount2));

    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
