package com.orders.dto;

import com.orders.utils.UserRole;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link UserOutDto}.
 * This class tests the getter and setter methods, {@code toString()} method, and {@code equals()} and {@code hashCode()} methods
 * of the {@link UserOutDto} class.
 */
public class UserOutDtoTest {

  /**
   * Tests the getter and setter methods of {@link UserOutDto}.
   * Verifies that the getters return the expected values after setting them through setters.
   */
  @Test
  public void testGettersAndSetters() {
    UserOutDto dto = new UserOutDto();

    assertNull(dto.getId());
    Integer id = 1;
    dto.setId(id);
    assertEquals(id, dto.getId());

    assertNull(dto.getUserRole());
    UserRole userRole = UserRole.CUSTOMER;
    dto.setUserRole(userRole);
    assertEquals(userRole, dto.getUserRole());

    assertNull(dto.getWalletBalance());
    BigDecimal walletBalance = new BigDecimal("100.00");
    dto.setWalletBalance(walletBalance);
    assertEquals(walletBalance, dto.getWalletBalance());
  }

  /**
   * Tests the {@code toString()} method of {@link UserOutDto}.
   * Verifies that the {@code toString()} method returns the correct string representation of the object.
   */
  @Test
  public void testToString() {
    UserOutDto dto = new UserOutDto();

    Integer id = 1;
    dto.setId(id);

    UserRole userRole = UserRole.CUSTOMER;
    dto.setUserRole(userRole);

    BigDecimal walletBalance = new BigDecimal("100.00");
    dto.setWalletBalance(walletBalance);

    assertEquals("UserOutDto(id=1, userRole=CUSTOMER, walletBalance=100.00)", dto.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of {@link UserOutDto}.
   * Verifies that the {@code equals()} method returns {@code true} for equal objects and {@code false} for non-equal objects,
   * and that {@code hashCode()} returns consistent values for equal objects.
   */
  @Test
  public void testEqualsAndHashcode() {
    Integer id = 1;
    UserRole userRole = UserRole.CUSTOMER;
    BigDecimal walletBalance = new BigDecimal("100.00");

    UserOutDto dto1 = new UserOutDto(id, userRole, walletBalance);

    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    UserOutDto dto2 = new UserOutDto(id, userRole, walletBalance);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = new UserOutDto(2, UserRole.RESTAURANT_OWNER, new BigDecimal("200.00"));
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new UserOutDto();
    dto2 = new UserOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }
}
