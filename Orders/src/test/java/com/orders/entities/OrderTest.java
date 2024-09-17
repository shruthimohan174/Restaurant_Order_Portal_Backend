package com.orders.entities;

import com.orders.utils.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Order}.
 * This class tests the getter and setter methods, {@code toString()} method, and {@code equals()} and {@code hashCode()} methods
 * of the {@link Order} class.
 */
class OrderTest {

  /**
   * Tests the {@code toString()} method of {@link Order}.
   * Verifies that the {@code toString()} method returns the correct string representation of the object.
   */
  @Test
  void testToString() {
    Order order = new Order();
    order.setId(1);
    order.setUserId(1);
    order.setDeliveryAddressId(1);
    order.setOrderStatus(OrderStatus.PLACED);
    order.setCartItems("[]");
    order.setOrderTime(LocalDateTime.now());
    order.setTotalPrice(BigDecimal.valueOf(100.0));
    order.setRestaurantId(1);

    String expectedString = "Order(id=1, userId=1, deliveryAddressId=1, orderStatus=PLACED, cartItems=[], orderTime="
      + order.getOrderTime() + ", totalPrice=100.0, restaurantId=1)";
    assertThat(order.toString()).contains(expectedString);
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of {@link Order}.
   * Verifies that the {@code equals()} method returns {@code true} for equal objects and {@code false} for non-equal objects,
   * and that {@code hashCode()} returns consistent values for equal objects.
   */
  @Test
  void testEqualsAndHashCode() {
    Order order1 = new Order();
    order1.setId(1);
    order1.setUserId(1);
    order1.setDeliveryAddressId(1);
    order1.setOrderStatus(OrderStatus.PLACED);
    order1.setCartItems("[]");
    order1.setOrderTime(LocalDateTime.now());
    order1.setTotalPrice(BigDecimal.valueOf(100.00));
    order1.setRestaurantId(1);

    Order order2 = new Order();
    order2.setId(1);
    order2.setUserId(1);
    order2.setDeliveryAddressId(1);
    order2.setOrderStatus(OrderStatus.PLACED);
    order2.setCartItems("[]");
    order2.setOrderTime(LocalDateTime.now());
    order2.setTotalPrice(BigDecimal.valueOf(100.00));
    order2.setRestaurantId(1);

    assertThat(order1).isEqualTo(order2);
    assertThat(order1.hashCode()).isEqualTo(order2.hashCode());

    order2.setId(2);
    assertThat(order1).isNotEqualTo(order2);
    assertThat(order1.hashCode()).isNotEqualTo(order2.hashCode());
  }

  /**
   * Tests the getter and setter methods of {@link Order}.
   * Verifies that the getters return the expected values after setting them through setters.
   */
  @Test
  void testGettersAndSetters() {
    Order order = new Order();
    order.setId(1);
    order.setUserId(1);
    order.setDeliveryAddressId(1);
    order.setOrderStatus(OrderStatus.PLACED);
    order.setCartItems("[]");
    order.setOrderTime(LocalDateTime.now());
    order.setTotalPrice(BigDecimal.valueOf(100.00));
    order.setRestaurantId(1);

    assertThat(order.getId()).isEqualTo(1);
    assertThat(order.getUserId()).isEqualTo(1);
    assertThat(order.getDeliveryAddressId()).isEqualTo(1);
    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PLACED);
    assertThat(order.getCartItems()).isEqualTo("[]");
    assertThat(order.getTotalPrice()).isEqualTo(BigDecimal.valueOf(100.00));
    assertThat(order.getRestaurantId()).isEqualTo(1);
  }
}
