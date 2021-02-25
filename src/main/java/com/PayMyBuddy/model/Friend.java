package com.PayMyBuddy.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

  private static final Logger logger = LogManager.getLogger(Friend.class);

  @Id
  private String emailAddress_user1;

  @Id
  private String emailAddress_user2;

}
