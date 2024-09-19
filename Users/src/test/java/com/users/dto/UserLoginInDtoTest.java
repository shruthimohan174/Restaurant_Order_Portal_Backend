package com.users.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
/**
 * Unit tests for {@link UserLoginInDto}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link UserLoginInDto} class, including getters, setters,
 * {@code toString} method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class UserLoginInDtoTest {

  /**
   * Sets up the test environment by initializing a {@link UserLoginInDto}
   * object before each test.
   */
  private UserLoginInDto userLoginInDto;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    userLoginInDto = new UserLoginInDto();
  }

  /**
   * Tests the getters and setters of the {@link UserLoginInDto} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    userLoginInDto.setEmail("email");
    userLoginInDto.setPassword("password");

    assertEquals("email", userLoginInDto.getEmail());
    assertEquals("password", userLoginInDto.getPassword());
  }

  /**
   * Tests the {@code toString} method of the {@link UserLoginInDto} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    userLoginInDto.setEmail("email");
    userLoginInDto.setPassword("password");

    String expectedString = "UserLoginInDto(email=email, password=password)";
    assertEquals(expectedString, userLoginInDto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the
   * {@link UserLoginInDto} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    UserLoginInDto userLoginInDto1 = buildUserLoginInDto("email", "password");

    assertEquals(userLoginInDto1, userLoginInDto1);
    assertEquals(userLoginInDto1.hashCode(), userLoginInDto1.hashCode());

    UserLoginInDto userLoginInDto2 = buildUserLoginInDto("email", "password");
    assertEquals(userLoginInDto1, userLoginInDto2);
    assertEquals(userLoginInDto1.hashCode(), userLoginInDto2.hashCode());

    userLoginInDto2.setPassword("differentPassword");
    assertNotEquals(userLoginInDto1, userLoginInDto2);
    assertNotEquals(userLoginInDto1.hashCode(), userLoginInDto2.hashCode());

    userLoginInDto1 = new UserLoginInDto();
    userLoginInDto2 = new UserLoginInDto();
    assertEquals(userLoginInDto1, userLoginInDto2);
    assertEquals(userLoginInDto1.hashCode(), userLoginInDto2.hashCode());
  }

  /**
   * Helper method to create an instance of {@link UserLoginInDto} with specified values.
   *
   * @param email    the email to set
   * @param password the password to set
   * @return a configured {@link UserLoginInDto} instance
   */
  private UserLoginInDto buildUserLoginInDto(final String email, final String password) {
    UserLoginInDto userLoginInDto = new UserLoginInDto();
    userLoginInDto.setEmail(email);
    userLoginInDto.setPassword(password);
    return userLoginInDto;
  }
}
