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
import com.orders.service.RestaurantFeignClient;
import com.orders.service.UserFeignClient;
import com.orders.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CartServiceImpl}.
 * This class tests the methods of the {@link CartServiceImpl} class, ensuring correct functionality
 * and handling of various scenarios
 * related to cart operations such as adding, updating, removing items, and clearing the cart.
 */
@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

  /**
   * Mock for the UserFeignClient used to interact with user-related services.
   */
  @Mock
  private UserFeignClient userClient;

  /**
   * Mock for the CartRepository used for cart-related database operations.
   */
  @Mock
  private CartRepository cartRepository;

  /**
   * Mock for the RestaurantFeignClient used to interact with restaurant-related services.
   */
  @Mock
  private RestaurantFeignClient restaurantClient;

  /**
   * Instance of {@link CartServiceImpl} to be tested.
   */
  @InjectMocks
  private CartServiceImpl cartService;

  /**
   * Data Transfer Object for cart input.
   */
  private CartInDto cartInDto;

  /**
   * Data Transfer Object for user output.
   */
  private UserOutDto userOutDto;

  /**
   * Data Transfer Object for food item output.
   */
  private FoodItemOutDto foodItemOutDto;

  /**
   * Cart entity instance for use in tests.
   */
  private Cart cart;

  /**
   * Sets up the test environment by initializing the necessary objects and mocks.
   * This method is executed before each test method to ensure a clean state.
   */
  @BeforeEach
  void setUp() {
    cartInDto = new CartInDto();
    cartInDto.setUserId(1);
    cartInDto.setFoodItemId(1);
    cartInDto.setRestaurantId(1);
    cartInDto.setPrice(new BigDecimal("10.00"));

    userOutDto = new UserOutDto();
    userOutDto.setUserRole(UserRole.CUSTOMER);

    foodItemOutDto = new FoodItemOutDto();
    foodItemOutDto.setPrice(new BigDecimal("10.00"));

    cart = new Cart();
    cart.setId(1);
    cart.setUserId(1);
    cart.setFoodItemId(1);
    cart.setRestaurantId(1);
    cart.setQuantity(1);
    cart.setPrice(new BigDecimal("10.00"));
  }

  @Test
  void addItemToCartSuccess() {
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(cartRepository.findByUserIdAndFoodItemIdAndRestaurantId(anyInt(), anyInt(), anyInt()))
      .thenReturn(Optional.empty());
    when(restaurantClient.getRestaurantById(cartInDto.getRestaurantId())).thenReturn(null);
    when(restaurantClient.getFoodItemById(cartInDto.getFoodItemId())).thenReturn(foodItemOutDto);
    when(cartRepository.save(any(Cart.class))).thenReturn(cart);

    MessageOutDto result = cartService.addItemToCart(cartInDto);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_ADDED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).save(any(Cart.class));
  }

  @Test
  void addItemToCartThrowsAccessDeniedException() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);

    assertThrows(AccessDeniedException.class, () -> cartService.addItemToCart(cartInDto));
    verify(userClient, times(1)).getUserById(cartInDto.getUserId());
  }

  @Test
  void addItemToCartThrowsPriceMismatchException() {
    foodItemOutDto.setPrice(new BigDecimal("12.00"));
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(restaurantClient.getRestaurantById(cartInDto.getRestaurantId())).thenReturn(null);
    when(restaurantClient.getFoodItemById(cartInDto.getFoodItemId())).thenReturn(foodItemOutDto);

    assertThrows(ResourceConflictException.class, () -> cartService.addItemToCart(cartInDto));
    verify(restaurantClient, times(1)).getFoodItemById(cartInDto.getFoodItemId());
  }

  @Test
  void updateCartItemQuantitySuccess() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.updateCartItemQuantity(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_UPDATED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).save(cart);
  }

  @Test
  void updateCartItemQuantityItemRemoved() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.updateCartItemQuantity(1, -1);

    assertNotNull(result);
    assertEquals(OrderConstants.ITEM_REMOVED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteById(1);
  }

  @Test
  void updateCartItemQuantityThrowsResourceNotFoundException() {
    when(cartRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> cartService.updateCartItemQuantity(1, 1));
  }

  @Test
  void removeItemFromCartSuccess() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.removeItemFromCart(1);

    assertNotNull(result);
    assertEquals(OrderConstants.ITEM_REMOVED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteById(1);
  }
  @Test
  void clearCartAfterOrderPlacedSuccess() {
    when(userClient.getUserById(1)).thenReturn(userOutDto);
    when(restaurantClient.getRestaurantById(1)).thenReturn(null);
    when(cartRepository.findByUserIdAndRestaurantId(1, 1))
      .thenReturn(Collections.singletonList(cart));

    MessageOutDto result = cartService.clearCartAfterOrderPlaced(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_DELETED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteAll(Collections.singletonList(cart));
  }

  @Test
  void clearCartAfterOrderPlacedCartAlreadyEmpty() {
    when(userClient.getUserById(1)).thenReturn(userOutDto);
    when(restaurantClient.getRestaurantById(1)).thenReturn(null);
    when(cartRepository.findByUserIdAndRestaurantId(1, 1)).thenReturn(Collections.emptyList());

    MessageOutDto result = cartService.clearCartAfterOrderPlaced(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_ALREADY_EMPTY, result.getMessage());
    verify(cartRepository, times(0)).deleteAll(any());
  }

  @Test
  void getCartItemsByUserIdAndRestaurantIdSuccess() {
    when(userClient.getUserById(1)).thenReturn(userOutDto);
    when(restaurantClient.getRestaurantById(1)).thenReturn(null);
    when(cartRepository.findByUserIdAndRestaurantId(1, 1))
      .thenReturn(Collections.singletonList(cart));

    List<Cart> result = cartService.getCartItemsByUserIdAndRestaurantId(1, 1);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(cart, result.get(0));
  }

  @Test
  void getCartByUserIdSuccess() {
    when(userClient.getUserById(1)).thenReturn(userOutDto);
    when(cartRepository.findByUserId(1)).thenReturn(Collections.singletonList(cart));

    List<Cart> result = cartService.getCartByUserId(1);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(cart, result.get(0));
  }
}
