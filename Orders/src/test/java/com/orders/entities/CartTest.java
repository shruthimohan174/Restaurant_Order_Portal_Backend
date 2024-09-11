package com.orders.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CartTest {

  @Test
  public void testGettersAndSetters() {
    Cart cart = new Cart();
    cart.setId(1);
    cart.setUserId(2);
    cart.setFoodItemId(3);
    cart.setQuantity(4);
    cart.setPrice(new BigDecimal("10.50"));
    cart.setRestaurantId(5);

    assertEquals(1, cart.getId());
    assertEquals(2, cart.getUserId());
    assertEquals(3, cart.getFoodItemId());
    assertEquals(4, cart.getQuantity());
    assertEquals(new BigDecimal("10.50"), cart.getPrice());
    assertEquals(5, cart.getRestaurantId());
  }

  @Test
  public void testToString() {
    Cart cart = new Cart(1,2, 3, 4, new BigDecimal("10.50"), 5);
    String expected = "Cart(id=1, userId=2, foodItemId=3, quantity=4, price=10.50, restaurantId=5)";
    assertEquals(expected, cart.toString());
  }

  @Test
  public void testHashCode() {
    Cart cart1 = new Cart(1,2, 3, 4, new BigDecimal("10.50"), 5);
    Cart cart2 = new Cart(1,2, 3, 4, new BigDecimal("10.50"), 5);
    assertEquals(cart1.hashCode(), cart2.hashCode());
  }

  @Test
  public void testEquals() {
    Cart cart1 = new Cart(1,2, 3, 4, new BigDecimal("10.50"), 5);
    Cart cart2 = new Cart(2,2, 3, 4, new BigDecimal("10.50"), 5);
    Cart cart3 = new Cart(3,6, 7, 8, new BigDecimal("20.75"), 9);

    assertEquals(cart1, cart2);
    assertNotEquals(cart1, cart3);
    assertNotEquals(cart1, null);
    assertNotEquals(cart1, new Object());
  }
}
