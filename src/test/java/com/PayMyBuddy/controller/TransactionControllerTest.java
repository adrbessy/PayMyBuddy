package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.BankAccountService;
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
  private BankAccountService bankAccountService;

  @MockBean
  private FriendService friendService;

  @MockBean
  private TransactionService transactionService;

  private Transaction friendTransaction;
  private Transaction moneyDeposit;
  private Transaction transactionToBankAccount;

  @Test
  public void testGetTransactions() throws Exception {
    mockMvc.perform(get("/transactions")).andExpect(status().isOk());
  }

  @Test
  public void testGetMyTransactions() throws Exception {
    mockMvc.perform(get("/myTransactions?emailAddress=adrien@mail.fr")).andExpect(status().isOk());
  }

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

  @Test
  public void testCreateMoneyDeposit() throws Exception {
    moneyDeposit = new Transaction();

    when(userAccountService.userAccountEmailExist(moneyDeposit.getEmailAddressReceiver())).thenReturn(true);
    when(transactionService.makeMoneyDeposit(moneyDeposit)).thenReturn(moneyDeposit);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/moneyDeposit")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(moneyDeposit));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testCreateMoneyDepositUserAccountDoesntExist() throws Exception {
    moneyDeposit = new Transaction();

    when(userAccountService.userAccountEmailExist(moneyDeposit.getEmailAddressReceiver())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/moneyDeposit")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(moneyDeposit));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateTransactionToBankAccount() throws Exception {
    transactionToBankAccount = new Transaction();
    transactionToBankAccount.setIdBankAccount((long) 1);
    transactionToBankAccount.setEmailAddressEmitter("adrien@mail.fr");

    when(userAccountService.userAccountEmailExist(transactionToBankAccount.getEmailAddressEmitter())).thenReturn(true);
    when(bankAccountService.bankAccountExist(transactionToBankAccount.getEmailAddressEmitter(),
        transactionToBankAccount.getIdBankAccount())).thenReturn(true);
    when(userAccountService.checkEnoughMoney(transactionToBankAccount.getEmailAddressEmitter(),
        transactionToBankAccount.getAmount())).thenReturn(true);
    when(transactionService.makeTransactionToBankAccount(transactionToBankAccount))
        .thenReturn(transactionToBankAccount);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transactionToBankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(transactionToBankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testCreateTransactionToBankAccountEmailDoesntExist() throws Exception {
    transactionToBankAccount = new Transaction();
    transactionToBankAccount.setIdBankAccount((long) 1);
    transactionToBankAccount.setEmailAddressEmitter("adrien@mail.fr");

    when(userAccountService.userAccountEmailExist(transactionToBankAccount.getEmailAddressEmitter())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transactionToBankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(transactionToBankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateTransactionToBankAccountBankAccountDoesntExist() throws Exception {
    transactionToBankAccount = new Transaction();
    transactionToBankAccount.setIdBankAccount((long) 1);
    transactionToBankAccount.setEmailAddressEmitter("adrien@mail.fr");

    when(userAccountService.userAccountEmailExist(transactionToBankAccount.getEmailAddressEmitter())).thenReturn(true);
    when(bankAccountService.bankAccountExist(transactionToBankAccount.getEmailAddressEmitter(),
        transactionToBankAccount.getIdBankAccount())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transactionToBankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(transactionToBankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateTransactionToBankAccountNotEnoughMoney() throws Exception {
    transactionToBankAccount = new Transaction();
    transactionToBankAccount.setIdBankAccount((long) 1);
    transactionToBankAccount.setEmailAddressEmitter("adrien@mail.fr");

    when(userAccountService.userAccountEmailExist(transactionToBankAccount.getEmailAddressEmitter())).thenReturn(true);
    when(bankAccountService.bankAccountExist(transactionToBankAccount.getEmailAddressEmitter(),
        transactionToBankAccount.getIdBankAccount())).thenReturn(true);
    when(userAccountService.checkEnoughMoney(transactionToBankAccount.getEmailAddressEmitter(),
        transactionToBankAccount.getAmount())).thenReturn(false);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/transactionToBankAccount")
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(new ObjectMapper().writeValueAsString(transactionToBankAccount));
    this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

}
