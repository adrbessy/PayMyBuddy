package com.PayMyBuddy.service;

import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.repository.UserAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

  private static final Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Override
  public UserAccount saveUserAccount(UserAccount userAccount) {
    logger.debug("in the method saveUserAccount in the class UserAccountServiceImpl");
    UserAccount savedUserAccount = null;
    try {
      savedUserAccount = userAccountRepository.save(userAccount);
    } catch (Exception exception) {
      logger.error("Error when we try to save a user account :" + exception.getMessage());
    }
    return savedUserAccount;
  }

}
