package com.restaurants.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link FoodCategoryOutDto}.
 * This class tests the methods of the {@link FoodCategoryOutDto} class,
 * ensuring correct functionality and handling of various scenarios
 */
public class FoodCategoryOutDtoTest {

  /**
   * Tests the getters and setters of the {@link FoodCategoryOutDto} class.
   * Verifies that the getter methods return the expected values after the setter methods are called.
   */
  @Test
  void testGettersAndSetters() {
    FoodCategoryOutDto dto = new FoodCategoryOutDto();

    assertEquals(null, dto.getId());
    int id = 456;
    dto.setId(id);
    assertEquals(id, dto.getId());

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
   * Tests the {@code toString} method of the {@link FoodCategoryOutDto} class.
   * Verifies that the {@code toString} method returns a string representation of the object with the expected format.
   */
  @Test
  void testToString() {
    FoodCategoryOutDto dto = new FoodCategoryOutDto();
    dto.setId(456);
    dto.setRestaurantId(123);
    dto.setCategoryName("DummyCategory");

    assertEquals("FoodCategoryOutDto(id=456, restaurantId=123, categoryName=DummyCategory)", dto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link FoodCategoryOutDto} class.
   * Verifies that objects with the same values are considered equal and that their hash codes are consistent.
   * Also checks that objects with different values are not considered equal.
   */
  @Test
  void testEqualsAndHashCode() {
    int id = 456;
    int restaurantId = 123;
    String categoryName = "DummyCategory";

    FoodCategoryOutDto dto1 = buildFoodCategoryOutDto(id, restaurantId, categoryName);
    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    FoodCategoryOutDto dto2 = buildFoodCategoryOutDto(id, restaurantId, categoryName);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodCategoryOutDto(id + 1, restaurantId, categoryName);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodCategoryOutDto(id, restaurantId + 1, categoryName);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodCategoryOutDto(id, restaurantId, categoryName + " ");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new FoodCategoryOutDto();
    dto2 = new FoodCategoryOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build a {@link FoodCategoryOutDto} object with specified values.
   *
   * @param id the ID
   * @param restaurantId the restaurant ID
   * @param categoryName the category name
   * @return a {@link FoodCategoryOutDto} object with the given values
   */
  private FoodCategoryOutDto buildFoodCategoryOutDto(final int id, final int restaurantId, final String categoryName) {
    FoodCategoryOutDto dto = new FoodCategoryOutDto();
    dto.setId(id);
    dto.setRestaurantId(restaurantId);
    dto.setCategoryName(categoryName);
    return dto;
  }
}
