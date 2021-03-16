package com.PayMyBuddy.service;

import com.PayMyBuddy.repository.UserAccountRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
public class CustomUserDetailsServiceTest {
  /*
   * @Autowired private CustomUserDetailsService customUserDetailsService;
   */
  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  /**
   * test to load user by his username
   * 
   */
  /*
   * @Test public void testBankAccountExist() { String emailAddress =
   * "adrien@mail.fr"; UserAccount userAccount = new UserAccount();
   * 
   * when(userAccountRepositoryMock.findByEmailAddress(emailAddress)).thenReturn(
   * userAccount);
   * 
   * UserDetails result =
   * customUserDetailsService.loadUserByUsername(emailAddress);
   * assertThat(result).isEqualTo(new CustomUserDetails(userAccount)); }
   */
}
