package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.PayMyBuddy.model.BankAccount;
import com.PayMyBuddy.repository.BankAccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
public class BankAccountServiceTest {

  @Autowired
  private BankAccountService bankAccountService;

  @MockBean
  private BankAccountRepository bankAccountRepositoryMock;

  private BankAccount bankAccount;

  /**
   * test to know if a bank account exists.
   * 
   */
  @Test
  public void testBankAccountExist() {
    String emailAddressReceiver = "adrien@mail.fr";
    Long idBankAccount = (long) 2;

    when(bankAccountRepositoryMock.existsByEmailAddressAndId(
        emailAddressReceiver,
        idBankAccount)).thenReturn(true);

    boolean result = bankAccountService.bankAccountExist(emailAddressReceiver,
        idBankAccount);
    assertTrue(result);
  }

  /**
   * test to get all the bank accounts.
   * 
   */
  @Test
  public void testGetBankAccounts() {
    bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList<>();
    bankAccountList.add(bankAccount);

    when(bankAccountRepositoryMock.findAll()).thenReturn(bankAccountList);

    List<BankAccount> result = (List<BankAccount>) bankAccountService.getBankAccounts();
    assertThat(result).isEqualTo(bankAccountList);
  }

  /**
   * test to save a bank account
   * 
   */
  @Test
  public void testSaveBankAccount() {
    bankAccount = new BankAccount();

    when(bankAccountRepositoryMock.save(bankAccount)).thenReturn(bankAccount);

    BankAccount result = bankAccountService.saveBankAccount(bankAccount);
    assertThat(result).isEqualTo(bankAccount);
  }

}
