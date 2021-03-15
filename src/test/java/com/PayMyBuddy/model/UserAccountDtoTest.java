package com.PayMyBuddy.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest()
public class UserAccountDtoTest {

  private UserAccountDto userAccountDto;
  String emailAddress = "adrien@mail.fr";
  String firstName = "Adrien";
  String name = "Bessy";


  @BeforeEach
  private void setUp() {
    userAccountDto = new UserAccountDto(emailAddress, firstName, name);
  }

  @Test
  public void simpleEqualsPersonInfoDTO() {
    EqualsVerifier.simple().forClass(UserAccountDto.class).verify();
  }

  @Test
  public void userAccountDtoConstructor() {
    Assert.assertEquals(emailAddress, userAccountDto.getEmailAddress());
    Assert.assertEquals(firstName, userAccountDto.getFirstName());
    Assert.assertEquals(name, userAccountDto.getName());
  }

  @Test
  public void testSetEmailAddress() throws Exception {
    userAccountDto.setEmailAddress("marie@mail.fr");
    assertThat(userAccountDto.getEmailAddress()).isEqualTo("marie@mail.fr");
  }

  @Test
  public void testSetFirstName() throws Exception {
    userAccountDto.setFirstName("Adrien");
    assertThat(userAccountDto.getFirstName()).isEqualTo("Adrien");
  }

  @Test
  public void testSetname() throws Exception {
    userAccountDto.setName("Robert");
    assertThat(userAccountDto.getName()).isEqualTo("Robert");
  }

}
