package com.users.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
/**
 * Unit tests for {@link Address}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link Address} class, including getters, setters, {@code toString}
 * method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class AddressTests {
  /**
   * Sets up the test environment by initializing an {@link Address}
   * object with predefined values before each test.
   */
  private Address address;


  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    address = new Address();
    address.setId(1);
    address.setUserId(1);
    address.setStreet("street");
    address.setCity("city");
    address.setState("state");
    address.setPincode(123456);
  }

  /**
   * Tests the getters and setters of the {@link Address} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, address.getId());
    assertEquals(1, address.getUserId());
    assertEquals("street", address.getStreet());
    assertEquals("city", address.getCity());
    assertEquals("state", address.getState());
    assertEquals(123456, address.getPincode());
  }

  /**
   * Tests the {@code toString} method of the {@link Address} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    String expected = "Address(id=1, street=street, city=city, state=state, pincode=123456, userId=1)";
    assertEquals(expected, address.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link Address} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    Address address1 = buildAddress(1, 1, "street", "city", "state", 123456);
    Address address2 = buildAddress(1, 1, "street", "city", "state", 123456);

    assertEquals(address1, address2);
    assertEquals(address1.hashCode(), address2.hashCode());

    address2.setStreet("456 Another Street");
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    address2 = buildAddress(2, 1, "street", "city", "state", 123456);
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    address2 = buildAddress(1, 2, "street", "city", "state", 123456);
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    address2 = buildAddress(1, 1, "street", "Different City", "state", 123456);
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    address2 = buildAddress(1, 1, "street", "city", "Different State", 123456);
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    address2 = buildAddress(1, 1, "street", "city", "state", 654321);
    assertNotEquals(address1, address2);
    assertNotEquals(address1.hashCode(), address2.hashCode());

    Address address3 = new Address();
    Address address4 = new Address();
    assertEquals(address3, address4);
    assertEquals(address3.hashCode(), address4.hashCode());
  }

  /**
   * Helper method to create an instance of {@link Address} with specified values.
   *
   * @param id        the ID to set
   * @param userId    the user ID to set
   * @param street    the street to set
   * @param city      the city to set
   * @param state     the state to set
   * @param pincode   the pincode to set
   * @return a configured {@link Address} instance
   */
  private Address buildAddress(final int id, final int userId, final String street,
                               final String city, final String state, final int pincode) {
    Address address = new Address();
    address.setId(id);
    address.setUserId(userId);
    address.setStreet(street);
    address.setCity(city);
    address.setState(state);
    address.setPincode(pincode);
    return address;
  }
}
