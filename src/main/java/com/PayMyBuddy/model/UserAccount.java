package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

  private static final Logger logger = LogManager.getLogger(UserAccount.class);

  @Id
  private String emailAddress;

  private String password;

  private String firstName;

  private String name;

  private double amount;
  /*
   * @ElementCollection private List<String> bankAccount;
   */
}
