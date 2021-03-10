package com.PayMyBuddy.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SpringBootTest()
public class UserAccountTest {

  @Test
  public void simpleEqualsUserAccount() {
    EqualsVerifier.forClass(UserAccount.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
  }

}
