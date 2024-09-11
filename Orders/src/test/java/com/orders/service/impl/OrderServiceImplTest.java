package com.orders.service.impl;

import com.orders.constants.OrderConstants;
import com.orders.dto.OrderInDto;
import com.orders.dto.AddressOutDto;
import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderOutDto;
import com.orders.dto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.exception.AddressNotFoundException;
import com.orders.exception.CartNotFoundException;
import com.orders.exception.CustomerNotFoundException;
import com.orders.exception.OrderNotFoundException;
import com.orders.exception.OrderUpdateException;
import com.orders.repositories.OrderRepository;
import com.orders.service.AddressFeignClient;
import com.orders.service.CartService;
import com.orders.service.RestaurantFeignClient;
import com.orders.service.UserFeignClient;
import com.orders.utils.OrderStatus;
import com.orders.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

  @InjectMocks
  private OrderServiceImpl orderService;

  @Mock
  private RestaurantFeignClient restaurantClient;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private CartService cartService;

  @Mock
  private UserFeignClient userClient;

  @Mock
  private AddressFeignClient addressClient;

  private OrderInDto orderInDto;
  private UserOutDto userOutDto;
  private List<Cart> cartItems;
  private List<AddressOutDto> addresses;
  private Order order;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    orderInDto = new OrderInDto(1, 2, 3, new ArrayList<>());
    userOutDto = new UserOutDto();
    userOutDto.setId(1);
    userOutDto.setWalletBalance(BigDecimal.valueOf(500));
    userOutDto.setUserRole(UserRole.CUSTOMER);

    cartItems = new ArrayList<>();
    Cart cart = new Cart();
    cart.setUserId(1);
    cart.setFoodItemId(1);
    cart.setQuantity(2);
    cart.setPrice(BigDecimal.valueOf(20));
    cart.setRestaurantId(3);
    cartItems.add(cart);

    addresses = new ArrayList<>();
    AddressOutDto address = new AddressOutDto();
    address.setId(2);
    addresses.add(address);

    order = new Order();
    order.setId(1);
    order.setUserId(1);
    order.setRestaurantId(3);
    order.setOrderTime(LocalDateTime.now());
  }

  @Test
  void placeOrder_successful() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(addressClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(cartItems);
    when(orderRepository.save(any(Order.class))).thenReturn(order);

    MessageOutDto result = orderService.placeOrder(orderInDto);

    assertNotNull(result);
    assertEquals(OrderConstants.ORDER_PLACED_SUCCESSFULLY, result.getMessage());
    verify(cartService, times(1)).clearCartAfterOrderPlaced(orderInDto.getUserId(), orderInDto.getRestaurantId());
  }

  @Test
  void placeOrder_cartNotFound() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(addressClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(new ArrayList<>());

    assertThrows(CartNotFoundException.class, () -> orderService.placeOrder(orderInDto));
  }

  @Test
  void placeOrder_addressNotFound() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(addressClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(new ArrayList<>());

    assertThrows(AddressNotFoundException.class, () -> orderService.placeOrder(orderInDto));
  }

  @Test
  void placeOrder_insufficientBalance() {
    userOutDto.setWalletBalance(BigDecimal.valueOf(10));

    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(addressClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(cartItems);
  }

  @Test
  void placeOrder_invalidCartItems() {
    cartItems.get(0).setQuantity(1);

    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(addressClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(cartItems);

  }

  @Test
  void placeOrder_nonCustomerRole() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);

    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);

    assertThrows(CustomerNotFoundException.class, () -> orderService.placeOrder(orderInDto));
  }

  @Test
  void cancelOrder_successful() {
    order.setOrderTime(LocalDateTime.now().minusSeconds(20));
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

    MessageOutDto result = orderService.cancelOrder(order.getId());

    assertNotNull(result);
    assertEquals(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY, result.getMessage());
    verify(userClient, times(1)).updateWalletBalance(order.getUserId(), order.getTotalPrice());
  }

  @Test
  void cancelOrder_after30Seconds() {
    order.setOrderTime(LocalDateTime.now().minusSeconds(40));
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

    assertThrows(OrderUpdateException.class, () -> orderService.cancelOrder(order.getId()));
  }

  @Test
  void getOrdersByUserId_successful() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(orderRepository.findByUserId(orderInDto.getUserId())).thenReturn(Collections.singletonList(order));

    List<OrderOutDto> result = orderService.getOrdersByUserId(orderInDto.getUserId());

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
  }

  @Test
  void markOrderAsCompleted_successful() {
    UserOutDto userOutDto = new UserOutDto();
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);
    userOutDto.setId(1);

    Order order = new Order();
    order.setId(1);
    order.setOrderStatus(OrderStatus.COMPLETED);

    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    when(userClient.getUserById(userOutDto.getId())).thenReturn(userOutDto);

    MessageOutDto result = orderService.markOrderAsCompleted(order.getId(), userOutDto.getId());

      }

  @Test
  void markOrderAsCompleted_notRestaurantOwner() {
    userOutDto.setUserRole(UserRole.CUSTOMER);
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    when(userClient.getUserById(order.getUserId())).thenReturn(userOutDto);

    assertThrows(OrderUpdateException.class, () -> orderService.markOrderAsCompleted(order.getId(), userOutDto.getId()));
  }

  @Test
  void markOrderAsCompleted_orderNotFound() {
    when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

    assertThrows(OrderNotFoundException.class, () -> orderService.markOrderAsCompleted(order.getId(), userOutDto.getId()));
  }
}
