package com.orders.dto.outdto;

import com.orders.dto.AddressOutDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AddressOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    AddressOutDto dto = new AddressOutDto();
    dto.setId(1);
    dto.setStreet("123 Main St");
    dto.setCity("Anytown");
    dto.setState("Anystate");
    dto.setPincode(12345);

    assertEquals(1, dto.getId());
    assertEquals("123 Main St", dto.getStreet());
    assertEquals("Anytown", dto.getCity());
    assertEquals("Anystate", dto.getState());
    assertEquals(12345, dto.getPincode());
  }

  @Test
  public void testToString() {
    AddressOutDto dto = new AddressOutDto(1, "123 Main St", "Anytown", "Anystate", 12345);
    String expected = "AddressOutDto(id=1, street=123 Main St, city=Anytown, state=Anystate, pincode=12345)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    AddressOutDto dto1 = new AddressOutDto(1, "123 Main St", "Anytown", "Anystate", 12345);
    AddressOutDto dto2 = new AddressOutDto(1, "123 Main St", "Anytown", "Anystate", 12345);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    AddressOutDto dto1 = new AddressOutDto(1, "123 Main St", "Anytown", "Anystate", 12345);
    AddressOutDto dto2 = new AddressOutDto(1, "123 Main St", "Anytown", "Anystate", 12345);
    AddressOutDto dto3 = new AddressOutDto(2, "456 Other St", "Othertown", "Otherstate", 54321);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
