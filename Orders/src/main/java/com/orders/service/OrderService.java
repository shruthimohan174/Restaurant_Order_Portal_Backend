package com.orders.service;

import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;

import java.util.List;

public interface OrderService {

  /**
   * Places an order based on the given order details.
   *
   * @param orderInDto the DTO containing order details
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto placeOrder(OrderInDto orderInDto);

  /**
   * Cancels an order based on the order ID.
   *
   * @param orderId the ID of the order to cancel
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto cancelOrder(Integer orderId);

  /**
   * Retrieves all orders placed by a user.
   *
   * @param userId the ID of the user
   * @return a list of {@link OrderOutDto} containing order details
   */
  List<OrderOutDto> getOrdersByUserId(Integer userId);

  /**
   * Retrieves all orders for a restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of {@link OrderOutDto} containing order details
   */
  List<OrderOutDto> getOrdersByRestaurantId(Integer restaurantId);

  /**
   * Marks an order as completed based on the order ID and user ID.
   *
   * @param orderId the ID of the order to complete
   * @param userId  the ID of the user
   * @return a success message wrapped in {@link MessageOutDto}
   */
  MessageOutDto markOrderAsCompleted(Integer orderId, Integer userId);
}
