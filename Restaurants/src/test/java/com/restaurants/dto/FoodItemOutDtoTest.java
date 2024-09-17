package com.restaurants.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link FoodItemOutDto} class.
 * This class tests the functionality of the FoodItemOutDto data transfer object, including
 * getters and setters, the {@code toString} method, and the {@code equals} and {@code hashCode} methods.
 */
public class FoodItemOutDtoTest {

  /**
   * An instance of {@link FoodItemOutDto} for testing.
   */
  private FoodItemOutDto dto1;

  /**
   * Another instance of {@link FoodItemOutDto} for testing equality and hash code.
   */
  private FoodItemOutDto dto2;

  /**
   * Sets up the test environment by initializing {@link FoodItemOutDto} instances with test data.
   */
  @BeforeEach
  void setUp() {
    dto1 = buildFoodItemOutDto(1, 1, 1, "Dummy Item", "Dummy description", true, BigDecimal.valueOf(250.5), null);
    dto2 = buildFoodItemOutDto(1, 1, 1, "Dummy Item", "Dummy description", true, BigDecimal.valueOf(250.5), null);
  }

  /**
   * Tests the getters and setters of {@link FoodItemOutDto}.
   */
  @Test
  void testGettersAndSetters() {
    assertEquals(1, dto1.getId());
    assertEquals(1, dto1.getCategoryId());
    assertEquals(1, dto1.getRestaurantId());
    assertEquals("Dummy Item", dto1.getItemName());
    assertEquals("Dummy description", dto1.getDescription());
    assertTrue(dto1.getIsVeg());
    assertEquals(BigDecimal.valueOf(250.5), dto1.getPrice());
  }

  /**
   * Tests the {@code toString} method of {@link FoodItemOutDto}.
   */
  @Test
  void testToString() {
    String expected = "FoodItemOutDto(id=1, categoryId=1, restaurantId=1, itemName=Dummy Item, "
      + "description=Dummy description, isVeg=true, price=250.5, imageData=null)";
    assertEquals(expected, dto1.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of {@link FoodItemOutDto}.
   */
  @Test
  void testEqualsAndHashCode() {
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    FoodItemOutDto dto3 = buildFoodItemOutDto(2, 1, 1, "Dummy Item",
      "Dummy description", true, BigDecimal.valueOf(250.5), null);
    assertNotEquals(dto1, dto3);
  }

  /**
   * Builds a {@link FoodItemOutDto} instance with the given parameters.
   *
   * @param id            the ID of the food item
   * @param categoryId    the category ID of the food item
   * @param restaurantId  the restaurant ID of the food item
   * @param itemName      the name of the food item
   * @param description   the description of the food item
   * @param isVeg         whether the food item is vegetarian
   * @param price         the price of the food item
   * @param imageData     the image data of the food item
   * @return a {@link FoodItemOutDto} instance with the specified properties
   */
  private FoodItemOutDto buildFoodItemOutDto(final int id, final int categoryId, final int restaurantId,
                                             final String itemName, final String description,
                                             final boolean isVeg, final BigDecimal price, final Object imageData) {
    FoodItemOutDto dto = new FoodItemOutDto();
    dto.setId(id);
    dto.setCategoryId(categoryId);
    dto.setRestaurantId(restaurantId);
    dto.setItemName(itemName);
    dto.setDescription(description);
    dto.setIsVeg(isVeg);
    dto.setPrice(price);
    dto.setImageData((byte[]) imageData);
    return dto;
  }
}
