package com.restaurants.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link FoodCategoryInDto}.
 * This class tests the methods of the {@link FoodCategoryInDto} class,
 * ensuring correct functionality and handling of various scenarios
 */
public class FoodCategoryInDtoTest {

  /**
   * Tests the getters and setters of the {@link FoodCategoryInDto} class.
   * Verifies that the getter methods return the expected values after the setter methods are called.
   */
  @Test
  void testGettersAndSetters() {
    FoodCategoryInDto dto = new FoodCategoryInDto();

    assertEquals(null, dto.getRestaurantId());
    int restaurantId = 123;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());

    assertNull(dto.getCategoryName());
    String categoryName = "DummyCategory";
    dto.setCategoryName(categoryName);
    assertEquals(categoryName, dto.getCategoryName());
  }

  /**
   * Tests the {@code toString} method of the {@link FoodCategoryInDto} class.
   * Verifies that the {@code toString} method returns a string representation of the object with the expected format.
   */
  @Test
  void testToString() {
    FoodCategoryInDto dto = new FoodCategoryInDto();
    dto.setRestaurantId(123);
    dto.setCategoryName("DummyCategory");

    assertEquals("FoodCategoryInDto(restaurantId=123, categoryName=DummyCategory)", dto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link FoodCategoryInDto} class.
   * Verifies that objects with the same values are considered equal and that their hash codes are consistent.
   * Also checks that objects with different values are not considered equal.
   */
  @Test
  void testEqualsAndHashCode() {
    int restaurantId = 123;
    String categoryName = "DummyCategory";

    FoodCategoryInDto dto1 = buildFoodCategoryInDto(restaurantId, categoryName);
    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    FoodCategoryInDto dto2 = buildFoodCategoryInDto(restaurantId, categoryName);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodCategoryInDto(restaurantId + 1, categoryName);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodCategoryInDto(restaurantId, categoryName + " ");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new FoodCategoryInDto();
    dto2 = new FoodCategoryInDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build a {@link FoodCategoryInDto} object with specified values.
   *
   * @param restaurantId the restaurant ID
   * @param categoryName the category name
   * @return a {@link FoodCategoryInDto} object with the given values
   */
  private FoodCategoryInDto buildFoodCategoryInDto(final int restaurantId, final String categoryName) {
    FoodCategoryInDto dto = new FoodCategoryInDto();
    dto.setRestaurantId(restaurantId);
    dto.setCategoryName(categoryName);
    return dto;
  }
}
