package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.BankAccountRepository;
import com.PayMyBuddy.repository.TransactionRepository;
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

  @Autowired
  private UserAccountService userAccountService;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  @MockBean
  private BankAccountRepository bankAccountRepositoryMock;

  @MockBean
  private TransactionRepository transactionRepositoryMock;

  private Transaction friendTransaction;
  private Transaction moneyDeposit;
  private Transaction transactionToBankAccount;
  private UserAccount userAccount;
  private UserAccount userAccount2;
  private BankAccount bankAccount;

  @BeforeEach
  private void setUp() {
    friendTransaction = new Transaction();
    moneyDeposit = new Transaction();
    transactionToBankAccount = new Transaction();
    userAccount = new UserAccount();
    userAccount2 = new UserAccount();
    bankAccount = new BankAccount();
  }

  /**
   * test to make a friend transaction.
   * 
   */
  @Test
  public void testMakeFriendTransaction() {
    friendTransaction.setEmailAddressEmitter("adrien@mail.fr");
    friendTransaction.setAmount(100);
    friendTransaction.setEmailAddressReceiver("marie@mail.fr");
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

  /**
   * test to make a friend transaction.
   * 
   */
  @Test
  public void testMakeMoneyDeposit() {
    moneyDeposit.setAmount(100);
    moneyDeposit.setEmailAddressReceiver("marie@mail.fr");
    userAccount.setEmailAddress("marie@mail.fr");
    userAccount.setAmount(500);

    when(userAccountRepositoryMock.findByEmailAddress(moneyDeposit.getEmailAddressReceiver())).thenReturn(userAccount);
    when(userAccountService.saveUserAccount(userAccount))
        .thenReturn(userAccount);
    when(transactionRepositoryMock.save(moneyDeposit)).thenReturn(moneyDeposit);

    transactionService.makeMoneyDeposit(moneyDeposit);
    assertThat(userAccount.getAmount()).isEqualTo(600);
  }

  @Test
  public void testMakeTransactionToBankAccount() {
    transactionToBankAccount.setAmount(100);
    transactionToBankAccount.setEmailAddressEmitter("marie@mail.fr");
    userAccount.setEmailAddress("marie@mail.fr");
    userAccount.setAmount(500);

    when(userAccountRepositoryMock.findByEmailAddress(transactionToBankAccount.getEmailAddressEmitter()))
        .thenReturn(userAccount);
    when(userAccountService.saveUserAccount(userAccount)).thenReturn(userAccount);

    transactionService.makeTransactionToBankAccount(transactionToBankAccount);
    double final_emitter_account = 500 - (100 + 100 * Tax.TAX100 / 100);
    assertThat(userAccount.getAmount()).isEqualTo(final_emitter_account);
  }


}
