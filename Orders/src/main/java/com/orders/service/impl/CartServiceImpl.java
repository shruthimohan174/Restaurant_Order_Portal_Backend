package com.orders.service.impl;

import com.orders.constants.OrderConstants;
import com.orders.dto.CartInDto;
import com.orders.dto.FoodItemOutDto;
import com.orders.dto.MessageOutDto;
import com.orders.dto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.exception.AccessDeniedException;
import com.orders.exception.ResourceConflictException;
import com.orders.exception.ResourceNotFoundException;
import com.orders.repositories.CartRepository;
import com.orders.service.CartService;
import com.orders.service.RestaurantFeignClient;
import com.orders.service.UserFeignClient;
import com.orders.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Cart-related operations.
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

  /**
   * Service layer dependency for user-related operations.
   */
  @Autowired
  private UserFeignClient userClient;

  /**
   * Service layer dependency for cart-related operations.
   */
  @Autowired
  private CartRepository cartRepository;

  /**
   * Service layer dependency for restaurant-related operations.
   */
  @Autowired
  private RestaurantFeignClient restaurantClient;

  /**
   * Adds an item to the cart. If the item already exists in the cart, updates its quantity.
   *
   * @param cartInDto Data transfer object containing cart details.
   * @return Success message indicating the action performed.
   */
  @Override
  public MessageOutDto addItemToCart(final CartInDto cartInDto) {
    log.info("Adding item to cart for userId: {}, foodItemId: {}", cartInDto.getUserId(), cartInDto.getFoodItemId());

    UserOutDto user = userClient.getUserById(cartInDto.getUserId());
    if (!user.getUserRole().equals(UserRole.CUSTOMER)) {
      log.error("User is not a customer. userId: {}", cartInDto.getUserId());
      throw new AccessDeniedException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
    restaurantClient.getRestaurantById(cartInDto.getRestaurantId());
    FoodItemOutDto foodItem = restaurantClient.getFoodItemById(cartInDto.getFoodItemId());

    if (foodItem.getPrice().compareTo(cartInDto.getPrice()) != 0) {
      throw new ResourceConflictException(OrderConstants.PRICE_MISMATCH);
    }

    Optional<Cart> existingCart = cartRepository.findByUserIdAndFoodItemIdAndRestaurantId(
      cartInDto.getUserId(),
      cartInDto.getFoodItemId(),
      cartInDto.getRestaurantId()
    );

    if (existingCart.isPresent()) {
      log.info("Item already in cart. Updating quantity for cartId: {}", existingCart.get().getId());
      return updateCartItemQuantity(existingCart.get().getId(), 1);
    } else {
      log.info("Adding new item to cart for userId: {}", cartInDto.getUserId());
      return addNewCartItem(cartInDto);
    }
  }

  /**
   * Adds a new item to the cart.
   *
   * @param cartInDto DTO containing cart details.
   * @return Success message indicating the item has been added to the cart.
   */
  private MessageOutDto addNewCartItem(final CartInDto cartInDto) {
    log.info("Creating new cart item for userId: {}, foodItemId: {}", cartInDto.getUserId(), cartInDto.getFoodItemId());

    Cart newCart = new Cart();
    newCart.setUserId(cartInDto.getUserId());
    newCart.setFoodItemId(cartInDto.getFoodItemId());
    newCart.setRestaurantId(cartInDto.getRestaurantId());
    newCart.setQuantity(1);
    newCart.setPrice(cartInDto.getPrice());

    cartRepository.save(newCart);
    log.info("Cart item added successfully for userId: {}", cartInDto.getUserId());
    return new MessageOutDto(OrderConstants.CART_ADDED_SUCCESSFULLY);
  }

  /**
   * Updates the quantity of an existing cart item.
   *
   * @param cartId         The ID of the cart item.
   * @param quantityChange The amount to change the quantity by.
   * @return Success message indicating the action performed.
   */
  @Override
  public MessageOutDto updateCartItemQuantity(final Integer cartId, final int quantityChange) {
    log.info("Updating cart item quantity for cartId: {}", cartId);

    Cart cart = getCartById(cartId);
    BigDecimal unitPrice = cart.getPrice().divide(BigDecimal.valueOf(cart.getQuantity()), BigDecimal.ROUND_HALF_EVEN);

    int newQuantity = Math.max(0, cart.getQuantity() + quantityChange);

    if (newQuantity == 0) {
      cartRepository.deleteById(cartId);
      log.info("Cart item removed successfully for cartId: {}", cartId);
      return new MessageOutDto(OrderConstants.ITEM_REMOVED_SUCCESSFULLY);
    }

    BigDecimal newPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity));

    cart.setQuantity(newQuantity);
    cart.setPrice(newPrice);
    cartRepository.save(cart);
    log.info("Cart item updated successfully for cartId: {}", cartId);
    return new MessageOutDto(OrderConstants.CART_UPDATED_SUCCESSFULLY);
  }

  /**
   * Removes an item from the cart.
   *
   * @param cartId The ID of the cart item to remove.
   * @return Success message indicating the item has been removed.
   */
  @Override
  public MessageOutDto removeItemFromCart(final Integer cartId) {
    log.info("Removing item from cart for cartId: {}", cartId);
    Cart cart = getCartById(cartId);
    cartRepository.deleteById(cartId);
    log.info("Item removed successfully from cart for cartId: {}", cartId);
    return new MessageOutDto(OrderConstants.ITEM_REMOVED_SUCCESSFULLY);
  }

  /**
   * Clears the cart after an order is placed.
   *
   * @param userId       The user ID whose cart should be cleared.
   * @param restaurantId The restaurant ID related to the cart.
   * @return Success message indicating the cart has been cleared.
   */
  @Override
  public MessageOutDto clearCartAfterOrderPlaced(final Integer userId, final Integer restaurantId) {
    log.info("Clearing cart for userId: {}, restaurantId: {}", userId, restaurantId);
    UserOutDto user = userClient.getUserById(userId);
    if (!user.getUserRole().equals(UserRole.CUSTOMER)) {
      throw new AccessDeniedException(OrderConstants.CUSTOMER_NOT_FOUND);
    }

    FoodItemOutDto restaurant = restaurantClient.getRestaurantById(restaurantId);

    List<Cart> cartItems = cartRepository.findByUserIdAndRestaurantId(userId, restaurantId);
    if (!cartItems.isEmpty()) {
      cartRepository.deleteAll(cartItems);
      log.info("Cart cleared successfully for userId: {}", userId);
      return new MessageOutDto(OrderConstants.CART_DELETED_SUCCESSFULLY);
    } else {
      log.info("Cart already empty for userId: {}", userId);
      return new MessageOutDto(OrderConstants.CART_ALREADY_EMPTY);
    }
  }

  /**
   * Retrieves all cart items for a specific user and restaurant.
   *
   * @param userId       The user ID.
   * @param restaurantId The restaurant ID.
   * @return List of cart items.
   */
  @Override
  public List<Cart> getCartItemsByUserIdAndRestaurantId(final Integer userId, final Integer restaurantId) {
    log.info("Retrieving cart items for userId: {}, restaurantId: {}", userId, restaurantId);
    UserOutDto user = userClient.getUserById(userId);
    if (!user.getUserRole().equals(UserRole.CUSTOMER)) {
      throw new AccessDeniedException(OrderConstants.CUSTOMER_NOT_FOUND);
    }

    FoodItemOutDto restaurant = restaurantClient.getRestaurantById(restaurantId);
    return cartRepository.findByUserIdAndRestaurantId(userId, restaurantId);
  }

  /**
   * Retrieves all cart items for a specific user.
   *
   * @param userId The user ID.
   * @return List of cart items.
   */
  @Override
  public List<Cart> getCartByUserId(final Integer userId) {
    log.info("Retrieving cart items for userId: {}", userId);
    UserOutDto user = userClient.getUserById(userId);
    if (!user.getUserRole().equals(UserRole.CUSTOMER)) {
      throw new AccessDeniedException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
    return cartRepository.findByUserId(userId);
  }

  /**
   * Retrieves a specific cart item by its ID.
   *
   * @param cartId The cart ID.
   * @return The cart item.
   * @throws ResourceNotFoundException If the cart item is not found.
   */
  @Override
  public Cart getCartById(final Integer cartId) {
    log.info("Retrieving cart item for cartId: {}", cartId);
    return cartRepository.findById(cartId)
      .orElseThrow(() -> new ResourceNotFoundException(OrderConstants.CART_NOT_FOUND));
  }
}
