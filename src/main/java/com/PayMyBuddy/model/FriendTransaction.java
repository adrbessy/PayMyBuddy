package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend_transaction")
public class FriendTransaction extends Transaction {

  public String emailAddress;

}
