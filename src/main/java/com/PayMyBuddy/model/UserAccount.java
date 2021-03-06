package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

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
