package com.orders.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link MessageOutDto}.
 */
public class MessageOutDtoTest {

  /**
   * Tests the getters and setters of the {@link MessageOutDto} class.
   */
  @Test
  public void testGettersAndSetters() {
    MessageOutDto dto = new MessageOutDto();

    assertNull(dto.getMessage());
    String message = "Success";
    dto.setMessage(message);
    assertEquals(message, dto.getMessage());
  }

  /**
   * Tests the {@link MessageOutDto#toString()} method.
   */
  @Test
  public void testToString() {
    MessageOutDto dto = new MessageOutDto();

    String message = "Success";
    dto.setMessage(message);

    assertEquals("MessageOutDto(message=Success)", dto.toString());
  }

  /**
   * Tests the {@link MessageOutDto#equals(Object)} and {@link MessageOutDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashcode() {
    String message = "Success";

    MessageOutDto dto1 = new MessageOutDto(message);

    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    MessageOutDto dto2 = new MessageOutDto(message);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = new MessageOutDto("Failure");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new MessageOutDto();
    dto2 = new MessageOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }
}
