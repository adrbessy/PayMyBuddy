package com.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeRessourceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHome() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }

  @Test
  @WithMockUser(username = "adrien@mail.fr", password = "pass1", roles = "USER")
  public void testUser() throws Exception {
    mockMvc.perform(get("/user"))
        .andExpect(status().isOk()).andExpect(view().name("user"));
  }

  @Test
  @WithMockUser(username = "springadmin", password = "spring123", roles = "ADMIN")
  public void testAdmin() throws Exception {
    mockMvc.perform(get("/admin"))
        .andExpect(status().isOk()).andExpect(view().name("admin"));
  }


}
