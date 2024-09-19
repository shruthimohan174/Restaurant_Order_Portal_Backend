package com.restaurants.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for {@link MessageOutDto}.
 * <p>
 * This class tests the functionality of the {@link MessageOutDto}
 * class, including its getters, setters, and overridden methods such as
 * {@code toString()}, {@code equals()}, and {@code hashCode()}.
 * </p>
 */
public class MessageOutDtoTest {

  /**
   * Tests the getters and setters of {@link MessageOutDto}.
   * <p>
   * Verifies that the getter and setter methods correctly handle the
   * {@code message} field.
   * </p>
   */
  @Test
  public void testGettersAndSetters() {
    MessageOutDto dto = new MessageOutDto();

    assertNull(dto.getMessage());
    String message = "Test message";
    dto.setMessage(message);
    assertEquals(message, dto.getMessage());
  }

  /**
   * Tests the {@code toString()} method of {@link MessageOutDto}.
   * <p>
   * Verifies that the {@code toString()} method returns a string representation
   * of the object that includes the {@code message} field.
   * </p>
   */
  @Test
  public void testToString() {
    MessageOutDto dto = new MessageOutDto();
    dto.setMessage("Operation successful");

    String expected = "MessageOutDto(message=Operation successful)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of {@link MessageOutDto}.
   * <p>
   * Verifies that two objects with the same {@code message} field value are considered equal
   * and have the same hash code. Also checks that objects with different {@code message} values
   * are not considered equal and have different hash codes.
   * </p>
   */
  @Test
  public void testEqualsAndHashCode() {
    MessageOutDto dto1 = new MessageOutDto();
    dto1.setMessage("Operation successful");

    MessageOutDto dto2 = new MessageOutDto();
    dto2.setMessage("Operation successful");

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setMessage("Operation failed");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new MessageOutDto();
    dto2 = new MessageOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }
}
