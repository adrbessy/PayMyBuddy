package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friendtransaction")
public class FriendTransaction extends Transaction {

  public String emailAddress;

}
