package com.users.dto;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link AddressOutDto}.
 */
public class AddressOutDtoTest {

  /**
   * Sets up the test environment by initializing an instance of {@link AddressOutDto}.
   */
  private AddressOutDto addressResponse;

  /**
   * Sets up the test environment by setting the dummy data.
   */
  @BeforeEach
  void setUp() {
    addressResponse = buildAddressOutDto(1, "street", "City", "state", 123456, 1);
  }

  /**
   * Tests the getter and setter methods of {@link AddressOutDto}.
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, addressResponse.getId());
    assertEquals("street", addressResponse.getStreet());
    assertEquals("City", addressResponse.getCity());
    assertEquals("state", addressResponse.getState());
    assertEquals(123456, addressResponse.getPincode());
    assertEquals(1, addressResponse.getUserId());
  }

  /**
   * Tests the {@link AddressOutDto#toString()} method.
   */
  @Test
  void testToString() {
    String expected = "AddressOutDto(id=1, street=street, city=City, state=state, pincode=123456, userId=1)";
    assertEquals(expected, addressResponse.toString());
  }

  /**
   * Tests the {@link AddressOutDto#equals(Object)} method.
   */
  @Test
  void testEquals() {
    AddressOutDto anotherAddressResponse = buildAddressOutDto(1, "street", "City", "state", 123456, 1);
    assertEquals(addressResponse, anotherAddressResponse);
  }

  /**
   * Tests the {@link AddressOutDto#hashCode()} method.
   */
  @Test
  void testHashCode() {
    AddressOutDto anotherAddressResponse = buildAddressOutDto(1, "street", "City", "state", 123456, 1);
    assertEquals(addressResponse.hashCode(), anotherAddressResponse.hashCode());
  }

  /**
   * Helper method to build an instance of {@link AddressOutDto}.
   *
   * @param id       the address ID
   * @param street   the street address
   * @param city     the city
   * @param state    the state
   * @param pincode  the postal code
   * @param userId   the user ID
   * @return a new {@link AddressOutDto} instance
   */
  private AddressOutDto buildAddressOutDto(final int id, final String street,
                                           final String city, final String state,
                                           final int pincode, final int userId) {
    AddressOutDto addressOutDto = new AddressOutDto();
    addressOutDto.setId(id);
    addressOutDto.setStreet(street);
    addressOutDto.setCity(city);
    addressOutDto.setState(state);
    addressOutDto.setPincode(pincode);
    addressOutDto.setUserId(userId);
    return addressOutDto;
  }
}
