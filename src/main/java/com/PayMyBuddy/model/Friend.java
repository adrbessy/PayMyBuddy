package com.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "friend")
public class Friend {

  private static final Logger logger = LogManager.getLogger(Friend.class);

  private String emailaddress_user1;

  private String emailaddress_user2;

}
