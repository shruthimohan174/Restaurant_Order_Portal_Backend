package com.users.entities;

import com.users.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
/**
 * Unit tests for {@link User}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link User} class, including getters, setters, {@code toString}
 * method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class UserTests {

  /**
   * Sets up the test environment by initializing a {@link User}
   * object with predefined values before each test.
   */
  private User user;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1);
    user.setFirstName("First");
    user.setLastName("Last");
    user.setEmail("email");
    user.setPassword("Password123");
    user.setPhoneNumber("0123456789");
    user.setWalletBalance(new BigDecimal("100.00"));
    user.setUserRole(UserRole.CUSTOMER);
  }

  /**
   * Tests the getters and setters of the {@link User} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, user.getId());
    assertEquals("First", user.getFirstName());
    assertEquals("Last", user.getLastName());
    assertEquals("email", user.getEmail());
    assertEquals("Password123", user.getPassword());
    assertEquals("0123456789", user.getPhoneNumber());
    assertEquals(new BigDecimal("100.00"), user.getWalletBalance());
    assertEquals(UserRole.CUSTOMER, user.getUserRole());
  }

  /**
   * Tests the {@code toString} method of the {@link User} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    String expected = "User(id=1, firstName=First, lastName=Last, email=email, password=Password123,"
      + " phoneNumber=0123456789, walletBalance=100.00, userRole=CUSTOMER)";
    assertEquals(expected, user.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link User} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    User user1 = buildUser(1, "First", "Last", "email", "Password123",
      "0123456789", new BigDecimal("100.00"), UserRole.CUSTOMER);
    User user2 = buildUser(1, "First", "Last", "email", "Password123",
      "0123456789", new BigDecimal("100.00"), UserRole.CUSTOMER);

    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());

    user2.setFirstName("Different");
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(2, "First", "Last", "email", "Password123",
      "0123456789", new BigDecimal("100.00"), UserRole.CUSTOMER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(1, "First", "Last", "email2", "Password123",
      "0123456789", new BigDecimal("100.00"), UserRole.CUSTOMER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(1, "First", "Last", "email", "DifferentPassword",
      "0123456789", new BigDecimal("100.00"), UserRole.CUSTOMER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(1, "First", "Last", "email", "Password123",
      "DifferentPhoneNumber", new BigDecimal("100.00"), UserRole.CUSTOMER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(1, "First", "Last", "email", "Password123",
      "0123456789", new BigDecimal("200.00"), UserRole.CUSTOMER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = buildUser(1, "First", "Last", "email", "Password123",
      "0123456789", new BigDecimal("100.00"), UserRole.RESTAURANT_OWNER);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    User user3 = new User();
    User user4 = new User();
    assertEquals(user3, user4);
    assertEquals(user3.hashCode(), user4.hashCode());
  }

  /**
   * Helper method to create an instance of {@link User} with specified values.
   *
   * @param id             the ID to set
   * @param firstName      the first name to set
   * @param lastName       the last name to set
   * @param email          the email to set
   * @param password       the password to set
   * @param phoneNumber    the phone number to set
   * @param walletBalance  the wallet balance to set
   * @param userRole       the user role to set
   * @return a configured {@link User} instance
   */
  private User buildUser(final int id, final String firstName, final String lastName,
                         final String email, final String password,
                         final String phoneNumber, final BigDecimal walletBalance, final UserRole userRole) {
    User user = new User();
    user.setId(id);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword(password);
    user.setPhoneNumber(phoneNumber);
    user.setWalletBalance(walletBalance);
    user.setUserRole(userRole);
    return user;
  }
}
