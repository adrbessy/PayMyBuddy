package com.PayMyBuddy.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.PayMyBuddy.model.UserAccount;
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

  private UserAccount userAccount;

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
  @WithMockUser(roles = "ADMIN")
  public void testAdmin() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isOk()).andExpect(view().name("admin"));
  }


}
