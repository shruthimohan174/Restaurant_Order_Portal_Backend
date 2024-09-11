package com.orders.dto.outdto;

import com.orders.dto.UserOutDto;
import com.orders.utils.UserRole;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    UserOutDto dto = new UserOutDto();
    dto.setId(1);
    dto.setUserRole(UserRole.CUSTOMER);
    dto.setWalletBalance(new BigDecimal("100.00"));

    assertEquals(1, dto.getId());
    assertEquals(UserRole.CUSTOMER, dto.getUserRole());
    assertEquals(new BigDecimal("100.00"), dto.getWalletBalance());
  }

  @Test
  public void testToString() {
    UserOutDto dto = new UserOutDto(1, UserRole.CUSTOMER, new BigDecimal("100.00"));
    String expected = "UserOutDto(id=1, userRole=CUSTOMER, walletBalance=100.00)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    UserOutDto dto1 = new UserOutDto(1, UserRole.CUSTOMER, new BigDecimal("100.00"));
    UserOutDto dto2 = new UserOutDto(1, UserRole.CUSTOMER, new BigDecimal("100.00"));
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    UserOutDto dto1 = new UserOutDto(1, UserRole.CUSTOMER, new BigDecimal("100.00"));
    UserOutDto dto2 = new UserOutDto(1, UserRole.CUSTOMER, new BigDecimal("100.00"));
    UserOutDto dto3 = new UserOutDto(2, UserRole.RESTAURANT_OWNER, new BigDecimal("200.00"));

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
