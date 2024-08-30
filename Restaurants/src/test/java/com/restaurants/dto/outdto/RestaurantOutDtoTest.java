package com.restaurants.dto.outdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RestaurantOutDtoTest {

  private RestaurantOutDto dto1;
  private RestaurantOutDto dto2;

  @BeforeEach
  void setUp() {
    dto1 = new RestaurantOutDto();
    dto1.setId(1);
    dto1.setUserId(1);
    dto1.setRestaurantName("Food Palace");
    dto1.setAddress("123 Food Street");
    dto1.setContactNumber("9876543210");
    dto1.setOpeningHours("9 AM - 9 PM");
    dto1.setImageData(null);


    dto2 = new RestaurantOutDto();
    dto2.setId(1);
    dto2.setUserId(1);
    dto2.setRestaurantName("Food Palace");
    dto2.setAddress("123 Food Street");
    dto2.setContactNumber("9876543210");
    dto2.setOpeningHours("9 AM - 9 PM");
    dto2.setImageData(null);
  }

  @Test
  void testToString() {
    String expected = "RestaurantOutDto(id=1, userId=1, restaurantName=Food Palace, address=123 Food Street, contactNumber=9876543210, openingHours=9 AM - 9 PM, imageData=null)";
    assertEquals(expected, dto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(dto1, dto2);
  }

  @Test
  void testHashCode() {
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, dto1.getId());
    assertEquals(1, dto1.getUserId());
    assertEquals("Food Palace", dto1.getRestaurantName());
    assertEquals("123 Food Street", dto1.getAddress());
    assertEquals("9876543210", dto1.getContactNumber());
    assertEquals("9 AM - 9 PM", dto1.getOpeningHours());
    assertNull(dto1.getImageData());
  }
}
