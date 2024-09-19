package com.orders.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.orders.constants.OrderConstants;
import com.orders.converter.DtoConversion;
import com.orders.dto.AddressOutDto;
import com.orders.dto.CartItemDto;
import com.orders.dto.FoodItemOutDto;
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
import com.orders.service.OrderService;
import com.orders.service.RestaurantFeignClient;
import com.orders.service.UserFeignClient;
import com.orders.utils.OrderStatus;
import com.orders.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
  /**
   * An instance of {@link ObjectMapper} used for JSON processing.
   * The {@link ObjectMapper} is provided by the Jackson library and is used to convert Java objects to JSON
   * and vice versa. It is a powerful utility for serializing and deserializing data.
   */
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Service layer dependency for restaurant-related operations.
   */
  @Autowired
  private RestaurantFeignClient restaurantClient;

  /**
   * Service layer dependency for order-related operations.
   */
  @Autowired
  private OrderRepository orderRepository;

  /**
   * Service layer dependency for cart-related operations.
   */
  @Autowired
  private CartService cartService;

  /**
   * Service layer dependency for user-related operations.
   */
  @Autowired
  private UserFeignClient userClient;

  /**
   * Places an order based on the provided order details.
   * Validates user role, address, and cart items. Calculates total price and updates wallet balance.
   * Creates an order and clears the cart after order placement.
   *
   * @param orderInDto DTO containing the details of the order to be placed
   * @return A MessageOutDto indicating the success or failure of the operation
   */
  @Override
  public MessageOutDto placeOrder(final OrderInDto orderInDto) {
    log.info("Placing order for userId: {}", orderInDto.getUserId());

    UserOutDto user = getUser(orderInDto.getUserId());
    validateUserRole(user);

    List<AddressOutDto> addresses = getAddresses(orderInDto.getUserId());
    validateAddress(orderInDto.getDeliveryAddressId(), addresses);

    List<Cart> cartItems = getCartItems(orderInDto);
    validateCartItems(cartItems, orderInDto.getCartItems());

    BigDecimal totalPrice = calculateTotalPrice(orderInDto.getCartItems());
    validateWalletBalance(user, totalPrice);

    updateWalletBalance(user, totalPrice);

    List<CartItemDto> cartItemDtos = cartItems.stream()
      .map(cart -> new CartItemDto(cart.getFoodItemId(), cart.getQuantity(), cart.getPrice()))
      .collect(Collectors.toList());

    Order order = createOrder(orderInDto, totalPrice, cartItemDtos);
    orderRepository.save(order);

    cartService.clearCartAfterOrderPlaced(orderInDto.getUserId(), orderInDto.getRestaurantId());
    log.info("Order placed successfully for userId: {}, orderId: {}", orderInDto.getUserId(), order.getId());
    return new MessageOutDto(OrderConstants.ORDER_PLACED_SUCCESSFULLY);
  }
  /**
   * Retrieves a user by their ID.
   *
   * @param userId The ID of the user to be fetched
   * @return UserOutDto representing the fetched user
   */

  private UserOutDto getUser(final Integer userId) {
    log.debug("Fetching user with userId: {}", userId);
    return userClient.getUserById(userId);
  }
  /**
   * Validates the role of the user.
   * Throws an exception if the user is not a customer.
   *
   * @param user The user to be validated
   */
  private void validateUserRole(final UserOutDto user) {
    log.debug("Validating user role for userId: {}", user.getId());
    if (!user.getUserRole().equals(UserRole.CUSTOMER)) {
      throw new AccessDeniedException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
  }
  /**
   * Retrieves a list of addresses for a given user.
   *
   * @param userId The ID of the user whose addresses are to be fetched
   * @return List of AddressOutDto representing the addresses
   */
  private List<AddressOutDto> getAddresses(final Integer userId) {
    log.info("Fetching addresses for user ID: {}", userId);
    return userClient.getAddressesByUserId(userId);
  }
  /**
   * Validates the delivery address against the user's addresses.
   * Throws an exception if the address is not found.
   *
   * @param deliveryAddressId The ID of the delivery address
   * @param addresses The list of addresses associated with the user
   */
  private void validateAddress(final Integer deliveryAddressId, final List<AddressOutDto> addresses) {
    boolean validAddress = addresses.stream()
      .anyMatch(address -> address.getId().equals(deliveryAddressId));
    if (!validAddress) {
      log.error("Delivery address ID: {} not found for user", deliveryAddressId);
      throw new ResourceNotFoundException(OrderConstants.ADDRESS_NOT_FOUND);
    }
  }
  /**
   * Retrieves cart items for a user and restaurant.
   * Throws an exception if the cart is empty.
   *
   * @param orderInDto DTO containing order details
   * @return List of Cart entities representing the cart items
   */
  public List<Cart> getCartItems(final OrderInDto orderInDto) {
    log.info("Fetching cart items for user ID: {} and restaurant ID: {}", orderInDto.getUserId(),
      orderInDto.getRestaurantId());

    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(),
      orderInDto.getRestaurantId());

    if (cartItems.isEmpty()) {
      log.error("No items found in the cart for user ID: {} and restaurant ID: {}",
        orderInDto.getUserId(), orderInDto.getRestaurantId());
      throw new ResourceConflictException(OrderConstants.INVALID_CART_ITEMS);
    }
    return cartItems;
  }

  /**
   * Validates that the cart items match the provided cart items DTO.
   * Logs a warning if some items do not match but proceeds without error.
   *
   * @param cartItems List of Cart entities representing the items in the cart
   * @param cartItemsDto List of CartItemDto representing the cart items to be validated
   */
  public void validateCartItems(final List<Cart> cartItems, final List<CartItemDto> cartItemsDto) {
    boolean allItemsValid = cartItemsDto.stream()
      .allMatch(cartItemDto -> cartItems.stream()
        .anyMatch(cartItem -> cartItem.getFoodItemId().equals(cartItemDto.getFoodItemId())
          && cartItem.getQuantity().equals(cartItemDto.getQuantity())
          && cartItem.getPrice().compareTo(cartItemDto.getPrice()) == 0));

    if (allItemsValid) {
      log.info("All cart items validated successfully.");
    } else {
      log.error("Some cart items do not match but proceeding without errors.");
    }
  }
  /**
   * Calculates the total price of the cart items.
   *
   * @param cartItems List of CartItemDto representing the items in the cart
   * @return BigDecimal representing the total price
   */
  public BigDecimal calculateTotalPrice(final List<CartItemDto> cartItems) {
    BigDecimal totalPrice = cartItems.stream()
      .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    log.info("Total price calculated: {}", totalPrice);
    return totalPrice;
  }
  /**
   * Validates that the user has sufficient wallet balance to place the order.
   * Throws an exception if the balance is insufficient.
   *
   * @param user The user whose wallet balance is to be validated
   * @param totalPrice The total price of the order
   */
  public void validateWalletBalance(final UserOutDto user, final BigDecimal totalPrice) {
    if (user.getWalletBalance().compareTo(totalPrice) < 0) {
      log.error("Insufficient balance in wallet. Required: {}, Available: {}", totalPrice, user.getWalletBalance());
      throw new ResourceConflictException(OrderConstants.INSUFFICIENT_BALANCE);
    }
    log.info("Wallet balance validated successfully.");
  }
  /**
   * Updates the user's wallet balance by deducting the order total.
   *
   * @param user The user whose wallet balance is to be updated
   * @param totalPrice The total price to be deducted
   */
  private void updateWalletBalance(final UserOutDto user, final BigDecimal totalPrice) {
    userClient.updateWalletBalance(user.getId(), totalPrice.negate());
    log.info("Wallet balance updated for userId: {}. Amount deducted: {}", user.getId(), totalPrice);
  }

  /**
   * Creates an order entity from the provided order details and cart items.
   *
   * @param orderInDto DTO containing order details
   * @param totalPrice The total price of the order
   * @param validCartItems List of CartItemDto representing the valid cart items
   * @return Order entity with the specified details
   */
  public Order createOrder(final OrderInDto orderInDto, final BigDecimal totalPrice, final List<CartItemDto> validCartItems) {
    Order order = DtoConversion.convertOrderInDtoToOrder(orderInDto);
    order.setRestaurantId(orderInDto.getRestaurantId());
    order.setOrderStatus(OrderStatus.PLACED);
    order.setOrderTime(LocalDateTime.now());
    try {
      order.setCartItems(objectMapper.writeValueAsString(validCartItems));
    } catch (JsonProcessingException e) {
      log.error("Failed to process cart items", e);
      throw new RuntimeException("Failed to process cart items", e);
    }
    order.setTotalPrice(totalPrice);
    log.info("Order created with ID: {}", order.getId());
    return order;
  }

  /**
   * Cancels an existing order if it is within the allowed cancellation time.
   * Updates the order status to CANCELLED and refunds the total price to the user's wallet.
   *
   * @param orderId The ID of the order to be cancelled
   * @return A MessageOutDto indicating the success of the cancellation
   * @throws ResourceNotFoundException if the order with the given ID is not found
   * @throws ResourceConflictException if the cancellation time has expired (more than 30 seconds since order creation)
   */
  @Override
  public MessageOutDto cancelOrder(final Integer orderId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(OrderConstants.ORDER_NOT_FOUND));

    if (order.getOrderTime().plusSeconds(30).isBefore(LocalDateTime.now())) {
      log.error("Order cancellation time exceeded for orderId: {}", orderId);
      throw new ResourceConflictException(OrderConstants.ORDER_CANCELLED_FAILURE);
    }

    userClient.updateWalletBalance(order.getUserId(), order.getTotalPrice());

    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);

    log.info("Order cancelled successfully with ID: {}", orderId);
    return new MessageOutDto(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY);
  }
  /**
   * Retrieves all orders associated with a specific user.
   * Validates the user's role and fetches orders based on the user's ID.
   *
   * @param userId The ID of the user whose orders are to be retrieved
   * @return A list of OrderOutDto representing the orders for the specified user
   * @throws ResourceNotFoundException if the user with the given ID is not found
   * @throws AccessDeniedException if the user does not have the appropriate role (not CUSTOMER)
   */
  @Override
  public List<OrderOutDto> getOrdersByUserId(final Integer userId) {
    UserOutDto user = getUser(userId);
    validateUserRole(user);
    List<Order> orders = orderRepository.findByUserId(userId);
    List<OrderOutDto> orderOutDtos = orders.stream()
      .map(this::convertOrderToOrderOutDto)
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for userId: {}", orderOutDtos.size(), userId);
    return orderOutDtos;
  }

  /**
   * Retrieves all orders associated with a specific restaurant.
   * Fetches orders based on the restaurant's ID.
   *
   * @param restaurantId The ID of the restaurant whose orders are to be retrieved
   * @return A list of OrderOutDto representing the orders for the specified restaurant
   * @throws ResourceNotFoundException if the restaurant with the given ID is not found
   */
  @Override
  public List<OrderOutDto> getOrdersByRestaurantId(final Integer restaurantId) {
    FoodItemOutDto restaurant = restaurantClient.getRestaurantById(restaurantId);

    List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
    List<OrderOutDto> orderOutDtos = orders.stream()
      .map(this::convertOrderToOrderOutDto)
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for restaurantId: {}", orderOutDtos.size(), restaurantId);
    return orderOutDtos;
  }
  /**
   * Marks an order as completed and updates its status in the repository.
   *
   * @param orderId The ID of the order to be marked as completed
   * @param userId The ID of the user performing the action
   * @return A MessageOutDto indicating the success of the operation
   * @throws ResourceNotFoundException if the order with the given ID is not found
   */
  @Override
  public MessageOutDto markOrderAsCompleted(final Integer orderId, final Integer userId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException(OrderConstants.ORDER_NOT_FOUND));

    order.setOrderStatus(OrderStatus.COMPLETED);
    orderRepository.save(order);

    log.info("Order marked as completed with ID: {}", orderId);
    return new MessageOutDto(OrderConstants.ORDER_COMPLETED_SUCCESSFULLY);
  }

  /**
   * Converts an Order entity to an OrderOutDto.
   *
   * @param order The Order entity to be converted
   * @return An OrderOutDto representing the converted order
   */
  public OrderOutDto convertOrderToOrderOutDto(final Order order) {
    OrderOutDto dto = DtoConversion.convertOrderToOrderOutDto(order);
    try {
      List<CartItemDto> cartItems = objectMapper.readValue(order.getCartItems(),
        TypeFactory.defaultInstance().constructCollectionType(List.class, CartItemDto.class));
      dto.setCartItems(cartItems);
    } catch (JsonProcessingException e) {
      log.error("Failed to process cart items for order ID: {}", order.getId(), e);
      throw new RuntimeException("Failed to process cart items", e);
    }
    return dto;
  }
}
