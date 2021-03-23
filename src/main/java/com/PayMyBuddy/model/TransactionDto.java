package com.PayMyBuddy.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class TransactionDto {

  private String connection;

  private String description;

  private String amount;

  String myDate;

  public TransactionDto(String connection, String description, String amount, Date myDate) {
    this.connection = connection;
    this.description = description;
    this.amount = amount;
    this.myDate = setDate(myDate);
  }

  public String setDate(Date myDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(myDate);
  }

}
