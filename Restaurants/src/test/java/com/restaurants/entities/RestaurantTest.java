package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link Restaurant} entity.
 * This class tests the methods and functionality of the {@link Restaurant} class, including its getters, setters,
 * {@code toString} method, and the {@code equals} and {@code hashCode} methods.
 */
public class RestaurantTest {

  /**
   * Restaurant data for the first {@link Restaurant} instance used in tests.
   */
  private Restaurant entity1;

  /**
   * Restaurant data for the second {@link Restaurant} instance used in tests.
   */
  private Restaurant entity2;

  /**
   * Sets up the test environment by initializing two {@link Restaurant} instances with identical values.
   * This method is called before each test.
   */
  @BeforeEach
  void setUp() {
    entity1 = buildRestaurant(101, 202, "Dummy Restaurant", "456 Dummy Lane", "555-0000", "10 AM - 10 PM", new byte[0]);
    entity2 = buildRestaurant(101, 202, "Dummy Restaurant", "456 Dummy Lane", "555-0000", "10 AM - 10 PM", new byte[0]);
  }

  /**
   * Tests the getter and setter methods of the {@link Restaurant} class.
   * Verifies that each getter method returns the expected value after setting it using the corresponding setter method.
   */
  @Test
  void testGettersSetters() {
    Restaurant restaurant = new Restaurant();

    assertNull(restaurant.getId());
    restaurant.setId(101);
    assertEquals(101, restaurant.getId());

    assertNull(restaurant.getUserId());
    restaurant.setUserId(202);
    assertEquals(202, restaurant.getUserId());

    assertNull(restaurant.getRestaurantName());
    restaurant.setRestaurantName("Dummy Restaurant");
    assertEquals("Dummy Restaurant", restaurant.getRestaurantName());

    assertNull(restaurant.getAddress());
    restaurant.setAddress("456 Dummy Lane");
    assertEquals("456 Dummy Lane", restaurant.getAddress());

    assertNull(restaurant.getContactNumber());
    restaurant.setContactNumber("555-0000");
    assertEquals("555-0000", restaurant.getContactNumber());

    assertNull(restaurant.getOpeningHours());
    restaurant.setOpeningHours("10 AM - 10 PM");
    assertEquals("10 AM - 10 PM", restaurant.getOpeningHours());

    assertNull(restaurant.getImageData());
    restaurant.setImageData(new byte[]{0, 1, 2});
    assertArrayEquals(new byte[]{0, 1, 2}, restaurant.getImageData());
  }

  /**
   * Tests the {@code toString} method of the {@link Restaurant} class.
   * Verifies that the {@code toString} method returns a string representation of the {@link Restaurant} object
   * with the expected format and values.
   */
  @Test
  void testToString() {
    String expected = "Restaurant(id=101, userId=202, restaurantName=Dummy Restaurant, address=456 Dummy Lane, "
      + "contactNumber=555-0000, openingHours=10 AM - 10 PM, imageData=[])";
    assertEquals(expected, entity1.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link Restaurant} class.
   * Verifies that two {@link Restaurant} objects with the same values are considered equal and have the same hash code.
   * Also checks that objects with different values are not considered equal and have different hash codes.
   */
  @Test
  void testEqualsAndHashcode() {
    assertEquals(entity1, entity1);
    assertEquals(entity1.hashCode(), entity1.hashCode());

    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(303, 404, "Different Restaurant", "789 Another Street", "555-1111", "9 AM - 9 PM", new byte[]{1});
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(101, 505, "Dummy Restaurant", "456 Dummy Lane", "555-0000", "10 AM - 10 PM", new byte[0]);
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(101, 202, "Another Dummy Restaurant", "456 Dummy Lane", "555-0000", "10 AM - 10 PM", new byte[0]);
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(101, 202, "Dummy Restaurant", "123 Another Lane", "555-0000", "10 AM - 10 PM", new byte[0]);
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(101, 202, "Dummy Restaurant", "456 Dummy Lane", "555-9999", "10 AM - 10 PM", new byte[0]);
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildRestaurant(101, 202, "Dummy Restaurant", "456 Dummy Lane", "555-0000", "8 AM - 8 PM", new byte[0]);
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());
  }

  /**
   * Helper method to create a {@link Restaurant} instance with specified values.
   *
   * @param id the restaurant ID
   * @param userId the user ID associated with the restaurant
   * @param restaurantName the name of the restaurant
   * @param address the address of the restaurant
   * @param contactNumber the contact number of the restaurant
   * @param operatingHours the opening hours of the restaurant
   * @param imageData the image data for the restaurant
   * @return a {@link Restaurant} object with the given values
   */
  private Restaurant buildRestaurant(final Integer id, final Integer userId, final String restaurantName,
                                     final String address,
                                     final String contactNumber, final String operatingHours, final byte[] imageData) {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(id);
    restaurant.setUserId(userId);
    restaurant.setRestaurantName(restaurantName);
    restaurant.setAddress(address);
    restaurant.setContactNumber(contactNumber);
    restaurant.setOpeningHours(operatingHours);
    restaurant.setImageData(imageData);
    return restaurant;
  }
}
