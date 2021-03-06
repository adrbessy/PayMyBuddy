package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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
  public void testGetMyBankAccounts() throws Exception {
    String emailAddress = "isabelle@mail.fr";
    List<BankAccount> bankAccountsList = new ArrayList<>();
    when(bankAccountService.getMyBankAccounts(emailAddress)).thenReturn(bankAccountsList);

    mockMvc.perform(get("/myBankAccounts?emailAddress=isabelle@mail.fr")).andExpect(status().isOk());
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

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testDeleteBankAccount() throws Exception {
    String emailAddress = "isabelle@mail.fr";
    String iban = "FR46541656184548646";
    bankAccount = new BankAccount();

    when(bankAccountService.bankAccountEmailAddressIbanExist(emailAddress, iban)).thenReturn(true);
    when(bankAccountService.deleteMyBankAccount(emailAddress, iban)).thenReturn(bankAccount);

    mockMvc
        .perform(MockMvcRequestBuilders.delete("/myBankAccount?emailAddress=isabelle@mail.fr&iban=FR46541656184548646"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testDeleteBankAccountIfBankAccountDoesntExist() throws Exception {
    String emailAddress = "isabelle@mail.fr";
    String iban = "FR46541656184548646";
    bankAccount = new BankAccount();

    when(bankAccountService.bankAccountEmailAddressIbanExist(emailAddress, iban)).thenReturn(false);

    mockMvc
        .perform(MockMvcRequestBuilders.delete("/myBankAccount?emailAddress=isabelle@mail.fr&iban=FR46541656184548646"))
        .andExpect(status().isNotFound());
  }

}
