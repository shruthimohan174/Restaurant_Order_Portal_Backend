package com.orders.service.impl;

import com.orders.constants.OrderConstants;
import com.orders.dto.CartInDto;
import com.orders.dto.FoodItemOutDto;
import com.orders.dto.MessageOutDto;
import com.orders.dto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.exception.CartNotFoundException;
import com.orders.exception.CustomerNotFoundException;
import com.orders.exception.PriceMismatchException;
import com.orders.repositories.CartRepository;
import com.orders.service.FoodItemFeignClient;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

  @Mock
  private UserFeignClient userClient;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private RestaurantFeignClient restaurantClient;

  @Mock
  private FoodItemFeignClient foodItemClient;

  @InjectMocks
  private CartServiceImpl cartService;

  private CartInDto cartInDto;
  private UserOutDto userOutDto;
  private FoodItemOutDto foodItemOutDto;
  private Cart cart;

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
  void addItemToCart_Success() {
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(foodItemClient.getFoodItemById(cartInDto.getFoodItemId())).thenReturn(foodItemOutDto);
    when(cartRepository.findByUserIdAndFoodItemIdAndRestaurantId(anyInt(), anyInt(), anyInt()))
      .thenReturn(Optional.empty());
    when(cartRepository.save(any(Cart.class))).thenReturn(cart);

    MessageOutDto result = cartService.addItemToCart(cartInDto);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_ADDED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).save(any(Cart.class));
  }

  @Test
  void addItemToCart_ThrowsCustomerNotFoundException() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);

    assertThrows(CustomerNotFoundException.class, () -> cartService.addItemToCart(cartInDto));
    verify(userClient, times(1)).getUserById(cartInDto.getUserId());
  }

  @Test
  void addItemToCart_ThrowsPriceMismatchException() {
    foodItemOutDto.setPrice(new BigDecimal("12.00"));
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(foodItemClient.getFoodItemById(cartInDto.getFoodItemId())).thenReturn(foodItemOutDto);

    assertThrows(PriceMismatchException.class, () -> cartService.addItemToCart(cartInDto));
    verify(foodItemClient, times(1)).getFoodItemById(cartInDto.getFoodItemId());
  }

  @Test
  void updateCartItemQuantity_Success() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.updateCartItemQuantity(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_UPDATED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).save(cart);
  }

  @Test
  void updateCartItemQuantity_ItemRemoved() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.updateCartItemQuantity(1, -1);

    assertNotNull(result);
    assertEquals(OrderConstants.ITEM_REMOVED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteById(1);
  }

  @Test
  void updateCartItemQuantity_ThrowsCartNotFoundException() {
    when(cartRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(CartNotFoundException.class, () -> cartService.updateCartItemQuantity(1, 1));
  }

  @Test
  void removeItemFromCart_Success() {
    when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

    MessageOutDto result = cartService.removeItemFromCart(1);

    assertNotNull(result);
    assertEquals(OrderConstants.ITEM_REMOVED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteById(1);
  }

  @Test
  void clearCartAfterOrderPlaced_Success() {
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(restaurantClient.getRestaurantById(cartInDto.getRestaurantId())).thenReturn(foodItemOutDto);
    when(cartRepository.findByUserIdAndRestaurantId(1, 1)).thenReturn(Collections.singletonList(cart));

    MessageOutDto result = cartService.clearCartAfterOrderPlaced(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_DELETED_SUCCESSFULLY, result.getMessage());
    verify(cartRepository, times(1)).deleteAll(anyList());
  }

  @Test
  void clearCartAfterOrderPlaced_ThrowsCustomerNotFoundException() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);

    assertThrows(CustomerNotFoundException.class, () -> cartService.clearCartAfterOrderPlaced(1, 1));
  }

  @Test
  void clearCartAfterOrderPlaced_CartAlreadyEmpty() {
    when(userClient.getUserById(cartInDto.getUserId())).thenReturn(userOutDto);
    when(cartRepository.findByUserIdAndRestaurantId(1, 1)).thenReturn(Collections.emptyList());

    MessageOutDto result = cartService.clearCartAfterOrderPlaced(1, 1);

    assertNotNull(result);
    assertEquals(OrderConstants.CART_ALREADY_EMPTY, result.getMessage());
  }
}
