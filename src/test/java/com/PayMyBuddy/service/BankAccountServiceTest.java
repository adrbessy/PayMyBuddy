package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
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

    List<BankAccount> result = bankAccountService.getBankAccounts();
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

  /**
   * test to get my bank accounts.
   * 
   */
  @Test
  public void testGetMyBankAccounts() {
    String emailAddress = "adrien@mail.fr";
    bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList<>();
    bankAccountList.add(bankAccount);

    when(bankAccountRepositoryMock.findByEmailAddress(emailAddress)).thenReturn(bankAccountList);

    List<BankAccount> result = bankAccountService.getMyBankAccounts(emailAddress);
    assertThat(result).isEqualTo(bankAccountList);
  }

  /**
   * test to delete a bank account.
   * 
   */
  @Test
  public void testDeleteBankAccount() {
    String emailAddress = "adrien@mail.fr";
    String iban = "FR13465165651246";
    bankAccount = new BankAccount();
    List<BankAccount> bankAccountList = new ArrayList<>();
    bankAccountList.add(bankAccount);

    when(bankAccountRepositoryMock.findByEmailAddressAndIban(emailAddress, iban)).thenReturn(bankAccount);
    doNothing().when(bankAccountRepositoryMock).deleteByEmailAddressAndIban(emailAddress, iban);

    BankAccount result = bankAccountService.deleteMyBankAccount(emailAddress, iban);
    assertThat(result).isEqualTo(bankAccount);
  }

  /**
   * test to check if the bank account exists.
   * 
   */
  @Test
  public void testBankAccountEmailAddressIbanExist() {
    String emailAddress = "adrien@mail.fr";
    String iban = "FR13465165651246";

    when(bankAccountRepositoryMock.existsByEmailAddressAndIban(
        emailAddress,
        iban)).thenReturn(true);

    boolean result = bankAccountService.bankAccountEmailAddressIbanExist(emailAddress, iban);
    assertThat(result).isEqualTo(true);
  }

}
