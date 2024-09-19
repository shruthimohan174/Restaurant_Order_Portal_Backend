package com.users.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AddressInDto}.
 */
public class AddressInDtoTest {

  /**
   * Sets up the test environment by initializing an instance of {@link AddressInDto}.
   */
  private AddressInDto addressInDto;

  /**
   * Sets up the test environment by setting the dummy data.
   */
  @BeforeEach
  void setUp() {
    addressInDto = buildAddressInDto(1, "street", "City", 123456, "state");
  }

  /**
   * Tests the getter and setter methods of {@link AddressInDto}.
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, addressInDto.getUserId());
    assertEquals("street", addressInDto.getStreet());
    assertEquals("City", addressInDto.getCity());
    assertEquals(123456, addressInDto.getPincode());
    assertEquals("state", addressInDto.getState());
  }

  /**
   * Tests the {@link AddressInDto#toString()} method.
   */
  @Test
  void testToString() {
    String expected = "AddressInDto(street=street, city=City, state=state, pincode=123456, userId=1)";
    assertEquals(expected, addressInDto.toString());
  }

  /**
   * Tests the {@link AddressInDto#equals(Object)} method.
   */
  @Test
  void testEquals() {
    AddressInDto anotherAddressInDto = buildAddressInDto(1, "street", "City", 123456, "state");
    assertEquals(addressInDto, anotherAddressInDto);
  }

  /**
   * Tests the {@link AddressInDto#hashCode()} method.
   */
  @Test
  void testHashCode() {
    AddressInDto anotherAddressInDto = buildAddressInDto(1, "street", "City", 123456, "state");
    assertEquals(addressInDto.hashCode(), anotherAddressInDto.hashCode());
  }

  /**
   * Helper method to build an instance of {@link AddressInDto}.
   *
   * @param userId   the user ID
   * @param street   the street address
   * @param city     the city
   * @param pincode  the postal code
   * @param state    the state
   * @return a new {@link AddressInDto} instance
   */
  private AddressInDto buildAddressInDto(final int userId, final String street,
                                         final String city, final int pincode,  final String state) {
    AddressInDto addressInDto = new AddressInDto();
    addressInDto.setUserId(userId);
    addressInDto.setStreet(street);
    addressInDto.setCity(city);
    addressInDto.setPincode(pincode);
    addressInDto.setState(state);
    return addressInDto;
  }
}
