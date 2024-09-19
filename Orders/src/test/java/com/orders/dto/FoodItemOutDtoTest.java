package com.orders.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link FoodItemOutDto}.
 */
public class FoodItemOutDtoTest {

  /**
   * Tests the getters and setters of the {@link FoodItemOutDto} class.
   */
  @Test
  public void testGettersAndSetters() {
    FoodItemOutDto dto = new FoodItemOutDto();

    assertNull(dto.getId());
    int id = 1;
    dto.setId(id);
    assertEquals(id, dto.getId());

    assertNull(dto.getRestaurantId());
    int restaurantId = 2;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());

    assertNull(dto.getItemName());
    String itemName = "DummyItem";
    dto.setItemName(itemName);
    assertEquals(itemName, dto.getItemName());

    assertNull(dto.getPrice());
    BigDecimal price = new BigDecimal("12.99");
    dto.setPrice(price);
    assertEquals(price, dto.getPrice());
  }

  /**
   * Tests the {@link FoodItemOutDto#toString()} method.
   */
  @Test
  public void testToString() {
    FoodItemOutDto dto = new FoodItemOutDto();

    int id = 1;
    dto.setId(id);

    int restaurantId = 2;
    dto.setRestaurantId(restaurantId);

    String itemName = "DummyItem";
    dto.setItemName(itemName);

    BigDecimal price = new BigDecimal("12.99");
    dto.setPrice(price);

    String expected = "FoodItemOutDto(id=1, restaurantId=2, itemName=DummyItem, price=12.99)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@link FoodItemOutDto#equals(Object)} and {@link FoodItemOutDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    int id = 1;
    int restaurantId = 2;
    String itemName = "DummyItem";
    BigDecimal price = new BigDecimal("12.99");

    FoodItemOutDto dto1 = buildFoodItemOutDto(id, restaurantId, itemName, price);

    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    FoodItemOutDto dto2 = buildFoodItemOutDto(id, restaurantId, itemName, price);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemOutDto(id + 1, restaurantId, itemName, price);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemOutDto(id, restaurantId + 1, itemName, price);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemOutDto(id, restaurantId, itemName + "X", price);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemOutDto(id, restaurantId, itemName, price.add(BigDecimal.ONE));
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new FoodItemOutDto();
    dto2 = new FoodItemOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to create a {@link FoodItemOutDto} with specified values.
   *
   * @param id           the ID of the food item
   * @param restaurantId the ID of the restaurant
   * @param itemName     the name of the food item
   * @param price        the price of the food item
   * @return a {@link FoodItemOutDto} instance with the given values
   */
  private FoodItemOutDto buildFoodItemOutDto(final int id, final int restaurantId, final String itemName,
                                             final BigDecimal price) {
    FoodItemOutDto dto = new FoodItemOutDto();
    dto.setId(id);
    dto.setRestaurantId(restaurantId);
    dto.setItemName(itemName);
    dto.setPrice(price);
    return dto;
  }
}
