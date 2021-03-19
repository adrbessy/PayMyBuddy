package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.constants.Tax;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.BankAccountRepository;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
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
  private MapService mapServiceMock;

  @MockBean
  private UserAccountRepository userAccountRepositoryMock;

  @MockBean
  private BankAccountRepository bankAccountRepositoryMock;

  @MockBean
  private TransactionRepository transactionRepositoryMock;

  private Transaction friendTransaction;
  private Transaction moneyDeposit;
  private Transaction transactionToBankAccount;
  private Transaction transaction;
  private Transaction transaction2;
  private UserAccount userAccount;
  private UserAccount userAccount2;

  @BeforeEach
  private void setUp() {
    friendTransaction = new Transaction();
    moneyDeposit = new Transaction();
    transactionToBankAccount = new Transaction();
    transaction = new Transaction();
    transaction2 = new Transaction();
    userAccount = new UserAccount();
    userAccount2 = new UserAccount();
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

  @Test
  public void testGetTransactionsOfOneUser() {
    String emailAddress = "adrien@mail.fr";
    transaction.setEmailAddressEmitter(emailAddress);
    transaction.setEmailAddressReceiver("marie@mail.fr");
    List<Transaction> transactionList1 = new ArrayList<>();
    transactionList1.add(transaction);
    transaction2.setEmailAddressReceiver(emailAddress);
    List<Transaction> transactionList21 = new ArrayList<>();
    transactionList21.add(transaction2);
    List<TransactionDto> transactionDtoList = new ArrayList<>();

    when(transactionRepositoryMock.findByEmailAddressEmitter(emailAddress)).thenReturn(transactionList1);
    when(transactionRepositoryMock.findByEmailAddressReceiver(emailAddress)).thenReturn(transactionList21);
    when(mapServiceMock.convertToTransactionDtoList(emailAddress, transactionList1)).thenReturn(transactionDtoList);

    List<TransactionDto> result = transactionService.getTransactionsOfOneUser(emailAddress);
    assertThat(result).isEqualTo(transactionDtoList);
  }

  @Test
  public void testGetTransactions() {
    List<Transaction> transactionList = new ArrayList<>();
    transactionList.add(transaction);
    transactionList.add(transaction2);

    when(transactionRepositoryMock.findAll()).thenReturn(transactionList);

    List<Transaction> result = transactionService.getTransactions();
    assertThat(result).isEqualTo(transactionList);
  }


}
