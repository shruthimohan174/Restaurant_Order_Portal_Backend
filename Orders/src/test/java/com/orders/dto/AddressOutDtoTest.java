package com.orders.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link AddressOutDto} class.
 */
public class AddressOutDtoTest {

  /**
   * Tests the getters and setters of the {@link AddressOutDto} class.
   */
  @Test
  public void testGetterAndSetter() {
    AddressOutDto addressOutDto = new AddressOutDto();

    assertNull(addressOutDto.getId());
    Integer id = 1;
    addressOutDto.setId(id);
    assertEquals(id, addressOutDto.getId());

    assertNull(addressOutDto.getStreet());
    String street = "street";
    addressOutDto.setStreet(street);
    assertEquals(street, addressOutDto.getStreet());

    assertNull(addressOutDto.getCity());
    String city = "city";
    addressOutDto.setCity(city);
    assertEquals(city, addressOutDto.getCity());

    assertNull(addressOutDto.getState());
    String state = "state";
    addressOutDto.setState(state);
    assertEquals(state, addressOutDto.getState());

    assertNull(addressOutDto.getPincode());
    int pincode = 1357;
    addressOutDto.setPincode(pincode);
    assertEquals(pincode, addressOutDto.getPincode());
  }

  /**
   * Tests the {@link AddressOutDto#toString()} method.
   */
  @Test
  public void testToString() {
    AddressOutDto addressOutDto = new AddressOutDto();

    Integer id = 1;
    addressOutDto.setId(id);

    String street = "street";
    addressOutDto.setStreet(street);

    String city = "city";
    addressOutDto.setCity(city);

    String state = "state";
    addressOutDto.setState(state);

    int pincode = 1357;
    addressOutDto.setPincode(pincode);

    String expected = "AddressOutDto(id=1, street=street, city=city, state=state, pincode=1357)";
    assertEquals(expected, addressOutDto.toString());
  }

  /**
   * Tests the {@link AddressOutDto#equals(Object)} and {@link AddressOutDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    Integer id = 1;
    String street = "street";
    String city = "city";
    String state = "state";
    int pincode = 1357;

    AddressOutDto dto1 = buildAddressOutDto(id, street, city, state, pincode);

    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    AddressOutDto dto2 = buildAddressOutDto(id, street, city, state, pincode);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildAddressOutDto(id, street + " ", city, state, pincode);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildAddressOutDto(id, street, city + " ", state, pincode);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildAddressOutDto(id, street, city, state + " ", pincode);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildAddressOutDto(id, street, city, state, pincode + 1);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildAddressOutDto(id + 1, street, city, state, pincode);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new AddressOutDto();
    dto2 = new AddressOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build an {@link AddressOutDto} instance with given properties.
   *
   * @param id      the ID of the address
   * @param street  the street of the address
   * @param city    the city of the address
   * @param state   the state of the address
   * @param pincode the pincode of the address
   * @return a fully initialized {@link AddressOutDto} instance
   */
  private AddressOutDto buildAddressOutDto(final Integer id, final String street, final String city, final String state,
                                           final int pincode) {
    AddressOutDto addressOutDto = new AddressOutDto();
    addressOutDto.setId(id);
    addressOutDto.setStreet(street);
    addressOutDto.setCity(city);
    addressOutDto.setState(state);
    addressOutDto.setPincode(pincode);
    return addressOutDto;
  }
}
