package com.restaurants.dto.outdto;

import com.restaurants.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserOutDtoTest {

  private UserOutDto userOutDto1;
  private UserOutDto userOutDto2;

  @BeforeEach
  void setUp() {
    userOutDto1 = new UserOutDto();
    userOutDto1.setId(1);
    userOutDto1.setUserRole(UserRole.CUSTOMER);

    userOutDto2 = new UserOutDto();
    userOutDto2.setId(1);
    userOutDto2.setUserRole(UserRole.CUSTOMER);
  }

  @Test
  void testGettersSetters() {
    UserOutDto dto = new UserOutDto();
    dto.setId(2);
    dto.setUserRole(UserRole.CUSTOMER);

    assertEquals(2, dto.getId());
    assertEquals(UserRole.CUSTOMER, dto.getUserRole());
  }

  @Test
  void testToString() {
    String expected = "UserOutDto(id=1, userRole=CUSTOMER)";
    assertEquals(expected, userOutDto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(userOutDto1, userOutDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(userOutDto1.hashCode(), userOutDto2.hashCode());
  }
}
