package com.restaurants.dto;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for {@link RestaurantInDto}.
 */
public class RestaurantInDtoTest {

  /**
   * Tests the getters and setters of {@link RestaurantInDto}.
   */
  @Test
  public void testGettersAndSetters() {
    RestaurantInDto dto = new RestaurantInDto();

    assertNull(dto.getUserId());
    int userId = 1001;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    assertNull(dto.getRestaurantName());
    String restaurantName = "Dummy Restaurant";
    dto.setRestaurantName(restaurantName);
    assertEquals(restaurantName, dto.getRestaurantName());

    assertNull(dto.getAddress());
    String address = "456 Dummy Road";
    dto.setAddress(address);
    assertEquals(address, dto.getAddress());

    assertNull(dto.getContactNumber());
    String contactNumber = "1234567890";
    dto.setContactNumber(contactNumber);
    assertEquals(contactNumber, dto.getContactNumber());

    assertNull(dto.getOpeningHours());
    String openingHours = "10 AM - 8 PM";
    dto.setOpeningHours(openingHours);
    assertEquals(openingHours, dto.getOpeningHours());
  }

  /**
   * Tests the {@link RestaurantInDto#toString()} method.
   */
  @Test
  public void testToString() {
    RestaurantInDto dto = new RestaurantInDto();
    dto.setUserId(1001);
    dto.setRestaurantName("Dummy Restaurant");
    dto.setAddress("456 Dummy Road");
    dto.setContactNumber("1234567890");
    dto.setOpeningHours("10 AM - 8 PM");

    String expected = "RestaurantInDto(userId=1001, restaurantName=Dummy Restaurant,"
      + " address=456 Dummy Road, contactNumber=1234567890, openingHours=10 AM - 8 PM)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@link RestaurantInDto#equals(Object)} and {@link RestaurantInDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    RestaurantInDto dto1 = buildRestaurantInDto(1001, "Dummy Restaurant", "456 Dummy Road", "1234567890", "10 AM - 8 PM");
    RestaurantInDto dto2 = buildRestaurantInDto(1001, "Dummy Restaurant", "456 Dummy Road", "1234567890", "10 AM - 8 PM");

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setRestaurantName("New Dummy Restaurant");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build a {@link RestaurantInDto} instance with provided values.
   *
   * @param userId          the user ID
   * @param restaurantName the restaurant name
   * @param address         the address
   * @param contactNumber   the contact number
   * @param openingHours    the opening hours
   * @return a {@link RestaurantInDto} instance
   */
  private RestaurantInDto buildRestaurantInDto(final int userId, final String restaurantName, final String address,
                                               final String contactNumber, final String openingHours) {
    RestaurantInDto dto = new RestaurantInDto();
    dto.setUserId(userId);
    dto.setRestaurantName(restaurantName);
    dto.setAddress(address);
    dto.setContactNumber(contactNumber);
    dto.setOpeningHours(openingHours);
    return dto;
  }
}
