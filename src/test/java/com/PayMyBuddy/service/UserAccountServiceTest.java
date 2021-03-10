package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.Arrays;
import java.util.List;
import org.jasypt.util.password.BasicPasswordEncryptor;
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

  /**
   * test to check if the user has enough money when he makes a transaction.
   * 
   */
  @Test
  public void testCheckEnoughMoney() {
    String emailAddress_emitter = "adrien@mail.fr";
    double amount = 100;
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setAmount(100.5);

    when(userAccountRepositoryMock.findByEmailAddress("adrien@mail.fr"))
        .thenReturn(userAccount);

    boolean result = userAccountService.checkEnoughMoney(emailAddress_emitter, amount);
    assertTrue(result);
  }

  /**
   * test to check if the user has enough money when he makes a transaction.
   * 
   */
  @Test
  public void testCheckNOTEnoughMoney() {
    String emailAddress_emitter = "adrien@mail.fr";
    double amount = 100;
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setAmount(100.4);

    when(userAccountRepositoryMock.findByEmailAddress("adrien@mail.fr"))
        .thenReturn(userAccount);

    boolean result = userAccountService.checkEnoughMoney(emailAddress_emitter, amount);
    assertFalse(result);
  }

  /**
   * test to check the correspondence between the input password and the encrypted
   * password.
   * 
   */
  @Test
  public void testCheckPassword() {
    String inputPassword = "hell";
    BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    String encryptedPassword = passwordEncryptor.encryptPassword(inputPassword);
    assertTrue(userAccountService.checkPassword(inputPassword, encryptedPassword));
    assertFalse(userAccountService.checkPassword("hello", encryptedPassword));
  }

}
