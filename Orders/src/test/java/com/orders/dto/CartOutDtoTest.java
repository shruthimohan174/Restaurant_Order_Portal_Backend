package com.orders.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link CartOutDto}.
 */
public class CartOutDtoTest {

  /**
   * Tests the getters and setters of the {@link CartOutDto} class.
   */
  @Test
  public void testGettersAndSetters() {
    CartOutDto dto = new CartOutDto();

    assertNull(dto.getId());
    int id = 1;
    dto.setId(id);
    assertEquals(id, dto.getId());

    assertNull(dto.getUserId());
    int userId = 2;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    assertNull(dto.getFoodItemId());
    int foodItemId = 3;
    dto.setFoodItemId(foodItemId);
    assertEquals(foodItemId, dto.getFoodItemId());

    assertNull(dto.getQuantity());
    int quantity = 4;
    dto.setQuantity(quantity);
    assertEquals(quantity, dto.getQuantity());

    assertNull(dto.getPrice());
    BigDecimal price = new BigDecimal("10.50");
    dto.setPrice(price);
    assertEquals(price, dto.getPrice());

    assertNull(dto.getRestaurantId());
    int restaurantId = 5;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());
  }

  /**
   * Tests the {@link CartOutDto#toString()} method.
   */
  @Test
  public void testToString() {
    CartOutDto dto = buildCartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    String expected = "CartOutDto(id=1, userId=2, foodItemId=3, quantity=4, price=10.50, restaurantId=5)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@link CartOutDto#equals(Object)} and {@link CartOutDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    CartOutDto dto1 = buildCartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    CartOutDto dto2 = buildCartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    CartOutDto dto3 = buildCartOutDto(6, 7, 8, 9, new BigDecimal("20.75"), 10);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());

    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }

  /**
   * Builds a {@link CartOutDto} instance with the given parameters.
   *
   * @param id           the ID
   * @param userId       the user ID
   * @param foodItemId   the food item ID
   * @param quantity     the quantity
   * @param price        the price
   * @param restaurantId the restaurant ID
   * @return a {@link CartOutDto} instance
   */
  private CartOutDto buildCartOutDto(final int id, final int userId, final int foodItemId,
                                     final int quantity, final BigDecimal price, final int restaurantId) {
    CartOutDto dto = new CartOutDto();

    dto.setId(id);
    dto.setUserId(userId);
    dto.setFoodItemId(foodItemId);
    dto.setQuantity(quantity);
    dto.setPrice(price);
    dto.setRestaurantId(restaurantId);

    return dto;
  }
}
