package com.PayMyBuddy.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
public class CustomUserDetailsTest {

  private CustomUserDetails customUserDetails;

  @MockBean
  private UserAccount userAccountMock;

  @BeforeEach
  private void setUp() {
    customUserDetails = new CustomUserDetails(userAccountMock);
  }

  @Test
  public void testGetFullName() throws Exception {
    when(userAccountMock.getFirstName()).thenReturn("Adrien");
    when(userAccountMock.getName()).thenReturn("Bessy");
    assertThat(customUserDetails.getFullName()).isEqualTo("Adrien" + " " + "Bessy");
  }

  @Test
  public void testGetPassword() throws Exception {
    when(userAccountMock.getPassword()).thenReturn("pass1");
    assertThat(customUserDetails.getPassword()).isEqualTo("pass1");
  }

  @Test
  public void testGetUsername() throws Exception {
    when(userAccountMock.getEmailAddress()).thenReturn("adrien@mail.fr");
    assertThat(customUserDetails.getUsername()).isEqualTo("adrien@mail.fr");
  }

  @Test
  public void testGetAuthorities() throws Exception {
    assertThat(customUserDetails.getAuthorities()).isEqualTo(null);
  }

  @Test
  public void testIsAccountNonExpired() throws Exception {
    assertThat(customUserDetails.isAccountNonExpired()).isEqualTo(true);
  }

  @Test
  public void testIsAccountNonLocked() throws Exception {
    assertThat(customUserDetails.isAccountNonLocked()).isEqualTo(true);
  }

  @Test
  public void testIsCredentialsNonExpired() throws Exception {
    assertThat(customUserDetails.isCredentialsNonExpired()).isEqualTo(true);
  }

  @Test
  public void testIsEnabled() throws Exception {
    assertThat(customUserDetails.isEnabled()).isEqualTo(true);
  }

}
