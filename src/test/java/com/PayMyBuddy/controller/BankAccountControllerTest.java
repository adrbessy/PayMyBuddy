package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.service.BankAccountService;
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

@WebMvcTest(controllers = BankAccountController.class)
public class BankAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BankAccountService bankAccountService;

  private BankAccount bankAccount;

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testGetBankAccounts() throws Exception {
    mockMvc.perform(get("/bankAccounts")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testCreateBankAccount() throws Exception {
    bankAccount = new BankAccount();
    bankAccount.setEmailAddress("adrien@mail.fr");
    bankAccount.setIban("abcde");

    when(bankAccountService.saveBankAccount(bankAccount)).thenReturn(bankAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/bankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(bankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testCreateBankAccountIfNoIban() throws Exception {
    bankAccount = new BankAccount();
    bankAccount.setEmailAddress("adrien@mail.fr");

    when(bankAccountService.saveBankAccount(bankAccount)).thenReturn(bankAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/bankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(bankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

}
