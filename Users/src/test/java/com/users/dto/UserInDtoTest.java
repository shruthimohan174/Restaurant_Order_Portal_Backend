package com.users.dto;

import com.users.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for {@link UserInDto}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link UserInDto} class, including getters, setters, {@code toString}
 * method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class UserInDtoTest {

  /**
   * Sets up the test environment by initializing a {@link UserInDto}
   * object before each test.
   */
  private UserInDto userInDto;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    userInDto = new UserInDto();
  }

  /**
   * Tests the getters and setters of the {@link UserInDto} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    userInDto.setFirstName("First");
    userInDto.setLastName("Last");
    userInDto.setEmail("email");
    userInDto.setPassword("password1@");
    userInDto.setPhoneNumber("1234567890");
    userInDto.setUserRole(UserRole.CUSTOMER);

    assertEquals("First", userInDto.getFirstName());
    assertEquals("Last", userInDto.getLastName());
    assertEquals("email", userInDto.getEmail());
    assertEquals("password1@", userInDto.getPassword());
    assertEquals("1234567890", userInDto.getPhoneNumber());
    assertEquals(UserRole.CUSTOMER, userInDto.getUserRole());
  }

  /**
   * Tests the {@code toString} method of the {@link UserInDto} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    userInDto.setFirstName("First");
    userInDto.setLastName("Last");
    userInDto.setEmail("email");
    userInDto.setPassword("password1@");
    userInDto.setPhoneNumber("1234567890");
    userInDto.setUserRole(UserRole.CUSTOMER);

    String expectedString = "UserInDto(firstName=First, lastName=Last, email=email, "
      + "password=password1@, phoneNumber=1234567890, userRole=CUSTOMER)";
    assertEquals(expectedString, userInDto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the
   * {@link UserInDto} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    UserInDto userInDto1 = buildUserInDto("First", "Last", "email", "password1@",
      "1234567890", UserRole.CUSTOMER);

    assertEquals(userInDto1, userInDto1);
    assertEquals(userInDto1.hashCode(), userInDto1.hashCode());

    UserInDto userInDto2 = buildUserInDto("First", "Last", "email", "password1@",
      "1234567890", UserRole.CUSTOMER);
    assertEquals(userInDto1, userInDto2);
    assertEquals(userInDto1.hashCode(), userInDto2.hashCode());

    userInDto2.setFirstName("email2");
    assertNotEquals(userInDto1, userInDto2);
    assertNotEquals(userInDto1.hashCode(), userInDto2.hashCode());

    userInDto1 = new UserInDto();
    userInDto2 = new UserInDto();
    assertEquals(userInDto1, userInDto2);
    assertEquals(userInDto1.hashCode(), userInDto2.hashCode());
  }

  /**
   * Helper method to create an instance of {@link UserInDto} with specified values.
   *
   * @param firstName   the first name to set
   * @param lastName    the last name to set
   * @param email       the email to set
   * @param password    the password to set
   * @param phoneNumber the phone number to set
   * @param userRole    the user role to set
   * @return a configured {@link UserInDto} instance
   */
  private UserInDto buildUserInDto(final String firstName, final String lastName, final String email,
                                   final String password, final String phoneNumber, final UserRole userRole) {
    UserInDto userInDto = new UserInDto();
    userInDto.setFirstName(firstName);
    userInDto.setLastName(lastName);
    userInDto.setEmail(email);
    userInDto.setPassword(password);
    userInDto.setPhoneNumber(phoneNumber);
    userInDto.setUserRole(userRole);
    return userInDto;
  }
}
