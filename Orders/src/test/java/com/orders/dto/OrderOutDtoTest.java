package com.orders.dto;

import com.orders.utils.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link OrderOutDto} class.
 * <p>
 * These tests cover the getters and setters, the {@code toString} method,
 * and the {@code equals} and {@code hashCode} methods.
 * </p>
 */
public class OrderOutDtoTest {

  /**
   * Tests the getters and setters of the {@link OrderOutDto} class.
   */
  @Test
  public void testGettersAndSetters() {
    OrderOutDto dto = new OrderOutDto();

    assertNull(dto.getId());
    int id = 1;
    dto.setId(id);
    assertEquals(id, dto.getId());

    assertNull(dto.getUserId());
    int userId = 2;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    assertNull(dto.getDeliveryAddressId());
    int deliveryAddressId = 3;
    dto.setDeliveryAddressId(deliveryAddressId);
    assertEquals(deliveryAddressId, dto.getDeliveryAddressId());

    assertNull(dto.getOrderStatus());
    OrderStatus orderStatus = OrderStatus.PLACED;
    dto.setOrderStatus(orderStatus);
    assertEquals(orderStatus, dto.getOrderStatus());

    assertNull(dto.getOrderTime());
    LocalDateTime orderTime = LocalDateTime.now();
    dto.setOrderTime(orderTime);
    assertEquals(orderTime, dto.getOrderTime());

    assertNull(dto.getTotalPrice());
    BigDecimal totalPrice = BigDecimal.valueOf(150.0);
    dto.setTotalPrice(totalPrice);
    assertEquals(totalPrice, dto.getTotalPrice());

    assertNull(dto.getRestaurantId());
    int restaurantId = 4;
    dto.setRestaurantId(restaurantId);
    assertEquals(restaurantId, dto.getRestaurantId());
  }

  /**
   * Tests the {@code toString} method of the {@link OrderOutDto} class.
   */
  @Test
  public void testToString() {
    LocalDateTime now = LocalDateTime.now();
    OrderOutDto dto = new OrderOutDto();

    dto.setId(1);
    dto.setUserId(2);
    dto.setDeliveryAddressId(3);
    dto.setOrderStatus(OrderStatus.PLACED);
    dto.setCartItems(Collections.singletonList(new CartItemDto()));
    dto.setOrderTime(now);
    dto.setTotalPrice(BigDecimal.valueOf(150.0));
    dto.setRestaurantId(4);

    String expected = "OrderOutDto(id=1, userId=2, deliveryAddressId=3, orderStatus=PLACED, "
      + "cartItems=[CartItemDto(foodItemId=null, quantity=null, price=null)], orderTime=" + now + ","
      + " totalPrice=150.0, restaurantId=4)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@code equals} and {@code hashCode} methods of the {@link OrderOutDto} class.
   */
  @Test
  public void testEqualsAndHashCode() {
    LocalDateTime now = LocalDateTime.now();
    int id = 1;
    int userId = 2;
    int deliveryAddressId = 3;
    OrderStatus orderStatus = OrderStatus.PLACED;
    BigDecimal totalPrice = BigDecimal.valueOf(150.0);
    int restaurantId = 4;

    OrderOutDto dto1 = buildOrderOutDto(id, userId, deliveryAddressId, orderStatus, now, totalPrice, restaurantId);

    assertEquals(dto1, dto1);
    assertEquals(dto1.hashCode(), dto1.hashCode());

    assertNotEquals(dto1, new Object());

    OrderOutDto dto2 = buildOrderOutDto(id, userId, deliveryAddressId, orderStatus, now, totalPrice, restaurantId);
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id + 1, userId, deliveryAddressId, orderStatus, now, totalPrice, restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId + 1, deliveryAddressId, orderStatus, now, totalPrice, restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId, deliveryAddressId + 1, orderStatus, now, totalPrice, restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId, deliveryAddressId, OrderStatus.COMPLETED, now, totalPrice, restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId, deliveryAddressId, orderStatus, now.plusSeconds(1), totalPrice, restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId, deliveryAddressId, orderStatus, now, totalPrice.add(BigDecimal.ONE), restaurantId);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildOrderOutDto(id, userId, deliveryAddressId, orderStatus, now, totalPrice, restaurantId + 1);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new OrderOutDto();
    dto2 = new OrderOutDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }


  /**
   * Builds an {@link OrderOutDto} instance with the specified parameters.
   *
   * @param id                The ID.
   * @param userId            The user ID.
   * @param deliveryAddressId The delivery address ID.
   * @param orderStatus       The order status.
   * @param orderTime         The order time.
   * @param totalPrice        The total price.
   * @param restaurantId      The restaurant ID.
   * @return The constructed {@link OrderOutDto} instance.
   */
  private OrderOutDto buildOrderOutDto(final int id, final int userId, final int deliveryAddressId, final OrderStatus orderStatus,
                                       final LocalDateTime orderTime, final BigDecimal totalPrice, final int restaurantId) {
    OrderOutDto dto = new OrderOutDto();
    dto.setId(id);
    dto.setUserId(userId);
    dto.setDeliveryAddressId(deliveryAddressId);
    dto.setOrderStatus(orderStatus);
    dto.setOrderTime(orderTime);
    dto.setTotalPrice(totalPrice);
    dto.setRestaurantId(restaurantId);
    return dto;
  }
}
