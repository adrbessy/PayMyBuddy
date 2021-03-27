package com.PayMyBuddy.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class TransactionDtoTest {

  @Test
  public void simpleEqualsTransactionDto() {
    EqualsVerifier.simple().forClass(TransactionDto.class).verify();
  }

}
