package com.orders.controller;

import com.orders.dto.CartInDto;
import com.orders.dto.MessageOutDto;
import com.orders.entities.Cart;
import com.orders.service.CartService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CartController {

  /**
   * Service layer dependency for service-related operations.
   */
  @Autowired
  private CartService cartService;

  /**
   * Adds an item to the cart.
   *
   * @param cartInDto DTO containing the details of the item to be added.
   * @return Response entity containing a success message and HTTP status code 201 (Created).
   */
  @PostMapping("")
  public ResponseEntity<MessageOutDto> addItemToCart(final @Valid @RequestBody CartInDto cartInDto) {
    log.info("Adding item to cart");
    MessageOutDto response = cartService.addItemToCart(cartInDto);
    log.info("Item added to cart successfully.");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates the quantity of an item in the cart.
   *
   * @param cartId         The ID of the cart item to be updated.
   * @param quantityChange The change in quantity to be applied.
   * @return Response entity containing a success message and HTTP status code 200 (OK).
   */
  @PutMapping("/update/{cartId}")
  public ResponseEntity<MessageOutDto> updateItemQuantity(final @Valid @PathVariable Integer cartId,
                                                          @RequestParam final int quantityChange) {
    log.info("Updating quantity for cart item ID {}: change by {}", cartId, quantityChange);
    MessageOutDto response = cartService.updateCartItemQuantity(cartId, quantityChange);
    log.info("Cart item quantity updated successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Removes an item from the cart.
   *
   * @param cartId The ID of the cart item to be removed.
   * @return Response entity containing a success message and HTTP status code 204 (No Content).
   */
  @DeleteMapping("/remove/{cartId}")
  public ResponseEntity<MessageOutDto> removeItemFromCart(@PathVariable final Integer cartId) {
    log.info("Removing item from cart with ID {}", cartId);
    MessageOutDto response = cartService.removeItemFromCart(cartId);
    log.info("Item removed from cart successfully");
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Clears the cart after an order is placed.
   *
   * @param userId         The ID of the user whose cart is to be cleared.
   * @param restaurantId   The ID of the restaurant associated with the cart.
   * @return Response entity containing a success message and HTTP status code 204 (No Content).
   */
  @DeleteMapping("/clear/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<MessageOutDto> clearCartAfterOrderPlaced(@PathVariable final Integer userId,
                                                                 @PathVariable final Integer restaurantId) {
    log.info("Clearing cart for user ID {} and restaurant ID {}", userId, restaurantId);
    MessageOutDto response = cartService.clearCartAfterOrderPlaced(userId, restaurantId);
    log.info("Cart cleared successfully after order placement");
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves all cart items for a specific user.
   *
   * @param userId The ID of the user whose cart items are to be fetched.
   * @return Response entity containing a list of cart items and HTTP status code 200 (OK).
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable final Integer userId) {
    log.info("Fetching cart items for user ID {}", userId);
    List<Cart> cartItems = cartService.getCartByUserId(userId);
    return new ResponseEntity<>(cartItems, HttpStatus.OK);
  }

  /**
   * Retrieves a cart item by its ID.
   *
   * @param cartId The ID of the cart item to be fetched.
   * @return Response entity containing the cart item and HTTP status code 200 (OK).
   */
  @GetMapping("/{cartId}")
  public ResponseEntity<Cart> getCartById(@PathVariable final Integer cartId) {
    log.info("Fetching cart item with ID {}", cartId);
    Cart cart = cartService.getCartById(cartId);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  /**
   * Retrieves all cart items for a specific user and restaurant.
   *
   * @param userId         The ID of the user whose cart items are to be fetched.
   * @param restaurantId   The ID of the restaurant associated with the cart items.
   * @return Response entity containing a list of cart items and HTTP status code 200 (OK).
   */
  @GetMapping("/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<List<Cart>> getCartByUserIdAndRestaurantId(@PathVariable final Integer userId,
                                                                   @PathVariable final Integer restaurantId) {
    log.info("Fetching cart items for user ID {} and restaurant ID {}", userId, restaurantId);
    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(userId, restaurantId);
    return new ResponseEntity<>(cartItems, HttpStatus.OK);
  }
}
