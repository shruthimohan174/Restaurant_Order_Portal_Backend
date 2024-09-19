package com.orders.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link OrderInDto} class.
 * <p>
 * These tests cover the getters and setters, the {@code toString} method,
 * and the {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class OrderInDtoTest {

  /**
   * Tests the getters and setters of the {@link OrderInDto} class.
   */
  @Test
  public void testGettersAndSetters() {
    OrderInDto dto = new OrderInDto();
    Integer userId = 1;
    Integer deliveryAddressId = 2;
    Integer restaurantId = 3;

    dto.setUserId(userId);
    dto.setDeliveryAddressId(deliveryAddressId);
    dto.setRestaurantId(restaurantId);

    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto.setFoodItemId(1);
    cartItemDto.setQuantity(2);
    cartItemDto.setPrice(new BigDecimal("12.99"));

    dto.setCartItems(Collections.singletonList(cartItemDto));

    assertEquals(userId, dto.getUserId());
    assertEquals(deliveryAddressId, dto.getDeliveryAddressId());
    assertEquals(restaurantId, dto.getRestaurantId());
    assertNotNull(dto.getCartItems());
    assertEquals(1, dto.getCartItems().size());
    assertEquals(1, dto.getCartItems().get(0).getFoodItemId());
    assertEquals(2, dto.getCartItems().get(0).getQuantity());
    assertEquals(new BigDecimal("12.99"), dto.getCartItems().get(0).getPrice());
  }

  /**
   * Tests the {@code toString} method of the {@link OrderInDto} class.
   */
  @Test
  public void testToString() {
    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto.setFoodItemId(1);
    cartItemDto.setQuantity(2);
    cartItemDto.setPrice(new BigDecimal("12.99"));

    OrderInDto dto = new OrderInDto(1, 2, 3, Collections.singletonList(cartItemDto));
    String expected = "OrderInDto(userId=1, deliveryAddressId=2, restaurantId=3, "
      + "cartItems=[CartItemDto(foodItemId=1, quantity=2, price=12.99)])";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@code hashCode} method of the {@link OrderInDto} class.
   */
  @Test
  public void testHashCode() {
    CartItemDto cartItemDto1 = new CartItemDto();
    cartItemDto1.setFoodItemId(1);
    cartItemDto1.setQuantity(2);
    cartItemDto1.setPrice(new BigDecimal("12.99"));

    OrderInDto dto1 = buildOrderInDto(1, 2, 3, cartItemDto1);
    OrderInDto dto2 = buildOrderInDto(1, 2, 3, cartItemDto1);

    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Tests the {@code equals} method of the {@link OrderInDto} class.
   */
  @Test
  public void testEquals() {
    CartItemDto cartItemDto1 = new CartItemDto();
    cartItemDto1.setFoodItemId(1);
    cartItemDto1.setQuantity(2);
    cartItemDto1.setPrice(new BigDecimal("12.99"));

    OrderInDto dto1 = buildOrderInDto(1, 2, 3, cartItemDto1);
    OrderInDto dto2 = buildOrderInDto(1, 2, 3, cartItemDto1);
    OrderInDto dto3 = buildOrderInDto(4, 5, 6, cartItemDto1);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }

  /**
   * Builds an {@link OrderInDto} instance with the specified parameters.
   *
   * @param userId            The user ID.
   * @param deliveryAddressId The delivery address ID.
   * @param restaurantId      The restaurant ID.
   * @param cartItemDto       The cart item DTO.
   * @return The constructed {@link OrderInDto} instance.
   */
  private OrderInDto buildOrderInDto(final Integer userId, final Integer deliveryAddressId,
                                     final Integer restaurantId, final CartItemDto cartItemDto) {
    return new OrderInDto(userId, deliveryAddressId, restaurantId, Collections.singletonList(cartItemDto));
  }
}
