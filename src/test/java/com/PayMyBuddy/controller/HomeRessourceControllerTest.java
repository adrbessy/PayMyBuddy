package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
  public void testAdmin() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isOk()).andExpect(view().name("admin"));
  }


}
