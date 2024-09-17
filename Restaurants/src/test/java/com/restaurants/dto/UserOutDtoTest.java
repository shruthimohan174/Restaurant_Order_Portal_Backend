package com.restaurants.dto;

import com.restaurants.utils.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link UserOutDto} class.
 * This class tests the functionality of the UserOutDto data transfer object, including
 * getters and setters, the {@code toString} method, and the {@code equals} and {@code hashCode} methods.
 */
public class UserOutDtoTest {

  /**
   * Builds a {@link UserOutDto} instance with the given parameters.
   *
   * @param id        the ID of the user
   * @param userRole  the role of the user
   * @return a {@link UserOutDto} instance with the specified properties
   */
  private UserOutDto buildUserOutDto(final int id, final UserRole userRole) {
    UserOutDto dto = new UserOutDto();
    dto.setId(id);
    dto.setUserRole(userRole);
    return dto;
  }

  /**
   * Tests the getters and setters of {@link UserOutDto}.
   */
  @Test
  public void testGettersAndSetters() {
    UserOutDto dto = new UserOutDto();

    assertEquals(null, dto.getId());
    assertNull(dto.getUserRole());

    int id = 2;
    dto.setId(id);
    assertEquals(id, dto.getId());

    UserRole userRole = UserRole.RESTAURANT_OWNER;
    dto.setUserRole(userRole);
    assertEquals(userRole, dto.getUserRole());
  }

  /**
   * Tests the {@code toString} method of {@link UserOutDto}.
   */
  @Test
  public void testToString() {
    UserOutDto dto = buildUserOutDto(3, UserRole.CUSTOMER);

    String expected = "UserOutDto(id=3, userRole=CUSTOMER)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of {@link UserOutDto}.
   */
  @Test
  public void testEqualsAndHashCode() {
    UserOutDto dto1 = buildUserOutDto(1, UserRole.CUSTOMER);
    UserOutDto dto2 = buildUserOutDto(1, UserRole.CUSTOMER);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildUserOutDto(2, UserRole.CUSTOMER);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildUserOutDto(1, UserRole.RESTAURANT_OWNER);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new UserOutDto();
    dto2 = new UserOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }
}
