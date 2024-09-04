package com.restaurants.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantInDtoTest {

  private RestaurantInDto restaurantInDto1;
  private RestaurantInDto restaurantInDto2;

  @BeforeEach
  void setUp() {
    restaurantInDto1 = new RestaurantInDto();
    restaurantInDto1.setUserId(1);
    restaurantInDto1.setRestaurantName("Food Palace");
    restaurantInDto1.setAddress("123 Food Street");
    restaurantInDto1.setContactNumber("9876543210");
    restaurantInDto1.setOpeningHours("9 AM - 9 PM");

    restaurantInDto2 = new RestaurantInDto();
    restaurantInDto2.setUserId(1);
    restaurantInDto2.setRestaurantName("Food Palace");
    restaurantInDto2.setAddress("123 Food Street");
    restaurantInDto2.setContactNumber("9876543210");
    restaurantInDto2.setOpeningHours("9 AM - 9 PM");
  }

  @Test
  void testGettersSetters() {

    assertEquals(1, restaurantInDto2.getUserId());
    assertEquals("Food Palace", restaurantInDto2.getRestaurantName());
    assertEquals("123 Food Street", restaurantInDto2.getAddress());
    assertEquals("9876543210", restaurantInDto2.getContactNumber());
    assertEquals("9 AM - 9 PM", restaurantInDto2.getOpeningHours());
  }


  @Test
  void testToString() {
    String expected =
      "RestaurantInDto(userId=1, restaurantName=Food Palace, address=123 Food Street, contactNumber=9876543210, " +
        "openingHours=9 AM - 9 PM)";
    assertEquals(expected, restaurantInDto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(restaurantInDto1, restaurantInDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(restaurantInDto1.hashCode(), restaurantInDto2.hashCode());
  }
}
