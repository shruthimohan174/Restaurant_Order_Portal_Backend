package com.orders.controller;

import com.orders.dto.CartInDto;
import com.orders.dto.MessageOutDto;
import com.orders.entities.Cart;
import com.orders.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing cart operations.
 * Provides endpoints to add, update, remove, and fetch cart items.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

  private static final Logger logger = LoggerFactory.getLogger(CartController.class);

  @Autowired
  private CartService cartService;

  /**
   * Adds an item to the cart.
   *
   * @param cartInDto DTO containing the cart item details.
   * @return Response entity containing a success message.
   */
  @PostMapping("/add")
  public ResponseEntity<MessageOutDto> addItemToCart(@Valid @RequestBody CartInDto cartInDto) {
    logger.info("Adding item to cart: {}", cartInDto);
    MessageOutDto response = cartService.addItemToCart(cartInDto);
    logger.info("Item added to cart successfully");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates the quantity of an item in the cart.
   *
   * @param cartId         The ID of the cart.
   * @param quantityChange The change in quantity.
   * @return Response entity containing a success message.
   */
  @PutMapping("/update/{cartId}")
  public ResponseEntity<MessageOutDto> updateItemQuantity(@Valid @PathVariable Integer cartId, @RequestParam int quantityChange) {
    logger.info("Updating quantity for cart ID {}: change by {}", cartId, quantityChange);
    MessageOutDto response = cartService.updateCartItemQuantity(cartId, quantityChange);
    logger.info("Cart item quantity updated successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Removes an item from the cart.
   *
   * @param cartId The ID of the cart item to remove.
   * @return Response entity containing a success message.
   */
  @DeleteMapping("/remove/{cartId}")
  public ResponseEntity<MessageOutDto> removeItemFromCart(@PathVariable Integer cartId) {
    logger.info("Removing item from cart with ID {}", cartId);
    MessageOutDto response = cartService.removeItemFromCart(cartId);
    logger.info("Item removed from cart successfully");
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Clears the cart after an order is placed.
   *
   * @param userId The ID of the user.
   * @param restaurantId The ID of the restaurant.
   * @return Response entity containing a success message.
   */
  @DeleteMapping("/clear/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<MessageOutDto> clearCartAfterOrderPlaced(@PathVariable Integer userId, @PathVariable Integer restaurantId) {
    logger.info("Clearing cart for user ID {} and restaurant ID {}", userId, restaurantId);
    MessageOutDto response = cartService.clearCartAfterOrderPlaced(userId, restaurantId);
    logger.info("Cart cleared successfully after order placement");
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves all cart items for a specific user.
   *
   * @param userId The ID of the user.
   * @return Response entity containing a list of cart items.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable Integer userId) {
    logger.info("Fetching cart items for user ID {}", userId);
    List<Cart> cartItems = cartService.getCartByUserId(userId);
    return new ResponseEntity<>(cartItems, HttpStatus.OK);
  }

  /**
   * Retrieves a cart item by its ID.
   *
   * @param cartId The ID of the cart item.
   * @return Response entity containing the cart item.
   */
  @GetMapping("/{cartId}")
  public ResponseEntity<Cart> getCartById(@PathVariable Integer cartId) {
    logger.info("Fetching cart item with ID {}", cartId);
    Cart cart = cartService.getCartById(cartId);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  /**
   * Retrieves all cart items for a specific user and restaurant.
   *
   * @param userId The ID of the user.
   * @param restaurantId The ID of the restaurant.
   * @return Response entity containing a list of cart items.
   */
  @GetMapping("/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<List<Cart>> getCartByUserIdAndRestaurantId(@PathVariable Integer userId, @PathVariable Integer restaurantId) {
    logger.info("Fetching cart items for user ID {} and restaurant ID {}", userId, restaurantId);
    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(userId, restaurantId);
    return new ResponseEntity<>(cartItems, HttpStatus.OK);
  }

}
