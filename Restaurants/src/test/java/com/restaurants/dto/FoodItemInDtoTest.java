package com.restaurants.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link FoodItemInDto} class.
 * This class tests the functionality of the FoodItemInDto data transfer object, including
 * getters and setters, the {@code toString} method, and the {@code equals} and {@code hashCode} methods.
 */
public class FoodItemInDtoTest {

  /**
   * An instance of {@link FoodItemInDto} for testing.
   */
  private FoodItemInDto foodItemInDto1;

  /**
   * Another instance of {@link FoodItemInDto} for testing equality and hash code.
   */
  private FoodItemInDto foodItemInDto2;

  /**
   * Sets up the test environment by initializing {@link FoodItemInDto} instances with test data.
   */
  @BeforeEach
  void setUp() {
    foodItemInDto1 = new FoodItemInDto();
    foodItemInDto1.setCategoryId(100);
    foodItemInDto1.setRestaurantId(200);
    foodItemInDto1.setItemName("Dummy Item");
    foodItemInDto1.setDescription("Dummy description");
    foodItemInDto1.setIsVeg(true);
    foodItemInDto1.setPrice(BigDecimal.valueOf(99.99));

    foodItemInDto2 = new FoodItemInDto();
    foodItemInDto2.setCategoryId(100);
    foodItemInDto2.setRestaurantId(200);
    foodItemInDto2.setItemName("Dummy Item");
    foodItemInDto2.setDescription("Dummy description");
    foodItemInDto2.setIsVeg(true);
    foodItemInDto2.setPrice(BigDecimal.valueOf(99.99));
  }

  /**
   * Tests the getters and setters of {@link FoodItemInDto}.
   */
  @Test
  void testGettersSetters() {
    assertEquals(100, foodItemInDto1.getCategoryId());
    assertEquals(200, foodItemInDto1.getRestaurantId());
    assertEquals("Dummy Item", foodItemInDto1.getItemName());
    assertEquals("Dummy description", foodItemInDto1.getDescription());
    assertTrue(foodItemInDto1.getIsVeg());
    assertEquals(BigDecimal.valueOf(99.99), foodItemInDto1.getPrice());
  }

  /**
   * Tests the {@code toString} method of {@link FoodItemInDto}.
   */
  @Test
  void testToString() {
    String expected =
      "FoodItemInDto(categoryId=100, restaurantId=200, itemName=Dummy Item, "
        + "description=Dummy description, isVeg=true, price=99.99)";
    assertEquals(expected, foodItemInDto1.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of {@link FoodItemInDto}.
   */
  @Test
  void testEqualsAndHashcode() {
    assertEquals(foodItemInDto1, foodItemInDto2);
    assertEquals(foodItemInDto1.hashCode(), foodItemInDto2.hashCode());

    FoodItemInDto foodItemInDto3 = new FoodItemInDto();
    foodItemInDto3.setCategoryId(300);
    foodItemInDto3.setRestaurantId(400);
    foodItemInDto3.setItemName("Different Item");
    foodItemInDto3.setDescription("Different description");
    foodItemInDto3.setIsVeg(false);
    foodItemInDto3.setPrice(BigDecimal.valueOf(49.99));

    assertNotEquals(foodItemInDto1, foodItemInDto3);
    assertNotEquals(foodItemInDto1.hashCode(), foodItemInDto3.hashCode());
  }
}
