package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserAccountServiceTest {

  @Autowired
  private UserAccountService userAccountService;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  private UserAccount userAccount;

  @BeforeEach
  private void setUp() {
    userAccount = new UserAccount();
  }

  /**
   * test to save an user account.
   * 
   */
  @Test
  public void testSaveUserAccount() {
    when(userAccountRepositoryMock.save(userAccount)).thenReturn(userAccount);

    UserAccount result = userAccountService.saveUserAccount(userAccount);
    assertThat(result).isEqualTo(userAccount);
  }

  /**
   * test to get all user accounts.
   * 
   */
  @Test
  public void testGetUserAccounts() {

    List<UserAccount> userAccountList = Arrays.asList(userAccount);
    Iterable<UserAccount> it = userAccountList;
    when(userAccountRepositoryMock.findAll()).thenReturn(it);

    Iterable<UserAccount> result = userAccountService.getUserAccounts();
    assertThat(result).isEqualTo(it);
  }

}
