package com.PayMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

  private static final Logger logger = LogManager.getLogger(Transaction.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  private String emailAddress_emitter;

  private Date date;

  private String description;

  private double amount;

  private String emailAddress_receiver;

  public String iban;

}
