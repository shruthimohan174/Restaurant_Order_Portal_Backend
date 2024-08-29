package com.users.dto.outdto;

import com.users.entities.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserOutDtoTest {
  private UserOutDto userResponse;

  @BeforeEach
  void setUp() {
    userResponse = new UserOutDto();
    userResponse.setId(1);
    userResponse.setFirstName("Shruthi");
    userResponse.setLastName("Mohan");
    userResponse.setEmail("shruthimohan1708@gmail.com");
    userResponse.setPhoneNumber("8434972888");
    userResponse.setUserRole(UserRole.CUSTOMER);
    userResponse.setWalletBalance(BigDecimal.valueOf(1000.00));
  }

  @Test
  void testGettersAndSetters() {
    assertEquals(1, userResponse.getId());
    assertEquals("Shruthi", userResponse.getFirstName());
    assertEquals("Mohan", userResponse.getLastName());
    assertEquals("shruthimohan1708@gmail.com", userResponse.getEmail());
    assertEquals("8434972888", userResponse.getPhoneNumber());
    assertEquals(UserRole.CUSTOMER, userResponse.getUserRole());
    assertEquals(BigDecimal.valueOf(1000.00), userResponse.getWalletBalance());
  }

  @Test
  void testToString() {
    String expected =
      "UserOutDto(id=1, firstName=Shruthi, lastName=Mohan, email=shruthimohan1708@gmail.com, phoneNumber=8434972888, userRole=CUSTOMER, walletBalance=1000.0)";
    assertEquals(expected, userResponse.toString());
  }

  @Test
  void testEquals() {
    UserOutDto anotherUserResponse = new UserOutDto();
    anotherUserResponse.setId(1);
    anotherUserResponse.setFirstName("Shruthi");
    anotherUserResponse.setLastName("Mohan");
    anotherUserResponse.setEmail("shruthimohan1708@gmail.com");
    anotherUserResponse.setPhoneNumber("8434972888");
    anotherUserResponse.setUserRole(UserRole.CUSTOMER);
    anotherUserResponse.setWalletBalance(BigDecimal.valueOf(1000.00));

    assertEquals(userResponse, anotherUserResponse);
  }

  @Test
  void testHashCode() {
    UserOutDto anotherUserResponse = new UserOutDto();
    anotherUserResponse.setId(1);
    anotherUserResponse.setFirstName("Shruthi");
    anotherUserResponse.setLastName("Mohan");
    anotherUserResponse.setEmail("shruthimohan1708@gmail.com");
    anotherUserResponse.setPhoneNumber("8434972888");
    anotherUserResponse.setUserRole(UserRole.CUSTOMER);
    anotherUserResponse.setWalletBalance(BigDecimal.valueOf(1000.00));

    assertEquals(userResponse.hashCode(), anotherUserResponse.hashCode());
  }
}
