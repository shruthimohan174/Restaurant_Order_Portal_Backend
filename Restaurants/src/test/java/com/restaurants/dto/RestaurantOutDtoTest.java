package com.restaurants.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for {@link RestaurantOutDto}.
 */
public class RestaurantOutDtoTest {

  /**
   * Tests the getters and setters of {@link RestaurantOutDto}.
   */
  @Test
  public void testGettersAndSetters() {
    RestaurantOutDto dto = new RestaurantOutDto();

    assertNull(dto.getId());
    int id = 2001;
    dto.setId(id);
    assertEquals(id, dto.getId());

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

    assertNull(dto.getImageData());
    byte[] imageData = new byte[] {1, 2, 3, 4, 5};
    dto.setImageData(imageData);
    assertArrayEquals(imageData, dto.getImageData());
  }

  /**
   * Tests the {@link RestaurantOutDto#toString()} method.
   */
  @Test
  public void testToString() {
    RestaurantOutDto dto = new RestaurantOutDto();
    dto.setId(2001);
    dto.setUserId(1001);
    dto.setRestaurantName("Dummy Restaurant");
    dto.setAddress("456 Dummy Road");
    dto.setContactNumber("1234567890");
    dto.setOpeningHours("10 AM - 8 PM");
    dto.setImageData(null);

    String expected = "RestaurantOutDto(id=2001, userId=1001, restaurantName=Dummy Restaurant, "
      + "address=456 Dummy Road, " + "contactNumber=1234567890, openingHours=10 AM - 8 PM, imageData=null)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@link RestaurantOutDto#equals(Object)} and {@link RestaurantOutDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    RestaurantOutDto dto1 = buildRestaurantOutDto(2001, 1001,
      "Dummy Restaurant", "456 Dummy Road", "1234567890", "10 AM - 8 PM", null);
    RestaurantOutDto dto2 = buildRestaurantOutDto(2001, 1001,
      "Dummy Restaurant", "456 Dummy Road", "1234567890", "10 AM - 8 PM", null);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setRestaurantName("New Dummy Restaurant");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build a {@link RestaurantOutDto} instance with provided values.
   *
   * @param id              the restaurant ID
   * @param userId          the user ID
   * @param restaurantName the restaurant name
   * @param address         the address
   * @param contactNumber   the contact number
   * @param openingHours    the opening hours
   * @param imageData       the image data
   * @return a {@link RestaurantOutDto} instance
   */
  private RestaurantOutDto buildRestaurantOutDto(final int id, final int userId, final String restaurantName,
                                                 final String address, final String contactNumber,
                                                 final String openingHours, final byte[] imageData) {
    RestaurantOutDto dto = new RestaurantOutDto();
    dto.setId(id);
    dto.setUserId(userId);
    dto.setRestaurantName(restaurantName);
    dto.setAddress(address);
    dto.setContactNumber(contactNumber);
    dto.setOpeningHours(openingHours);
    dto.setImageData(imageData);
    return dto;
  }
}
