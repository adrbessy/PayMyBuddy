package com.PayMyBuddy.model;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "useraccount")
public class UserAccount {

  private static final Logger logger = LogManager.getLogger(UserAccount.class);

  @Id
  private String emailaddress;

  private String password;

  private String firstName;

  private String name;

  private double amount;

  @ElementCollection
  private List<String> bankAccount;

}
