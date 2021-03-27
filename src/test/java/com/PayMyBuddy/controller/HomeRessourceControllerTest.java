package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeRessourceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAccountController userAccountControllerMock;

  @MockBean
  private BankAccountController bankAccountControllerMock;

  @MockBean
  private FriendController friendControllerMock;

  @MockBean
  private TransactionController transactionControllerMock;

  private UserAccount userAccount;
  private UserAccountDto userAccountDto;
  private TransactionDto transactionDto;

  @Test
  @WithMockUser(roles = "ADMIN")
  public void testHome() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }

  @Test
  @WithMockUser(username = "adrien@mail.fr")
  public void testUser() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");

    when(userAccountControllerMock.getMyUserAccount("adrien@mail.fr"))
        .thenReturn(userAccount);

    mockMvc.perform(get("/user"))
        .andExpect(status().isOk()).andExpect(view().name("user"));
  }

  @Test
  @WithMockUser(username = "adrien@mail.fr")
  public void testProfile() throws Exception {
    List<BankAccount> myBankAccounts = new ArrayList<>();

    when(bankAccountControllerMock.getMyBankAccounts("adrien@mail.fr"))
        .thenReturn(myBankAccounts);

    mockMvc.perform(get("/profile"))
        .andExpect(status().isOk()).andExpect(view().name("profile"));
  }


  @Test
  @WithMockUser(username = "adrien@mail.fr")
  public void testDeleteBankAccount() throws Exception {
    BankAccount bankAccount = new BankAccount();
    List<BankAccount> myBankAccounts = new ArrayList<>();

    when(bankAccountControllerMock.deleteBankAccount("adrien@mail.fr",
        "FR3546565449854")).thenReturn(bankAccount);
    when(bankAccountControllerMock.getMyBankAccounts("adrien@mail.fr"))
        .thenReturn(myBankAccounts);

    mockMvc.perform(get("/deleteBankAccount?emailAddress=adrien@mail.fr&iban=FR3546565449854"))
        .andExpect(status().isOk()).andExpect(view().name("profile"));
  }


  /*
   * @Test
   * 
   * @WithMockUser(username = "adrien@mail.fr") public void testAdminUser() throws
   * Exception { userAccount = new UserAccount();
   * userAccount.setEmailAddress("adrien@mail.fr");
   * userAccount.setPassword("abcde"); userAccount.setFirstName("Adrien");
   * userAccount.setName("Bessy"); List<UserAccount> userAccountList = new
   * ArrayList<>(); userAccountList.add(userAccount); List<TransactionDto>
   * transactionDtoList = new ArrayList<>(); TransactionDto transactionDto = new
   * TransactionDto("connection", "description", "amount", "myDate");
   * transactionDtoList.add(transactionDto);
   * 
   * when(userAccountControllerMock.getMyUserAccount("adrien@mail.fr"))
   * .thenReturn(userAccount); when(userAccountControllerMock.getUserAccounts())
   * .thenReturn(userAccountList);
   * 
   * // mockMvc.perform(get("/admin_user", //
   * transactionDtoList)).andExpect(status().isOk()).andExpect(view().name(
   * "admin_user"));
   * 
   * MockHttpServletRequestBuilder builder =
   * MockMvcRequestBuilders.post("/admin_user").flashAttr("transactionList",
   * transactionDtoList);
   * this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()
   * ).andExpect(view().name("admin_user")); }
   */

  @Test
  @WithMockUser(username = "adrien@mail.fr")
  public void testContact() throws Exception {
    userAccountDto = new UserAccountDto("isabelle@mail.fr", "Isabelle", "Bernardin");
    List<UserAccountDto> friendList = new ArrayList<>();
    friendList.add(userAccountDto);

    when(friendControllerMock.getMyFriends("adrien@mail.fr"))
        .thenReturn(friendList);

    mockMvc.perform(get("/contact"))
        .andExpect(status().isOk()).andExpect(view().name("friend"));
  }

  @Test
  @WithMockUser(username = "adrien@mail.fr")
  public void testTransac() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");
    double amount = 800.0;
    userAccount.setAmount(amount);
    transactionDto = new TransactionDto("isabelle@mail.fr", "for a present", "50", "");
    List<TransactionDto> transactionList = new ArrayList<>();
    transactionList.add(transactionDto);

    when(transactionControllerMock.getMyTransactions("adrien@mail.fr"))
        .thenReturn(transactionList);
    when(userAccountControllerMock.getMyUserAccount("adrien@mail.fr"))
        .thenReturn(userAccount);

    mockMvc.perform(get("/transac"))
        .andExpect(status().isOk()).andExpect(view().name("transaction"));
  }


  @Test
  @WithMockUser(roles = "ADMIN")
  public void testDeposit() throws Exception {
    userAccount = new UserAccount();
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setPassword("abcde");
    userAccount.setFirstName("Adrien");
    userAccount.setName("Bessy");
    List<UserAccount> userAccountList = new ArrayList<>();
    double amount = 800.0;
    userAccount.setAmount(amount);
    transactionDto = new TransactionDto("isabelle@mail.fr", "for a present", "50", "");
    List<TransactionDto> transactionList = new ArrayList<>();
    transactionList.add(transactionDto);
    Transaction transaction = new Transaction();
    transaction.setEmailAddressReceiver("adrien@mail.fr");

    when(transactionControllerMock.createMoneyDeposit(transaction)).thenReturn(transaction);
    when(transactionControllerMock.getMyTransactions("adrien@mail.fr"))
        .thenReturn(transactionList);
    when(userAccountControllerMock.getUserAccounts())
        .thenReturn(userAccountList);
    when(userAccountControllerMock.getMyUserAccount("adrien@mail.fr"))
        .thenReturn(userAccount);

    // mockMvc.perform(post("/deposit")).andExpect(status().isOk());
  }


  @Test
  @WithMockUser(roles = "ADMIN")
  public void testAdmin() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isOk()).andExpect(view().name("admin"));
  }


}
