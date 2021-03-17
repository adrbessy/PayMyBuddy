package com.PayMyBuddy.service;

import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.ArrayList;
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
   * @param userList A list of users
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
   * Get a user with the following attributes: FirstName, LastName, email address
   * 
   * @param user A user
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
