package com.orders.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.constants.OrderConstants;
import com.orders.dtoconversion.DtoConversion;
import com.orders.dto.OrderInDto;
import com.orders.dto.AddressOutDto;
import com.orders.dto.FoodItemOutDto;
import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderOutDto;
import com.orders.dto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.exception.AddressNotFoundException;
import com.orders.exception.CartNotFoundException;
import com.orders.exception.CustomerNotFoundException;
import com.orders.exception.InsufficientBalanceException;
import com.orders.exception.OrderNotFoundException;
import com.orders.exception.OrderUpdateException;
import com.orders.repositories.OrderRepository;
import com.orders.service.AddressFeignClient;
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

  @Autowired
  private RestaurantFeignClient restaurantClient;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CartService cartService;

  @Autowired
  private UserFeignClient userClient;

  @Autowired
  private AddressFeignClient addressClient;

  /**
   * Places an order for a user by validating cart items, wallet balance, and delivery address.
   *
   * @param orderInDto the order input DTO containing order details
   * @return a MessageOutDto containing a success message if the order is placed successfully
   */
  @Override
  public MessageOutDto placeOrder(OrderInDto orderInDto) {
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
    Order order = createOrder(orderInDto, totalPrice, cartItems);

    orderRepository.save(order);
    cartService.clearCartAfterOrderPlaced(orderInDto.getUserId(), orderInDto.getRestaurantId());
    log.info("Order placed successfully for userId: {}, orderId: {}", orderInDto.getUserId(), order.getId());
    return new MessageOutDto(OrderConstants.ORDER_PLACED_SUCCESSFULLY);
  }

  /**
   * Retrieves the user details based on user ID.
   *
   * @param userId The ID of the user.
   * @return UserOutDto containing user details.
   */
  private UserOutDto getUser(Integer userId) {
    log.debug("Fetching user with userId: {}", userId);
    return userClient.getUserById(userId);
  }

  /**
   * Validates if the user is a customer.
   *
   * @param user The user to validate.
   */
  private void validateUserRole(UserOutDto user) {
    log.debug("Validating user role for userId: {}", user.getId());
    if (user.getUserRole() != UserRole.CUSTOMER) {
      throw new CustomerNotFoundException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
  }

  /**
   * Fetches the list of addresses associated with the user.
   *
   * @param userId The ID of the user.
   * @return List of AddressOutDto objects containing the user's addresses.
   */
  private List<AddressOutDto> getAddresses(Integer userId) {
    log.info("Fetching addresses for user ID: {}", userId);
    return addressClient.getAddressesByUserId(userId);
  }

  /**
   * Validates if the provided delivery address exists in the user's address list.
   *
   * @param deliveryAddressId The ID of the delivery address.
   * @param addresses         The list of addresses for the user.
   */
  private void validateAddress(Integer deliveryAddressId, List<AddressOutDto> addresses) {
    boolean validAddress = addresses.stream()
      .anyMatch(address -> address.getId().equals(deliveryAddressId));
    if (!validAddress) {
      log.error("Delivery address ID: {} not found for user", deliveryAddressId);
      throw new AddressNotFoundException(OrderConstants.ADDRESS_NOT_FOUND);
    }
  }

  /**
   * Retrieves the cart items for the given user and restaurant.
   *
   * @param orderInDto The input data for the order.
   * @return List of Cart items.
   */
  private List<Cart> getCartItems(OrderInDto orderInDto) {
    log.info("Fetching cart items for user ID: {} and restaurant ID: {}", orderInDto.getUserId(), orderInDto.getRestaurantId());
    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId());
    if (cartItems.isEmpty()) {
      log.error("No items found in the cart for user ID: {} and restaurant ID: {}", orderInDto.getUserId(), orderInDto.getRestaurantId());
      throw new CartNotFoundException(OrderConstants.CART_NOT_FOUND);
    }
    return cartItems;
  }
  /**
   * Validates that all cart items in the provided list exist in the user's cart.
   * Ensures the quantities and prices match between the provided cart items and those in the cart.
   *
   * @param cartItems   the list of cart items currently in the user's cart
   * @param cartItemsDto the list of cart items from the order request
   */
  private void validateCartItems(List<Cart> cartItems, List<Cart> cartItemsDto) {
    List<Cart> invalidCartItems = cartItemsDto.stream()
      .filter(cartItemDto -> cartItems.stream()
        .noneMatch(cartItem -> cartItem.getFoodItemId().equals(cartItemDto.getFoodItemId())
          && cartItem.getQuantity().equals(cartItemDto.getQuantity())
          && cartItem.getPrice().compareTo(cartItemDto.getPrice()) == 0))
      .collect(Collectors.toList());
    if (!invalidCartItems.isEmpty()) {
      String errorMessage = "Some items are not valid in the cart: " + invalidCartItems.stream()
        .map(dto -> String.format("foodItemId=%d, quantity=%d, price=%s", dto.getFoodItemId(), dto.getQuantity(), dto.getPrice()))
        .collect(Collectors.joining(", "));
      log.error(errorMessage);
    }
    log.info("All cart items validated successfully.");

  }
  /**
   * Calculates the total price of the order based on cart items.
   *
   * @param cartItems the list of cart items
   * @return the total price of the order
   */
  private BigDecimal calculateTotalPrice(List<Cart> cartItems) {
    BigDecimal totalPrice = cartItems.stream()
      .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    log.info("Total price calculated: {}", totalPrice);
    return totalPrice;
  }

  /**
   * Validates if the user's wallet has sufficient balance to cover the total price.
   *
   * @param user      the user placing the order
   * @param totalPrice the total price of the order
   */
  private void validateWalletBalance(UserOutDto user, BigDecimal totalPrice) {
    if (user.getWalletBalance().compareTo(totalPrice) < 0) {
      log.error("Insufficient balance in wallet. Required: {}, Available: {}", totalPrice, user.getWalletBalance());
      throw new InsufficientBalanceException("Insufficient balance in wallet to place the order.");
    }
    log.info("Wallet balance validated successfully.");
  }

  /**
   * Updates the user's wallet balance after placing an order.
   *
   * @param user      the user whose wallet balance is to be updated
   * @param totalPrice the amount to subtract from the user's wallet
   */
  private void updateWalletBalance(UserOutDto user, BigDecimal totalPrice) {
    userClient.updateWalletBalance(user.getId(), totalPrice.negate());
    log.info("Wallet balance updated for userId: {}. Amount deducted: {}", user.getId(), totalPrice);
  }

  /**
   * Creates an Order entity from the provided order details.
   * Sets the order status to PLACED and processes the cart items.
   *
   * @param orderInDto   the order details
   * @param totalPrice   the total price of the order
   * @param validCartItems the validated list of cart items
   * @return the created Order entity
   */
  private Order createOrder(OrderInDto orderInDto, BigDecimal totalPrice, List<Cart> validCartItems) {
    Order order = DtoConversion.convertOrderInDtoToOrder(orderInDto);
    order.setRestaurantId(orderInDto.getRestaurantId());
    order.setOrderStatus(OrderStatus.PLACED);
    order.setOrderTime(LocalDateTime.now());
    try {
      order.setCartItemsFromList(validCartItems);
    } catch (JsonProcessingException e) {
      log.error("Failed to process cart items", e);
      throw new RuntimeException("Failed to process cart items", e);
    }
    order.setTotalPrice(totalPrice);
    log.info("Order created with ID: {}", order.getId());
    return order;
  }

  /**
   * Cancels an existing order.
   * Validates order time for cancellation and updates wallet balance.
   *
   * @param orderId the ID of the order to cancel
   * @return a message indicating the result of the operation
   */
  @Override
  public MessageOutDto cancelOrder(Integer orderId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));

    if (order.getOrderTime().plusSeconds(30).isBefore(LocalDateTime.now())) {
      log.error("Order cancellation time exceeded for orderId: {}", orderId);
      throw new OrderUpdateException(OrderConstants.ORDER_CANCELLED_FAILURE);
    }

    userClient.updateWalletBalance(order.getUserId(), order.getTotalPrice());

    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);

    log.info("Order cancelled successfully with ID: {}", orderId);
    return new MessageOutDto(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY);
  }

  /**
   * Retrieves all orders placed by a specific user.
   *
   * @param userId the ID of the user
   * @return a list of orders placed by the user
   */
  @Override
  public List<OrderOutDto> getOrdersByUserId(Integer userId) {
    UserOutDto user = getUser(userId);
    validateUserRole(user);
    List<Order> orders = orderRepository.findByUserId(userId);
    List<OrderOutDto> orderOutDtos = orders.stream()
      .map(DtoConversion::convertOrderToOrderOutDto)
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for userId: {}", orderOutDtos.size(), userId);
    return orderOutDtos;
  }

  /**
   * Retrieves all orders received by a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of orders received by the restaurant
   */
  @Override
  public List<OrderOutDto> getOrdersByRestaurantId(Integer restaurantId) {
    FoodItemOutDto restaurant = restaurantClient.getRestaurantById(restaurantId);

    List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
    List<OrderOutDto> orderOutDtos = orders.stream()
      .map(DtoConversion::convertOrderToOrderOutDto)
      .collect(Collectors.toList());

    log.info("Retrieved {} orders for restaurantId: {}", orderOutDtos.size(), restaurantId);
    return orderOutDtos;
  }

  /**
   * Marks an order as completed.
   * Validates user role and updates the order status to COMPLETED.
   *
   * @param orderId the ID of the order to complete
   * @return a message indicating the result of the operation
   */
  @Override
  public MessageOutDto markOrderAsCompleted(Integer orderId, Integer userId) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));

    UserOutDto user = getUser(userId);

    if (user.getUserRole() != UserRole.RESTAURANT_OWNER) {
      log.error("Order cannot be completed because it is not in PLACED status. Current status: {}", order.getOrderStatus());
      throw new OrderUpdateException("Only a restaurant owner can complete this order.");
    }
    order.setOrderStatus(OrderStatus.COMPLETED);
    orderRepository.save(order);
    log.info("Order marked as completed with ID: {}", orderId);
    return new MessageOutDto(OrderConstants.ORDER_COMPLETED_SUCCESSFULLY);
  }
}
