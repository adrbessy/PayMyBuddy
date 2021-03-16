package com.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.PayMyBuddy.model.UserAccount;
import com.PayMyBuddy.model.UserAccountDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class MapServiceTest {

  @Autowired
  private MapService mapService;

  @Test
  public void testConvertToUserAccountDtoList() {
    UserAccount userAccount2 = new UserAccount();
    userAccount2.setEmailAddress("marie@mail.fr");
    List<UserAccount> userList = new ArrayList<>();
    userList.add(userAccount2);
    List<UserAccountDto> userAccountDtoList = new ArrayList<>();
    UserAccountDto userAccountDto = new UserAccountDto(userAccount2.getEmailAddress(),
        userAccount2.getFirstName(),
        userAccount2.getName());
    userAccountDtoList.add(userAccountDto);

    // when(bankAccountRepositoryMock.save(bankAccount)).thenReturn(bankAccount);

    List<UserAccountDto> result = mapService.convertToUserAccountDtoList(userList);
    assertThat(result).isEqualTo(userAccountDtoList);
  }

}
