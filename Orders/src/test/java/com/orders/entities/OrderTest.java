package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.utils.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

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

    String expectedString = "Order(id=1, userId=1, deliveryAddressId=1, orderStatus=PENDING, cartItems=[], orderTime="
      + order.getOrderTime() + ", totalPrice=100.0, restaurantId=1)";
    assertThat(order.toString()).contains(expectedString);
  }

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

  @Test
  void testGettersAndSetters() throws JsonProcessingException {
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

    List<Cart> cartItems = Collections.emptyList();
    order.setCartItemsFromList(cartItems);
    assertThat(order.getCartItemsAsList()).isEqualTo(cartItems);
  }
}
