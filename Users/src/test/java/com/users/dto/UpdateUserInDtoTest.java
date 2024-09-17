package com.users.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link UpdateUserInDto} class.
 * <p>
 * This class contains test cases for the methods in the
 * {@link UpdateUserInDto} class, including getters, setters, {@code toString()},
 * {@code equals()}, and {@code hashCode()} methods.
 * </p>
 */
public class UpdateUserInDtoTest {

  /**
   * The {@link UpdateUserInDto} instance to be tested.
   * <p>
   * This field is initialized in the {@link #setUp()} method.
   * </p>
   */
  private UpdateUserInDto updateUserInDto;

  /**
   * Initializes the {@link UpdateUserInDto} instance before each test.
   */
  @BeforeEach
  void setUp() {
    updateUserInDto = new UpdateUserInDto();
  }

  /**
   * Tests the getters and setters of the {@link UpdateUserInDto} class.
   */
  @Test
  void testGettersAndSetters() {
    updateUserInDto.setFirstName("First");
    updateUserInDto.setLastName("Last");
    updateUserInDto.setPhoneNumber("1234567890");

    assertEquals("First", updateUserInDto.getFirstName());
    assertEquals("Last", updateUserInDto.getLastName());
    assertEquals("1234567890", updateUserInDto.getPhoneNumber());
  }

  /**
   * Tests the {@code toString()} method of the {@link UpdateUserInDto} class.
   */
  @Test
  void testToString() {
    updateUserInDto.setFirstName("First");
    updateUserInDto.setLastName("Last");
    updateUserInDto.setPhoneNumber("1234567890");

    String expectedString = "UpdateUserInDto(firstName=First, lastName=Last, phoneNumber=1234567890, password=null)";
    assertEquals(expectedString, updateUserInDto.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of the
   * {@link UpdateUserInDto} class.
   */
  @Test
  void testEqualsAndHashCode() {
    UpdateUserInDto updateUserInDto1 =
      buildUpdateUserInDto("First", "Last", "1234567890");

    assertEquals(updateUserInDto1, updateUserInDto1);
    assertEquals(updateUserInDto1.hashCode(), updateUserInDto1.hashCode());

    UpdateUserInDto updateUserInDto2 =
      buildUpdateUserInDto("First", "Last", "1234567890");
    assertEquals(updateUserInDto1, updateUserInDto2);
    assertEquals(updateUserInDto1.hashCode(), updateUserInDto2.hashCode());

    updateUserInDto2.setFirstName("Different");
    assertNotEquals(updateUserInDto1, updateUserInDto2);
    assertNotEquals(updateUserInDto1.hashCode(), updateUserInDto2.hashCode());

    updateUserInDto1 = new UpdateUserInDto();
    updateUserInDto2 = new UpdateUserInDto();
    assertEquals(updateUserInDto1, updateUserInDto2);
    assertEquals(updateUserInDto1.hashCode(), updateUserInDto2.hashCode());
  }

  /**
   * Helper method to create an {@link UpdateUserInDto} with specified values.
   *
   * @param firstName the first name to set
   * @param lastName the last name to set
   * @param phoneNumber the phone number to set
   * @return an {@link UpdateUserInDto} instance with the specified values
   */
  private UpdateUserInDto buildUpdateUserInDto(final String firstName, final String lastName, final String phoneNumber) {
    UpdateUserInDto updateUserInDto = new UpdateUserInDto();
    updateUserInDto.setFirstName(firstName);
    updateUserInDto.setLastName(lastName);
    updateUserInDto.setPhoneNumber(phoneNumber);
    return updateUserInDto;
  }
}
