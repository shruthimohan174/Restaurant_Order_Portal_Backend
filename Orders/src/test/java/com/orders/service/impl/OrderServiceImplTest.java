package com.orders.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.constants.OrderConstants;
import com.orders.dto.AddressOutDto;
import com.orders.dto.CartItemDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orders.dto.FoodItemOutDto;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;
import com.orders.dto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.exception.AccessDeniedException;
import com.orders.exception.ResourceConflictException;
import com.orders.exception.ResourceNotFoundException;
import com.orders.repositories.OrderRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link OrderServiceImpl} class.
 * This class contains tests for the functionalities of the OrderServiceImpl,
 * including order placement, cancellation, and retrieval.
 */
class OrderServiceImplTest {

  /**
   * The service under test.
   */
  @InjectMocks
  private OrderServiceImpl orderService;

  /**
   * Mocked RestaurantFeignClient used to interact with restaurant services.
   */
  @Mock
  private RestaurantFeignClient restaurantClient;

  /**
   * Mocked OrderRepository for performing CRUD operations on orders.
   */
  @Mock
  private OrderRepository orderRepository;

  /**
   * Mocked CartService for managing cart operations.
   */
  @Mock
  private CartService cartService;

  /**
   * Mocked UserFeignClient for interacting with user services.
   */
  @Mock
  private UserFeignClient userClient;

  /**
   * Mocked ObjectMapper for JSON processing.
   */
  @Mock
  private ObjectMapper objectMapper;

  /**
   * Test data for OrderInDto used in order placement scenarios.
   */
  private OrderInDto orderInDto;

  /**
   * Test data for UserOutDto representing user details.
   */
  private UserOutDto userOutDto;

  /**
   * Test data for Cart items used in order placement scenarios.
   */
  private List<Cart> cartItems;

  /**
   * Test data for AddressOutDto representing user addresses.
   */
  private List<AddressOutDto> addresses;

  /**
   * Test data for Order entity used in various test scenarios.
   */
  private Order order;

  /**
   * Sets up the test environment by initializing mocks and test data.
   */
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
  void placeOrderSuccessful() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(userClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(cartItems);
    when(orderRepository.save(any(Order.class))).thenReturn(order);

    MessageOutDto result = orderService.placeOrder(orderInDto);

    assertNotNull(result);
    assertEquals(OrderConstants.ORDER_PLACED_SUCCESSFULLY, result.getMessage());
    verify(cartService, times(1)).clearCartAfterOrderPlaced(orderInDto.getUserId(), orderInDto.getRestaurantId());
  }

  @Test
  void placeOrderCartNotFound() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(userClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(new ArrayList<>());
  }

  @Test
  void placeOrderAddressNotFound() {
    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(userClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(new ArrayList<>());

    assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(orderInDto));
  }

  @Test
  void validateWalletBalanceInsufficientBalance() {
    userOutDto.setWalletBalance(BigDecimal.valueOf(10));
    BigDecimal totalPrice = BigDecimal.valueOf(20);

    ResourceConflictException exception = assertThrows(
      ResourceConflictException.class,
      () -> orderService.validateWalletBalance(userOutDto, totalPrice)
    );

    assertEquals(OrderConstants.INSUFFICIENT_BALANCE, exception.getMessage());
  }


  @Test
  void placeOrderInvalidCartItems() {
    cartItems.get(0).setQuantity(1);

    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
    when(userClient.getAddressesByUserId(orderInDto.getUserId())).thenReturn(addresses);
    when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(cartItems);

  }

  @Test
  void placeOrderNonCustomerRole() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);

    when(userClient.getUserById(orderInDto.getUserId())).thenReturn(userOutDto);
  }

  @Test
  void cancelOrderSuccessful() {
    order.setOrderTime(LocalDateTime.now().minusSeconds(20));
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

    MessageOutDto result = orderService.cancelOrder(order.getId());

    assertNotNull(result);
    assertEquals(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY, result.getMessage());
    verify(userClient, times(1)).updateWalletBalance(order.getUserId(), order.getTotalPrice());
  }

  @Test
  void testCalculateTotalPrice() {
    List<CartItemDto> cartItems = Arrays.asList(
      new CartItemDto(1, 2, BigDecimal.valueOf(10)),
      new CartItemDto(2, 1, BigDecimal.valueOf(20))
    );

    BigDecimal totalPrice = orderService.calculateTotalPrice(cartItems);

    assertEquals(BigDecimal.valueOf(40), totalPrice);
  }

  @Test
  void testCancelOrder() {
    Integer orderId = 1;
    Order order = new Order();
    order.setId(orderId);
    order.setOrderTime(LocalDateTime.now().minusSeconds(20));
    order.setTotalPrice(BigDecimal.valueOf(100));
    order.setOrderStatus(OrderStatus.PLACED);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    MessageOutDto response = orderService.cancelOrder(orderId);

    assertEquals(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY, response.getMessage());
    verify(orderRepository).save(order);
  }

  @Test
  void cancelOrderAfter30Seconds() {
    order.setOrderTime(LocalDateTime.now().minusSeconds(40));
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

    assertThrows(ResourceConflictException.class, () -> orderService.cancelOrder(order.getId()));
  }

  @Test
  void markOrderAsCompletedSuccessful() {
    userOutDto.setUserRole(UserRole.RESTAURANT_OWNER);
    order.setRestaurantId(userOutDto.getId());

    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    when(userClient.getUserById(userOutDto.getId())).thenReturn(userOutDto);

    MessageOutDto result = orderService.markOrderAsCompleted(order.getId(), userOutDto.getId());

    assertNotNull(result);
    assertEquals(OrderConstants.ORDER_COMPLETED_SUCCESSFULLY, result.getMessage());
  }

  @Test
  void markOrderAsCompletedNotRestaurantOwner() {
    userOutDto.setUserRole(UserRole.CUSTOMER);
    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    when(userClient.getUserById(order.getUserId())).thenReturn(userOutDto);
  }

  @Test
  void markOrderAsCompletedOrderNotFound() {
    when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> orderService.markOrderAsCompleted(order.getId(), userOutDto.getId()));
  }

  @Test
  void testValidateOrderDetails() {
    List<CartItemDto> cartItems = Arrays.asList(
      new CartItemDto(1, 2, BigDecimal.valueOf(10)),
      new CartItemDto(2, 3, BigDecimal.valueOf(5))
    );
  }

  @Test
  void testValidateCartItemsAllItemsValid() {
    List<Cart> cartItems = Arrays.asList(
      new Cart(1, 1, 2, 1, BigDecimal.valueOf(20), 3),
      new Cart(2, 2, 1, 1, BigDecimal.valueOf(15), 3)
    );

    List<CartItemDto> cartItemsDto = Arrays.asList(
      new CartItemDto(1, 2, BigDecimal.valueOf(20)),
      new CartItemDto(2, 1, BigDecimal.valueOf(15))
    );

    orderService.validateCartItems(cartItems, cartItemsDto);
  }

  @Test
  void testValidateCartItemSomeItemsInvalid() {
    List<Cart> cartItems = Collections.singletonList(
      new Cart(1, 1, 2, 1, BigDecimal.valueOf(20), 3)
    );

    List<CartItemDto> cartItemsDto = Arrays.asList(
      new CartItemDto(1, 2, BigDecimal.valueOf(20)),
      new CartItemDto(2, 1, BigDecimal.valueOf(15))
    );

    orderService.validateCartItems(cartItems, cartItemsDto);

  }

  @Test
  void testValidateCartItemsemptyCart() {
    List<Cart> cartItems = new ArrayList<>();
    List<CartItemDto> cartItemsDto = Collections.singletonList(
      new CartItemDto(1, 2, BigDecimal.valueOf(20))
    );

    orderService.validateCartItems(cartItems, cartItemsDto);
  }

  @Test
  void testConvertOrderToDto() throws JsonProcessingException {
    OrderOutDto expectedDto = new OrderOutDto();
    expectedDto.setId(order.getId());
    expectedDto.setUserId(order.getUserId());
    expectedDto.setRestaurantId(order.getRestaurantId());
    expectedDto.setOrderTime(order.getOrderTime());
  }

  @Test
  void testConvertOrderToOrderOutDtoSuccess() throws JsonProcessingException {
    Order order = new Order();
    order.setId(1);
    order.setUserId(1);
    order.setRestaurantId(1);
    order.setOrderStatus(OrderStatus.PLACED);
    order.setOrderTime(LocalDateTime.now());
    order.setTotalPrice(BigDecimal.valueOf(100));
    order.setCartItems("[{\"foodItemId\":1,\"quantity\":2,\"price\":50}]");

    List<CartItemDto> cartItems = Collections.singletonList(new CartItemDto(1, 2, BigDecimal.valueOf(50)));

    OrderOutDto result = orderService.convertOrderToOrderOutDto(order);

    assertNotNull(result);
    assertEquals(order.getId(), result.getId());
    assertEquals(order.getUserId(), result.getUserId());
    assertEquals(order.getRestaurantId(), result.getRestaurantId());
    assertEquals(order.getOrderStatus(), result.getOrderStatus());
    assertEquals(order.getOrderTime(), result.getOrderTime());
    assertEquals(order.getTotalPrice(), result.getTotalPrice());
    assertEquals(cartItems, result.getCartItems());
  }

  @Test
  void testConvertOrderToOrderOutDtoJsonProcessingException() throws JsonProcessingException {
    Order order = new Order();
    order.setId(1);
    order.setCartItems("invalid json");

    assertThrows(RuntimeException.class, () -> orderService.convertOrderToOrderOutDto(order));
  }

  @Test
  void getCartItemsThrowsResourceConflictException() {
   when(cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId()))
      .thenReturn(Collections.emptyList());

    ResourceConflictException exception = assertThrows(
      ResourceConflictException.class,
      () -> orderService.getCartItems(orderInDto)
    );
    assertEquals(OrderConstants.INVALID_CART_ITEMS, exception.getMessage());

    verify(cartService, times(1)).getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId());
  }

  @Test
  void testGetOrdersByUserId() throws JsonProcessingException {
    Integer userId = 1;
    UserOutDto user = new UserOutDto();
    user.setId(userId);
    user.setUserRole(UserRole.CUSTOMER);

    List<Order> orders = Arrays.asList(
      new Order(1, userId, 1, OrderStatus.PLACED, "[]", LocalDateTime.now(), BigDecimal.valueOf(100), 1),
      new Order(2, userId, 2, OrderStatus.COMPLETED, "[]", LocalDateTime.now(), BigDecimal.valueOf(150), 2)
    );

    List<OrderOutDto> expectedOrderOutDtos = orders.stream()
      .map(order -> {
        OrderOutDto dto = new OrderOutDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setOrderTime(order.getOrderTime());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setRestaurantId(order.getRestaurantId());
        return dto;
      })
      .collect(Collectors.toList());

    when(userClient.getUserById(userId)).thenReturn(user);
    when(orderRepository.findByUserId(userId)).thenReturn(orders);
    when(objectMapper.readValue(anyString(), eq(new TypeReference<List<CartItemDto>>() { })))
      .thenReturn(Collections.emptyList());

    List<OrderOutDto> result = orderService.getOrdersByUserId(userId);

    assertNotNull(result);
    assertEquals(expectedOrderOutDtos.size(), result.size());
    for (int i = 0; i < expectedOrderOutDtos.size(); i++) {
      assertEquals(expectedOrderOutDtos.get(i).getId(), result.get(i).getId());
      assertEquals(expectedOrderOutDtos.get(i).getUserId(), result.get(i).getUserId());
      assertEquals(expectedOrderOutDtos.get(i).getOrderStatus(), result.get(i).getOrderStatus());
      assertEquals(expectedOrderOutDtos.get(i).getOrderTime(), result.get(i).getOrderTime());
      assertEquals(expectedOrderOutDtos.get(i).getTotalPrice(), result.get(i).getTotalPrice());
      assertEquals(expectedOrderOutDtos.get(i).getRestaurantId(), result.get(i).getRestaurantId());
    }

    verify(userClient, times(1)).getUserById(userId);
    verify(orderRepository, times(1)).findByUserId(userId);
  }

  @Test
  void testGetOrdersByUserIdInvalidRole() {
    Integer userId = 1;
    UserOutDto user = new UserOutDto();
    user.setId(userId);
    user.setUserRole(UserRole.RESTAURANT_OWNER);

    when(userClient.getUserById(userId)).thenReturn(user);

    assertThrows(AccessDeniedException.class, () -> orderService.getOrdersByUserId(userId));

    verify(userClient, times(1)).getUserById(userId);
    verify(orderRepository, never()).findByUserId(userId);
  }

  @Test
  void testGetOrdersByRestaurantId() throws JsonProcessingException {
    Integer restaurantId = 1;
    FoodItemOutDto restaurant = new FoodItemOutDto();
    restaurant.setId(restaurantId);

    List<Order> orders = Arrays.asList(
      new Order(1, 1, 1, OrderStatus.PLACED, "[]", LocalDateTime.now(), BigDecimal.valueOf(100), restaurantId),
      new Order(2, 2, 2, OrderStatus.COMPLETED, "[]", LocalDateTime.now(), BigDecimal.valueOf(150), restaurantId)
    );

    List<OrderOutDto> expectedOrderOutDtos = orders.stream()
      .map(order -> {
        OrderOutDto dto = new OrderOutDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setOrderTime(order.getOrderTime());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setRestaurantId(order.getRestaurantId());
        return dto;
      })
      .collect(Collectors.toList());

    when(restaurantClient.getRestaurantById(restaurantId)).thenReturn(restaurant);
    when(orderRepository.findByRestaurantId(restaurantId)).thenReturn(orders);
    when(objectMapper.readValue(anyString(), eq(new TypeReference<List<CartItemDto>>() { })))
      .thenReturn(Collections.emptyList());

    List<OrderOutDto> result = orderService.getOrdersByRestaurantId(restaurantId);

    assertNotNull(result);
    assertEquals(expectedOrderOutDtos.size(), result.size());
    for (int i = 0; i < expectedOrderOutDtos.size(); i++) {
      assertEquals(expectedOrderOutDtos.get(i).getId(), result.get(i).getId());
      assertEquals(expectedOrderOutDtos.get(i).getUserId(), result.get(i).getUserId());
      assertEquals(expectedOrderOutDtos.get(i).getOrderStatus(), result.get(i).getOrderStatus());
      assertEquals(expectedOrderOutDtos.get(i).getOrderTime(), result.get(i).getOrderTime());
      assertEquals(expectedOrderOutDtos.get(i).getTotalPrice(), result.get(i).getTotalPrice());
      assertEquals(expectedOrderOutDtos.get(i).getRestaurantId(), result.get(i).getRestaurantId());
    }

    verify(restaurantClient, times(1)).getRestaurantById(restaurantId);
    verify(orderRepository, times(1)).findByRestaurantId(restaurantId);
  }

}
