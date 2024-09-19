package com.users.dto;

import com.users.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for {@link UserOutDto}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link UserOutDto} class, including getters, setters, {@code toString}
 * method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class UserOutDtoTest {

  /**
   * Sets up the test environment by initializing a {@link UserOutDto}
   * object with predefined values before each test.
   */
  private UserOutDto userOutDto;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    userOutDto = new UserOutDto();
    userOutDto.setId(1);
    userOutDto.setFirstName("First");
    userOutDto.setLastName("Last");
    userOutDto.setEmail("email");
    userOutDto.setPhoneNumber("1234567890");
    userOutDto.setUserRole(UserRole.CUSTOMER);
    userOutDto.setWalletBalance(BigDecimal.valueOf(500.00));
  }

  /**
   * Tests the getters and setters of the {@link UserOutDto} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, userOutDto.getId());
    assertEquals("First", userOutDto.getFirstName());
    assertEquals("Last", userOutDto.getLastName());
    assertEquals("email", userOutDto.getEmail());
    assertEquals("1234567890", userOutDto.getPhoneNumber());
    assertEquals(UserRole.CUSTOMER, userOutDto.getUserRole());
    assertEquals(BigDecimal.valueOf(500.00), userOutDto.getWalletBalance());
  }

  /**
   * Tests the {@code toString} method of the {@link UserOutDto} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    String expected = "UserOutDto(id=1, firstName=First, lastName=Last, email=email, "
      + "phoneNumber=1234567890, userRole=CUSTOMER, walletBalance=500.0)";
    assertEquals(expected, userOutDto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the
   * {@link UserOutDto} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    UserOutDto userOutDto1 = buildUserOutDto(1, "First", "Last",
      "email", "1234567890", UserRole.CUSTOMER, BigDecimal.valueOf(500.00));

    assertEquals(userOutDto1, userOutDto1);
    assertEquals(userOutDto1.hashCode(), userOutDto1.hashCode());

    UserOutDto userOutDto2 = buildUserOutDto(1, "First", "Last",
      "email", "1234567890", UserRole.CUSTOMER, BigDecimal.valueOf(500.00));
    assertEquals(userOutDto1, userOutDto2);
    assertEquals(userOutDto1.hashCode(), userOutDto2.hashCode());

    userOutDto2.setEmail("email2");
    assertNotEquals(userOutDto1, userOutDto2);
    assertNotEquals(userOutDto1.hashCode(), userOutDto2.hashCode());

    userOutDto1 = new UserOutDto();
    userOutDto2 = new UserOutDto();
    assertEquals(userOutDto1, userOutDto2);
    assertEquals(userOutDto1.hashCode(), userOutDto2.hashCode());
  }

  /**
   * Helper method to create an instance of {@link UserOutDto} with specified values.
   *
   * @param id            the ID to set
   * @param firstName     the first name to set
   * @param lastName      the last name to set
   * @param email         the email to set
   * @param phoneNumber   the phone number to set
   * @param userRole      the user role to set
   * @param walletBalance the wallet balance to set
   * @return a configured {@link UserOutDto} instance
   */
  private UserOutDto buildUserOutDto(final int id, final String firstName, final String lastName, final String email,
                                     final String phoneNumber, final UserRole userRole, final BigDecimal walletBalance) {
    UserOutDto userOutDto = new UserOutDto();
    userOutDto.setId(id);
    userOutDto.setFirstName(firstName);
    userOutDto.setLastName(lastName);
    userOutDto.setEmail(email);
    userOutDto.setPhoneNumber(phoneNumber);
    userOutDto.setUserRole(userRole);
    userOutDto.setWalletBalance(walletBalance);
    return userOutDto;
  }
}
