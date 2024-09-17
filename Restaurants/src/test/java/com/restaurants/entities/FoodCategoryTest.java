package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for {@link FoodCategory}.
 * <p>
 * This test class verifies the functionality of the {@link FoodCategory} entity,
 * including its getters, setters, and methods such as {@link FoodCategory#toString()},
 * {@link FoodCategory#equals(Object)}, and {@link FoodCategory#hashCode()}.
 * </p>
 */
public class FoodCategoryTest {

  /**
   * Food category data for entity1.
   */
  private FoodCategory entity1;

  /**
   * Food category data for entity2.
   */
  private FoodCategory entity2;

  /**
   * Sets up test data before each test case.
   * <p>
   * Initializes {@link #entity1} and {@link #entity2} with sample {@link FoodCategory} instances.
   * </p>
   */
  @BeforeEach
  void setUp() {
    entity1 = buildFoodCategory(100, 200, "Dummy Category");
    entity2 = buildFoodCategory(100, 200, "Dummy Category");
  }

  /**
   * Tests the getters and setters of {@link FoodCategory}.
   * <p>
   * Verifies that the getters and setters work as expected for {@link FoodCategory}'s properties.
   * </p>
   */
  @Test
  void testGettersSetters() {
    FoodCategory foodCategory = new FoodCategory();

    assertNull(foodCategory.getId());
    foodCategory.setId(100);
    assertEquals(100, foodCategory.getId());

    assertNull(foodCategory.getRestaurantId());
    foodCategory.setRestaurantId(200);
    assertEquals(200, foodCategory.getRestaurantId());

    assertNull(foodCategory.getCategoryName());
    foodCategory.setCategoryName("Dummy Category");
    assertEquals("Dummy Category", foodCategory.getCategoryName());
  }

  /**
   * Tests the {@link FoodCategory#toString()} method.
   * <p>
   * Verifies that the {@link FoodCategory#toString()} method returns the expected string representation.
   * </p>
   */
  @Test
  void testToString() {
    String expected = "FoodCategory(id=100, restaurantId=200, categoryName=Dummy Category)";
    assertEquals(expected, entity1.toString());
  }

  /**
   * Tests the {@link FoodCategory#equals(Object)} and {@link FoodCategory#hashCode()} methods.
   * <p>
   * Verifies that {@link FoodCategory#equals(Object)} and {@link FoodCategory#hashCode()} methods
   * behave as expected for equality and hash code calculations.
   * </p>
   */
  @Test
  void testEqualsAndHashcode() {
    assertEquals(entity1, entity1);
    assertEquals(entity1.hashCode(), entity1.hashCode());

    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildFoodCategory(101, 200, "Dummy Category");
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildFoodCategory(100, 201, "Dummy Category");
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());

    entity2 = buildFoodCategory(100, 200, "Other Category");
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());
  }

  /**
   * Helper method to build a {@link FoodCategory} instance with provided values.
   *
   * @param id             the category ID.
   * @param restaurantId   the restaurant ID.
   * @param categoryName   the category name.
   * @return a {@link FoodCategory} instance.
   */
  private FoodCategory buildFoodCategory(final Integer id, final Integer restaurantId, final String categoryName) {
    FoodCategory foodCategory = new FoodCategory();
    foodCategory.setId(id);
    foodCategory.setRestaurantId(restaurantId);
    foodCategory.setCategoryName(categoryName);
    return foodCategory;
  }
}
