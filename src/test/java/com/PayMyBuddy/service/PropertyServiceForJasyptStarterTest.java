package com.PayMyBuddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertyServiceForJasyptStarterTest {

  @Autowired
  private PropertyServiceForJasyptStarter propertyServiceForJasyptStarter;

  @Test
  public void whenDecryptedPasswordNeeded_GetFromService() {
    System.setProperty("jasypt.encryptor.password", "password");

    assertEquals("something", propertyServiceForJasyptStarter.getProperty());

  }

}
