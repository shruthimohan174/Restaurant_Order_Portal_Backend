package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link FoodItem} entity class.
 * This class tests the functionality of the FoodItem entity, including
 * getters and setters, the {@code toString} method, and the {@code equals} and {@code hashCode} methods.
 */
public class FoodItemTest {

  /**
   * Sets up food item item1.
   */
  private FoodItem item1;
  /**
   * Sets up food item item2.
   */
  private FoodItem item2;

  /**
   * Sets up the test environment by initializing {@link FoodItem} instances with default values.
   */
  @BeforeEach
  void setUp() {
    item1 = buildFoodItem(100, 200, 300, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
    item2 = buildFoodItem(100, 200, 300, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
  }

  /**
   * Tests the getters and setters of the {@link FoodItem} class.
   */
  @Test
  void testGettersSetters() {
    FoodItem foodItem = new FoodItem();

    // Test default getter values
    assertNull(foodItem.getId());
    foodItem.setId(100);
    assertEquals(100, foodItem.getId());

    assertNull(foodItem.getCategoryId());
    foodItem.setCategoryId(200);
    assertEquals(200, foodItem.getCategoryId());

    assertNull(foodItem.getRestaurantId());
    foodItem.setRestaurantId(300);
    assertEquals(300, foodItem.getRestaurantId());

    assertNull(foodItem.getItemName());
    foodItem.setItemName("Dummy Item");
    assertEquals("Dummy Item", foodItem.getItemName());

    assertNull(foodItem.getDescription());
    foodItem.setDescription("This is a dummy description");
    assertEquals("This is a dummy description", foodItem.getDescription());

    assertNull(foodItem.getIsVeg());
    foodItem.setIsVeg(false);
    assertFalse(foodItem.getIsVeg());

    assertNull(foodItem.getPrice());
    foodItem.setPrice(BigDecimal.valueOf(99.99));
    assertEquals(BigDecimal.valueOf(99.99), foodItem.getPrice());

    assertNull(foodItem.getImageData());
    foodItem.setImageData(new byte[]{});
    assertArrayEquals(new byte[]{}, foodItem.getImageData());
  }

  /**
   * Tests the {@code toString} method of the {@link FoodItem} class.
   */
  @Test
  void testToString() {
    String expected =
      "FoodItem(id=100, categoryId=200, restaurantId=300, itemName=Dummy Item, "
        + "description=This is a dummy description, "
        + "isVeg=false, price=99.99, imageData=[])";
    assertEquals(expected, item1.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link FoodItem} class.
   */
  @Test
  void testEqualsAndHashcode() {
    assertEquals(item1, item1);
    assertEquals(item1.hashCode(), item1.hashCode());

    // Test equality and hash code with objects having the same attributes
    assertEquals(item1, item2);
    assertEquals(item1.hashCode(), item2.hashCode());

    // Test inequality with different attributes
    item2 = buildFoodItem(101, 200, 300, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 201, 300, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 200, 301, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 200, 300, "Different Item",
      "This is a dummy description", false, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 200, 300, "Dummy Item",
      "Different description", false, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 200, 300, "Dummy Item",
      "This is a dummy description", true, BigDecimal.valueOf(99.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());

    item2 = buildFoodItem(100, 200, 300, "Dummy Item",
      "This is a dummy description", false, BigDecimal.valueOf(199.99), new byte[]{});
    assertNotEquals(item1, item2);
    assertNotEquals(item1.hashCode(), item2.hashCode());
  }

  /**
   * Creates a {@link FoodItem} instance with the specified attributes.
   *
   * @param id            the ID of the food item
   * @param categoryId    the category ID of the food item
   * @param restaurantId  the restaurant ID of the food item
   * @param itemName      the name of the food item
   * @param description   the description of the food item
   * @param isVeg         whether the food item is vegetarian
   * @param price         the price of the food item
   * @param imageData     the image data for the food item
   * @return a {@link FoodItem} instance with the specified attributes
   */
  private FoodItem buildFoodItem(final Integer id, final Integer categoryId, final Integer restaurantId,
                                 final String itemName, final String description,
                                 final Boolean isVeg, final BigDecimal price, final byte[] imageData) {
    FoodItem foodItem = new FoodItem();
    foodItem.setId(id);
    foodItem.setCategoryId(categoryId);
    foodItem.setRestaurantId(restaurantId);
    foodItem.setItemName(itemName);
    foodItem.setDescription(description);
    foodItem.setIsVeg(isVeg);
    foodItem.setPrice(price);
    foodItem.setImageData(imageData);
    return foodItem;
  }
}
