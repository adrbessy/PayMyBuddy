package com.PayMyBuddy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class UserAccountDto {

  private String emailAddress;

  private String firstName;

  private String name;

  public UserAccountDto(String emailAddress, String firstName, String name) {
    this.emailAddress = emailAddress;
    this.firstName = firstName;
    this.name = name;
  }

}
