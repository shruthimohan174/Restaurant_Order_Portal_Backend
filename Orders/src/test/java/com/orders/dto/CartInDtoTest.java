package com.orders.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link CartInDto}.
 */
public class CartInDtoTest {

  /**
   * Tests the getters and setters of the {@link CartInDto} class.
   */
  @Test
  public void testGetterAndSetter() {
    CartInDto cartInDto = new CartInDto();

    assertNull(cartInDto.getUserId());
    Integer userId = 1;
    cartInDto.setUserId(userId);
    assertEquals(userId, cartInDto.getUserId());

    assertNull(cartInDto.getFoodItemId());
    Integer foodItemId = 2;
    cartInDto.setFoodItemId(foodItemId);
    assertEquals(foodItemId, cartInDto.getFoodItemId());

    assertNull(cartInDto.getPrice());
    BigDecimal price = new BigDecimal("10.50");
    cartInDto.setPrice(price);
    assertEquals(price, cartInDto.getPrice());

    assertNull(cartInDto.getRestaurantId());
    Integer restaurantId = 3;
    cartInDto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, cartInDto.getRestaurantId());
  }

  /**
   * Tests the {@link CartInDto#toString()} method.
   */
  @Test
  public void testToString() {
    CartInDto cartInDto = new CartInDto();
    cartInDto.setUserId(1);
    cartInDto.setFoodItemId(2);
    cartInDto.setPrice(new BigDecimal("10.50"));
    cartInDto.setRestaurantId(3);

    String expected = "CartInDto(userId=1, foodItemId=2, price=10.50, restaurantId=3)";
    assertEquals(expected, cartInDto.toString());
  }

  /**
   * Tests the {@link CartInDto#equals(Object)} and {@link CartInDto#hashCode()} methods.
   */
  @Test
  public void testEqualsAndHashCode() {
    CartInDto dto1 = buildCartInDto(1, 2, new BigDecimal("10.50"), 3);
    CartInDto dto2 = buildCartInDto(1, 2, new BigDecimal("10.50"), 3);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setFoodItemId(3);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Tests the all-args constructor of the {@link CartInDto} class.
   */
  @Test
  public void testAllArgsConstructor() {
    CartInDto cartInDto = new CartInDto(1, 2, new BigDecimal("10.50"), 3);

    assertEquals(1, cartInDto.getUserId());
    assertEquals(2, cartInDto.getFoodItemId());
    assertEquals(new BigDecimal("10.50"), cartInDto.getPrice());
    assertEquals(3, cartInDto.getRestaurantId());
  }

  /**
   * Builds a {@link CartInDto} instance with the given parameters.
   *
   * @param userId       the user ID
   * @param foodItemId   the food item ID
   * @param price        the price
   * @param restaurantId the restaurant ID
   * @return a {@link CartInDto} instance
   */
  private CartInDto buildCartInDto(final Integer userId, final Integer foodItemId,
                                   final BigDecimal price, final Integer restaurantId) {
    CartInDto cartInDto = new CartInDto();
    cartInDto.setUserId(userId);
    cartInDto.setFoodItemId(foodItemId);
    cartInDto.setPrice(price);
    cartInDto.setRestaurantId(restaurantId);
    return cartInDto;
  }
}
