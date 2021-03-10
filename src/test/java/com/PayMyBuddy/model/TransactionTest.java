package com.PayMyBuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SpringBootTest()
public class TransactionTest {

  private Transaction transaction;

  @BeforeEach
  private void setUp() {
    transaction = new Transaction();
  }

  @Test
  public void simpleEqualsTransaction() {
    EqualsVerifier.forClass(Transaction.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
  }

}
