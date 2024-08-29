package com.users.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTests {

  private Address address;

  @BeforeEach
  void setUp() {
    address = new Address();
    address.setId(1);
    address.setUserId(1);
    address.setStreet("Plot No 147, Sector 74");
    address.setCity("Indore");
    address.setState("MP");
    address.setPincode(123456);
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, address.getId());
    assertEquals(1, address.getUserId());
    assertEquals("Plot No 147, Sector 74", address.getStreet());
    assertEquals("Indore", address.getCity());
    assertEquals("MP", address.getState());
    assertEquals(123456, address.getPincode());
  }

  @Test
  void testToString() {
    String expected = "Address(id=1, street=Plot No 147, Sector 74, city=Indore, state=MP, pincode=123456, userId=1)";
    assertEquals(expected, address.toString());
  }

  @Test
  void testEquals() {
    Address anotherAddress = new Address();
    anotherAddress.setId(1);
    anotherAddress.setUserId(1);
    anotherAddress.setStreet("Plot No 147, Sector 74");
    anotherAddress.setCity("Indore");
    anotherAddress.setState("MP");
    anotherAddress.setPincode(123456);

    assertEquals(address, anotherAddress);
  }

  @Test
  void testHashCode() {
    Address anotherAddress = new Address();
    anotherAddress.setId(1);
    anotherAddress.setUserId(1);
    anotherAddress.setStreet("Plot No 147, Sector 74");
    anotherAddress.setCity("Indore");
    anotherAddress.setState("MP");
    anotherAddress.setPincode(123456);

    assertEquals(address.hashCode(), anotherAddress.hashCode());
  }
}
