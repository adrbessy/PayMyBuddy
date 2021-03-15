package com.PayMyBuddy.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class BankAccountTest {

  private BankAccount bankAccount;


  @BeforeEach
  private void setUp() {
    bankAccount = new BankAccount();
  }

  @Test
  public void testSetId() throws Exception {
    Long id = (long) 2;
    bankAccount.setId(id);
    assertThat(bankAccount.getId()).isEqualTo(id);
  }

}
