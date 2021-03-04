package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TransactionServiceTest {

  @Autowired
  private TransactionService transactionService;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  private Transaction friendTransaction;
  private UserAccount userAccount;
  private UserAccount userAccount2;

  @BeforeEach
  private void setUp() {
    friendTransaction = new Transaction();
    userAccount = new UserAccount();
    userAccount2 = new UserAccount();
  }

  /**
   * test to save an user account.
   * 
   */
  @Test
  public void testMakeFriendTransaction() {
    friendTransaction.setEmailAddress_emitter("adrien@mail.fr");
    friendTransaction.setAmount(100);
    friendTransaction.setEmailAddress_receiver("marie@mail.fr");
    userAccount.setEmailAddress("adrien@mail.fr");
    userAccount.setAmount(500);
    userAccount2.setEmailAddress("marie@mail.fr");
    userAccount2.setAmount(0);

    when(userAccountRepositoryMock.findByEmailAddress("adrien@mail.fr"))
        .thenReturn(userAccount);
    when(userAccountRepositoryMock.save(userAccount)).thenReturn(userAccount);
    when(userAccountRepositoryMock.findByEmailAddress("marie@mail.fr"))
        .thenReturn(userAccount2);
    when(userAccountRepositoryMock.save(userAccount2)).thenReturn(userAccount2);

    transactionService.makeFriendTransaction(friendTransaction);
    double final_emitter_account = 500 - (100 + 100 * Tax.TAX100 / 100);
    assertThat(userAccount.getAmount()).isEqualTo(final_emitter_account);
    assertThat(userAccount2.getAmount()).isEqualTo(100);
  }


}
