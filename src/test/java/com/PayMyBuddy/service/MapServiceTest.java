package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class MapServiceTest {

  @Autowired
  private MapService mapService;

  @Test
  public void testConvertToUserAccountDtoList() {
    UserAccount userAccount2 = new UserAccount();
    userAccount2.setEmailAddress("marie@mail.fr");
    List<UserAccount> userList = new ArrayList<>();
    userList.add(userAccount2);
    List<UserAccountDto> userAccountDtoList = new ArrayList<>();
    UserAccountDto userAccountDto = new UserAccountDto(userAccount2.getEmailAddress(),
        userAccount2.getFirstName(),
        userAccount2.getName());
    userAccountDtoList.add(userAccountDto);

    List<UserAccountDto> result = mapService.convertToUserAccountDtoList(userList);
    assertThat(result).isEqualTo(userAccountDtoList);
  }

  @Test
  public void testConvertToTransactionDtoList() {
    Transaction transaction = new Transaction();
    List<Transaction> transactionList = new ArrayList<>();
    transaction.setEmailAddressEmitter("marie@mail.fr");
    transaction.setEmailAddressReceiver("adrien@mail.fr");
    transaction.setAmount(50);
    String description = "";
    transactionList.add(transaction);
    TransactionDto transactionDto = new TransactionDto(transaction.getEmailAddressReceiver(),
        "- EMITTED TRANSACTION - " + description,
        "-" + transaction.getAmount());
    List<TransactionDto> transactionDtoList = new ArrayList<>();
    transactionDtoList.add(transactionDto);

    List<TransactionDto> result = mapService.convertToTransactionDtoList("marie@mail.fr", transactionList);
    assertThat(result).isEqualTo(transactionDtoList);
  }

  @Test
  public void testConvertToTransactionDtoList2() {
    Transaction transaction = new Transaction();
    List<Transaction> transactionList = new ArrayList<>();
    transaction.setEmailAddressEmitter("marie@mail.fr");
    transaction.setEmailAddressReceiver("adrien@mail.fr");
    transaction.setAmount(50);
    String description = "";
    transactionList.add(transaction);
    TransactionDto transactionDto = new TransactionDto(transaction.getEmailAddressEmitter(),
        "- RECEIVED TRANSACTION - " + description,
        "+" + transaction.getAmount());
    List<TransactionDto> transactionDtoList = new ArrayList<>();
    transactionDtoList.add(transactionDto);

    List<TransactionDto> result = mapService.convertToTransactionDtoList("adrien@mail.fr", transactionList);
    assertThat(result).isEqualTo(transactionDtoList);
  }

  @Test
  public void testConvertToTransactionDtoList3() {
    Transaction transaction = new Transaction();
    List<Transaction> transactionList = new ArrayList<>();
    transaction.setEmailAddressEmitter("marie@mail.fr");
    transaction.setEmailAddressReceiver(null);
    transaction.setIdBankAccount((long) 1);
    transaction.setAmount(50);
    String description = "";
    transactionList.add(transaction);
    TransactionDto transactionDto = new TransactionDto(
        "Deposit on my bank account number : " + transaction.getIdBankAccount(),
        "- EMITTED TRANSACTION - " + description,
        "-" + transaction.getAmount());
    List<TransactionDto> transactionDtoList = new ArrayList<>();
    transactionDtoList.add(transactionDto);

    List<TransactionDto> result = mapService.convertToTransactionDtoList("marie@mail.fr", transactionList);
    assertThat(result).isEqualTo(transactionDtoList);
  }

  @Test
  public void testConvertToTransactionDtoList4() {
    Transaction transaction = new Transaction();
    List<Transaction> transactionList = new ArrayList<>();
    transaction.setEmailAddressReceiver("marie@mail.fr");
    transaction.setEmailAddressEmitter(null);
    transaction.setIdBankAccount((long) 1);
    transaction.setAmount(50);
    String description = "";
    transactionList.add(transaction);
    TransactionDto transactionDto = new TransactionDto(
        "Money deposit",
        "- RECEIVED TRANSACTION - " + description,
        "+" + transaction.getAmount());
    List<TransactionDto> transactionDtoList = new ArrayList<>();
    transactionDtoList.add(transactionDto);

    List<TransactionDto> result = mapService.convertToTransactionDtoList("marie@mail.fr", transactionList);
    assertThat(result).isEqualTo(transactionDtoList);
  }

  @Test
  public void testConvertToUserAccountDto() {
    UserAccount userAccount2 = new UserAccount();
    userAccount2.setEmailAddress("marie@mail.fr");

    UserAccountDto userAccountDto = new UserAccountDto(userAccount2.getEmailAddress(),
        userAccount2.getFirstName(),
        userAccount2.getName());

    UserAccountDto result = mapService.convertToUserAccountDto(userAccount2);
    assertThat(result).isEqualTo(userAccountDto);
  }

}
