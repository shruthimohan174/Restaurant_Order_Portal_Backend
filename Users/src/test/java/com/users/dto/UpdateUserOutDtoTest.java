package com.users.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link UpdateUserOutDto}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link UpdateUserOutDto} class, including getters, setters,
 * {@code toString} method, and {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class UpdateUserOutDtoTest {

  /**
   * Sets up the test environment by initializing an {@link UpdateUserOutDto}
   * object with predefined values before each test.
   */
  private UpdateUserOutDto updateUserOutDto;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    updateUserOutDto = new UpdateUserOutDto();
    updateUserOutDto.setId(1);
    updateUserOutDto.setFirstName("First");
    updateUserOutDto.setLastName("Last");
    updateUserOutDto.setPhoneNumber("1234567890");
  }

  /**
   * Tests the getters and setters of the {@link UpdateUserOutDto} class.
   * <p>
   * Verifies that the getter methods return the correct values that were set
   * using the setter methods.
   * </p>
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, updateUserOutDto.getId());
    assertEquals("First", updateUserOutDto.getFirstName());
    assertEquals("Last", updateUserOutDto.getLastName());
    assertEquals("1234567890", updateUserOutDto.getPhoneNumber());
  }

  /**
   * Tests the {@code toString} method of the {@link UpdateUserOutDto} class.
   * <p>
   * Verifies that the {@code toString} method produces the expected string
   * representation of the object.
   * </p>
   */
  @Test
  void testToString() {
    String expected = "UpdateUserOutDto(id=1, firstName=First, lastName=Last, phoneNumber=1234567890)";
    assertEquals(expected, updateUserOutDto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the
   * {@link UpdateUserOutDto} class.
   * <p>
   * Verifies that the {@code equals} method correctly compares objects for equality,
   * and the {@code hashCode} method produces consistent hash codes for equal objects.
   * </p>
   */
  @Test
  void testEqualsAndHashCode() {
    UpdateUserOutDto updateUserOutDto1 = buildUpdateUserOutDto(1, "First", "Last", "1234567890");

    assertEquals(updateUserOutDto1, updateUserOutDto1);
    assertEquals(updateUserOutDto1.hashCode(), updateUserOutDto1.hashCode());

    assertNotEquals(updateUserOutDto1, new Object());

    UpdateUserOutDto updateUserOutDto2 = buildUpdateUserOutDto(1, "First", "Last", "1234567890");
    assertEquals(updateUserOutDto1, updateUserOutDto2);
    assertEquals(updateUserOutDto1.hashCode(), updateUserOutDto2.hashCode());

    updateUserOutDto2 = buildUpdateUserOutDto(2, "First", "Last", "1234567890");
    assertNotEquals(updateUserOutDto1, updateUserOutDto2);
    assertNotEquals(updateUserOutDto1.hashCode(), updateUserOutDto2.hashCode());

    updateUserOutDto2 = buildUpdateUserOutDto(1, "First", "Other", "1234567890");
    assertNotEquals(updateUserOutDto1, updateUserOutDto2);
    assertNotEquals(updateUserOutDto1.hashCode(), updateUserOutDto2.hashCode());

    updateUserOutDto2 = buildUpdateUserOutDto(1, "First", "Last", "0987654321");
    assertNotEquals(updateUserOutDto1, updateUserOutDto2);
    assertNotEquals(updateUserOutDto1.hashCode(), updateUserOutDto2.hashCode());

    updateUserOutDto1 = new UpdateUserOutDto();
    updateUserOutDto2 = new UpdateUserOutDto();
    assertEquals(updateUserOutDto1, updateUserOutDto2);
    assertEquals(updateUserOutDto1.hashCode(), updateUserOutDto2.hashCode());
  }

  /**
   * Helper method to create an instance of {@link UpdateUserOutDto} with specified values.
   *
   * @param id          the ID to set
   * @param firstName   the first name to set
   * @param lastName    the last name to set
   * @param phoneNumber the phone number to set
   * @return a configured {@link UpdateUserOutDto} instance
   */
  private UpdateUserOutDto buildUpdateUserOutDto(final int id, final String firstName,
                                                 final String lastName, final String phoneNumber) {
    UpdateUserOutDto updateUserOutDto = new UpdateUserOutDto();
    updateUserOutDto.setId(id);
    updateUserOutDto.setFirstName(firstName);
    updateUserOutDto.setLastName(lastName);
    updateUserOutDto.setPhoneNumber(phoneNumber);
    return updateUserOutDto;
  }
}
