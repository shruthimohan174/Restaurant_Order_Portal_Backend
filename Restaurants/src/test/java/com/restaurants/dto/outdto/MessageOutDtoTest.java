package com.restaurants.dto.outdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageOutDtoTest {

  private MessageOutDto messageOutDto1;
  private MessageOutDto messageOutDto2;

  @BeforeEach
  void setUp() {
    messageOutDto1 = new MessageOutDto();
    messageOutDto1.setMessage("Operation successful");

    messageOutDto2 = new MessageOutDto();
    messageOutDto2.setMessage("Operation successful");
  }

  @Test
  void testGettersSetters() {
    MessageOutDto dto = new MessageOutDto();
    dto.setMessage("Test message");

    assertEquals("Test message", dto.getMessage());
  }

  @Test
  void testToString() {
    String expected = "MessageOutDto(message=Operation successful)";
    assertEquals(expected, messageOutDto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(messageOutDto1, messageOutDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(messageOutDto1.hashCode(), messageOutDto2.hashCode());
  }
}
