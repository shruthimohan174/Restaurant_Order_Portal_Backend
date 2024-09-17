package com.orders.service;

import com.orders.dto.CartInDto;
import com.orders.dto.MessageOutDto;
import com.orders.entities.Cart;

import java.util.List;

public interface CartService {

  /**
   * Adds an item to the cart.
   *
   * @param cartInDto the DTO containing cart item details
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto addItemToCart(CartInDto cartInDto);

  /**
   * Updates the quantity of an item in the cart.
   *
   * @param cartId        the ID of the cart
   * @param quantityChange the quantity change (positive or negative)
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto updateCartItemQuantity(Integer cartId, int quantityChange);

  /**
   * Removes an item from the cart.
   *
   * @param cartId the ID of the cart
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto removeItemFromCart(Integer cartId);

  /**
   * Clears the cart after an order is placed.
   *
   * @param userId        the ID of the user
   * @param restaurantId  the ID of the restaurant
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto clearCartAfterOrderPlaced(Integer userId, Integer restaurantId);

  /**
   * Retrieves the cart items for a specific user.
   *
   * @param userId the ID of the user
   * @return a list of {@link Cart} items
   */
  List<Cart> getCartByUserId(Integer userId);

  /**
   * Retrieves the cart items for a specific user and restaurant.
   *
   * @param userId       the ID of the user
   * @param restaurantId the ID of the restaurant
   * @return a list of {@link Cart} items
   */
  List<Cart> getCartItemsByUserIdAndRestaurantId(Integer userId, Integer restaurantId);

  /**
   * Retrieves the cart by cart ID.
   *
   * @param userId the ID of the user
   * @return the cart object
   */
  Cart getCartById(Integer userId);
}
