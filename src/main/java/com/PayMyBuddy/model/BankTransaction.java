package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "banktransaction")
public class BankTransaction extends Transaction {

  public String iban;

}
