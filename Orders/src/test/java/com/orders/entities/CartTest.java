package com.orders.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link Cart}.
 * This class tests the getter and setter methods, {@code toString()} method, and {@code equals()} and {@code hashCode()} methods
 * of the {@link Cart} class.
 */
public class CartTest {

  /**
   * Tests the getter and setter methods of {@link Cart}.
   * Verifies that the getters return the expected values after setting them through setters.
   */
  @Test
  public void testGettersAndSetters() {
    Cart cart = new Cart();

    assertNull(cart.getId());
    Integer id = 1;
    cart.setId(id);
    assertEquals(id, cart.getId());

    assertNull(cart.getUserId());
    Integer userId = 2;
    cart.setUserId(userId);
    assertEquals(userId, cart.getUserId());

    assertNull(cart.getFoodItemId());
    Integer foodItemId = 3;
    cart.setFoodItemId(foodItemId);
    assertEquals(foodItemId, cart.getFoodItemId());

    assertNull(cart.getQuantity());
    Integer quantity = 4;
    cart.setQuantity(quantity);
    assertEquals(quantity, cart.getQuantity());

    assertNull(cart.getPrice());
    BigDecimal price = new BigDecimal("10.50");
    cart.setPrice(price);
    assertEquals(price, cart.getPrice());

    assertNull(cart.getRestaurantId());
    Integer restaurantId = 5;
    cart.setRestaurantId(restaurantId);
    assertEquals(restaurantId, cart.getRestaurantId());
  }

  /**
   * Tests the {@code toString()} method of {@link Cart}.
   * Verifies that the {@code toString()} method returns the correct string representation of the object.
   */
  @Test
  public void testToString() {
    Cart cart = new Cart(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    String expected = "Cart(id=1, userId=2, foodItemId=3, quantity=4, price=10.50, restaurantId=5)";
    assertEquals(expected, cart.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of {@link Cart}.
   * Verifies that the {@code equals()} method returns {@code true} for equal objects and {@code false} for non-equal objects,
   * and that {@code hashCode()} returns consistent values for equal objects.
   */
  @Test
  public void testEqualsAndHashCode() {
    Cart cart1 = buildCart(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    assertEquals(cart1, cart1);
    assertEquals(cart1.hashCode(), cart1.hashCode());

    assertNotEquals(cart1, new Object());

    Cart cart2 = buildCart(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    assertEquals(cart1, cart2);
    assertEquals(cart1.hashCode(), cart2.hashCode());

    cart2 = buildCart(2, 2, 3, 4, new BigDecimal("10.50"), 5);
    assertNotEquals(cart1, cart2);
    assertNotEquals(cart1.hashCode(), cart2.hashCode());

    cart2 = buildCart(1, 2, 3, 4, new BigDecimal("10.50"), 6);
    assertNotEquals(cart1, cart2);
    assertNotEquals(cart1.hashCode(), cart2.hashCode());
  }

  /**
   * Helper method to build a {@link Cart} instance with specified properties.
   *
   * @param id           the ID of the cart
   * @param userId       the ID of the user
   * @param foodItemId   the ID of the food item
   * @param quantity     the quantity of the food item
   * @param price        the price of the food item
   * @param restaurantId the ID of the restaurant
   * @return a {@link Cart} instance with the specified properties
   */
  private Cart buildCart(final Integer id, final Integer userId, final Integer foodItemId,
                         final Integer quantity, final BigDecimal price, final Integer restaurantId) {
    Cart cart = new Cart();
    cart.setId(id);
    cart.setUserId(userId);
    cart.setFoodItemId(foodItemId);
    cart.setQuantity(quantity);
    cart.setPrice(price);
    cart.setRestaurantId(restaurantId);
    return cart;
  }
}
