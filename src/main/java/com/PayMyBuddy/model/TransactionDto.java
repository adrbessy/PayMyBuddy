package com.PayMyBuddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

  private String connection;

  private String description;

  private String amount;

  public TransactionDto(String connection, String description, String amount) {
    this.connection = connection;
    this.description = description;
    this.amount = amount;
  }

}
