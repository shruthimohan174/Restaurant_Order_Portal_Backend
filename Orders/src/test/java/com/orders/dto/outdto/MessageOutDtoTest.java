package com.orders.dto.outdto;

import com.orders.dto.MessageOutDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MessageOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    MessageOutDto dto = new MessageOutDto();
    dto.setMessage("Success");

    assertEquals("Success", dto.getMessage());
  }

  @Test
  public void testToString() {
    MessageOutDto dto = new MessageOutDto("Success");
    String expected = "MessageOutDto(message=Success)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    MessageOutDto dto1 = new MessageOutDto("Success");
    MessageOutDto dto2 = new MessageOutDto("Success");
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    MessageOutDto dto1 = new MessageOutDto("Success");
    MessageOutDto dto2 = new MessageOutDto("Success");
    MessageOutDto dto3 = new MessageOutDto("Failure");

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
