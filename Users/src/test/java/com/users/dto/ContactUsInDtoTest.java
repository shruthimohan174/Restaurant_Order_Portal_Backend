package com.users.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * Unit tests for the {@link ContactUsInDto} class.
 * <p>
 * This class contains test cases for the methods in the
 * {@link ContactUsInDto} class, including getters, setters, {@code toString()},
 * {@code equals()}, and {@code hashCode()} methods.
 * </p>
 */
public class ContactUsInDtoTest {

  /**
   * The {@link ContactUsInDto} instance to be tested.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private ContactUsInDto contactUsDto;

  /**
   * Initializes the {@link ContactUsInDto} instance before each test.
   */
  @BeforeEach
  void setUp() {
    contactUsDto = new ContactUsInDto();
  }

  /**
   * Tests the getters and setters of the {@link ContactUsInDto} class.
   */
  @Test
  void testGettersAndSetters() {
    contactUsDto.setName("Test Name");
    contactUsDto.setEmails(Collections.singletonList("email"));
    contactUsDto.setSubject("Test Subject");
    contactUsDto.setMessage("Test Message");

    assertEquals("Test Name", contactUsDto.getName());
    assertEquals(Collections.singletonList("email"), contactUsDto.getEmails());
    assertEquals("Test Subject", contactUsDto.getSubject());
    assertEquals("Test Message", contactUsDto.getMessage());
  }

  /**
   * Tests the {@code toString()} method of the {@link ContactUsInDto} class.
   */
  @Test
  void testToString() {
    contactUsDto.setName("Test Name");
    contactUsDto.setEmails(Collections.singletonList("email"));
    contactUsDto.setSubject("Test Subject");
    contactUsDto.setMessage("Test Message");

    String expectedString =
      "ContactUsInDto(name=Test Name, emails=[email], subject=Test Subject, message=Test Message)";
    assertEquals(expectedString, contactUsDto.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of the
   * {@link ContactUsInDto} class.
   */
  @Test
  void testEqualsAndHashCode() {
    ContactUsInDto contactUsDto1 =
      buildContactUsInDto("Test Name", Collections.singletonList("email"), "Test Subject", "Test Message");

    assertEquals(contactUsDto1, contactUsDto1);
    assertEquals(contactUsDto1.hashCode(), contactUsDto1.hashCode());

    ContactUsInDto contactUsDto2 =
      buildContactUsInDto("Test Name", Collections.singletonList("email"), "Test Subject", "Test Message");
    assertEquals(contactUsDto1, contactUsDto2);
    assertEquals(contactUsDto1.hashCode(), contactUsDto2.hashCode());

    contactUsDto2.setMessage("Different Message");
    assertNotEquals(contactUsDto1, contactUsDto2);
    assertNotEquals(contactUsDto1.hashCode(), contactUsDto2.hashCode());

    contactUsDto1 = new ContactUsInDto();
    contactUsDto2 = new ContactUsInDto();
    assertEquals(contactUsDto1, contactUsDto2);
    assertEquals(contactUsDto1.hashCode(), contactUsDto2.hashCode());
  }

  /**
   * Helper method to create a {@link ContactUsInDto} with specified values.
   *
   * @param name the name to set
   * @param emails the list of emails to set
   * @param subject the subject to set
   * @param message the message to set
   * @return a {@link ContactUsInDto} instance with the specified values
   */
  private ContactUsInDto buildContactUsInDto(final String name, final List<String> emails,
                                             final String subject, final String message) {
    ContactUsInDto contactUsInDto = new ContactUsInDto();
    contactUsInDto.setName(name);
    contactUsInDto.setEmails(emails);
    contactUsInDto.setSubject(subject);
    contactUsInDto.setMessage(message);
    return contactUsInDto;
  }
}
