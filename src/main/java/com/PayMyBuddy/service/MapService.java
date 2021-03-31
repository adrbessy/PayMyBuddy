package com.PayMyBuddy.service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.TransactionDto;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MapService {

  private static final Logger logger = LogManager.getLogger(MapService.class);

  /**
   * Get a list of users with the following attributes: FirstName, LastName, email
   * address
   * 
   * @param userAccountList A list of users
   * @return A List of users
   */
  public List<UserAccountDto> convertToUserAccountDtoList(List<UserAccount> userAccountList) {
    logger.debug("in the method convertToUserAccountDtoList in the class MapService");
    List<UserAccountDto> userAccountDtoList = new ArrayList<>();
    try {
      userAccountList.forEach(personIterator -> {
        UserAccountDto userAccountDto = new UserAccountDto(personIterator.getEmailAddress(),
            personIterator.getFirstName(),
            personIterator.getName());
        userAccountDtoList.add(userAccountDto);
      });
    } catch (Exception exception) {
      logger.error("Error in the method convertToUserAccountDtoList :" + exception.getMessage());
    }
    return userAccountDtoList;
  }

  /**
   * Get a list of transactions with the following attributes: connection,
   * description, amount
   * 
   * @param emailAddress    An email address
   * @param transactionList A list of the transactions
   * @return A List of users
   */
  public List<TransactionDto> convertToTransactionDtoList(String emailAddress, List<Transaction> transactionList) {
    logger.debug("in the method convertToTransactionDtoList in the class MapService");
    List<TransactionDto> transactionDtoList = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      transactionList.forEach(transactionIterator -> {
        String description = transactionIterator.getDescription();
        if (description == null) {
          description = "";
        }
        String myDate = sdf.format(transactionIterator.getMyDate());
        if (emailAddress.equals(transactionIterator.getEmailAddressEmitter())
            && transactionIterator.idBankAccount == null) {
          TransactionDto transactionDto = new TransactionDto(transactionIterator.getEmailAddressReceiver(),
              "- EMITTED TRANSACTION - " + description,
              "-" + transactionIterator.getAmount(), myDate);
          transactionDtoList.add(transactionDto);
        }
        if (emailAddress.equals(transactionIterator.getEmailAddressReceiver())
            && transactionIterator.idBankAccount == null) {
          TransactionDto transactionDto = new TransactionDto(transactionIterator.getEmailAddressEmitter(),
              "- RECEIVED TRANSACTION - " + description,
              "+" + transactionIterator.getAmount(), myDate);
          transactionDtoList.add(transactionDto);
        }
        if (emailAddress.equals(transactionIterator.getEmailAddressEmitter())
            && transactionIterator.idBankAccount != null) {
          TransactionDto transactionDto = new TransactionDto(
              "Deposit on my bank account number : " + transactionIterator.getIdBankAccount(),
              "- EMITTED TRANSACTION - " + description,
              "-" + transactionIterator.getAmount(), myDate);
          transactionDtoList.add(transactionDto);
        }
        if (emailAddress.equals(transactionIterator.getEmailAddressReceiver())
            && transactionIterator.idBankAccount != null) {
          TransactionDto transactionDto = new TransactionDto("Money deposit",
              "- RECEIVED TRANSACTION - " + description,
              "+" + transactionIterator.getAmount(), myDate);
          transactionDtoList.add(transactionDto);
        }
      });
    } catch (Exception exception) {
      logger.error("Error in the method convertToUserAccountDtoList :" + exception.getMessage());
    }
    Collections.reverse(transactionDtoList);
    return transactionDtoList;
  }

  /**
   * Get a user with the following attributes: FirstName, LastName, email address
   * 
   * @param userAccount A user account
   * @return A user
   */
  public UserAccountDto convertToUserAccountDto(UserAccount userAccount) {
    logger.debug("in the method convertToUserAccountDto in the class MapService");
    UserAccountDto userAccountDto = null;
    try {
      userAccountDto = new UserAccountDto(userAccount.getEmailAddress(),
          userAccount.getFirstName(),
          userAccount.getName());
    } catch (Exception exception) {
      logger.error("Error in the method convertToUserAccountDto :" + exception.getMessage());
    }
    return userAccountDto;
  }

}
