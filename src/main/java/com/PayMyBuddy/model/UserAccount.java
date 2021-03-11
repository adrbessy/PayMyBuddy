package com.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {

  @Id
  @Column(nullable = false, unique = true, length = 50)
  private String emailAddress;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 100)
  private String firstName;

  @Column(nullable = false, length = 100)
  private String name;

  private double amount;

}
