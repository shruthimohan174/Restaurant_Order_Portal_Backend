package com.users.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressInDtoTest {
  private AddressInDto addressInDto;

  @BeforeEach
  void setUp() {
    addressInDto = new AddressInDto();
    addressInDto.setUserId(1);
    addressInDto.setStreet("Plot No 147, Sector 74");
    addressInDto.setCity("Indore");
    addressInDto.setPincode(123456);
    addressInDto.setState("MP");
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, addressInDto.getUserId());
    assertEquals("Plot No 147, Sector 74", addressInDto.getStreet());
    assertEquals("Indore", addressInDto.getCity());
    assertEquals(123456, addressInDto.getPincode());
    assertEquals("MP", addressInDto.getState());
  }

  @Test
  void testToString() {
    String expected = "AddressInDto(street=Plot No 147, Sector 74, city=Indore, state=MP, pincode=123456, userId=1)";
    assertEquals(expected, addressInDto.toString());
  }

  @Test
  void testEquals() {
    AddressInDto anotherAddressInDto = new AddressInDto();
    anotherAddressInDto.setUserId(1);
    anotherAddressInDto.setStreet("Plot No 147, Sector 74");
    anotherAddressInDto.setCity("Indore");
    anotherAddressInDto.setPincode(123456);
    anotherAddressInDto.setState("MP");

    assertEquals(addressInDto, anotherAddressInDto);
  }

  @Test
  void testHashCode() {
    AddressInDto anotherAddressInDto = new AddressInDto();
    anotherAddressInDto.setUserId(1);
    anotherAddressInDto.setStreet("Plot No 147, Sector 74");
    anotherAddressInDto.setCity("Indore");
    anotherAddressInDto.setPincode(123456);
    anotherAddressInDto.setState("MP");

    assertEquals(addressInDto.hashCode(), anotherAddressInDto.hashCode());
  }
}
