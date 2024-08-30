package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTest {

  private Restaurant entity1;
  private Restaurant entity2;

  @BeforeEach
  void setUp() {
    entity1 = new Restaurant();
    entity1.setId(1);
    entity1.setUserId(1);
    entity1.setRestaurantName("Food Palace");
    entity1.setAddress("123 Food Street");
    entity1.setContactNumber("9876543210");
    entity1.setOpeningHours("9 AM - 9 PM");
    entity1.setImageData(null);

    entity2 = new Restaurant();
    entity2.setId(1);
    entity2.setUserId(1);
    entity2.setRestaurantName("Food Palace");
    entity2.setAddress("123 Food Street");
    entity2.setContactNumber("9876543210");
    entity2.setOpeningHours("9 AM - 9 PM");
    entity2.setImageData(null);
  }

  @Test
  void testToString() {
    String expected =
      "Restaurant(id=1, userId=1, restaurantName=Food Palace, address=123 Food Street, contactNumber=9876543210, " +
        "openingHours=9 AM - 9 PM, imageData=null)";
    assertEquals(expected, entity1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(entity1, entity2);
  }

  @Test
  void testHashCode() {
    assertEquals(entity1.hashCode(), entity2.hashCode());
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, entity1.getId());
    assertEquals(1, entity1.getUserId());
    assertEquals("Food Palace", entity1.getRestaurantName());
    assertEquals("123 Food Street", entity1.getAddress());
    assertEquals("9876543210", entity1.getContactNumber());
    assertEquals("9 AM - 9 PM", entity1.getOpeningHours());
    assertArrayEquals(null, entity1.getImageData());
  }
}
