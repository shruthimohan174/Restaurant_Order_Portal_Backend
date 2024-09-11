package com.orders.controller;

import com.orders.dto.OrderInDto;
import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderOutDto;
import com.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing order operations.
 * Provides endpoints to place, cancel, retrieve, and complete orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private OrderService orderService;

  /**
   * Places a new order.
   *
   * @param orderInDto DTO containing order details.
   * @return Response entity containing a success message.
   */
  @PostMapping("/place")
  public ResponseEntity<MessageOutDto> placeOrder(@Valid @RequestBody OrderInDto orderInDto) {
    logger.info("Placing order with details: {}", orderInDto);
    MessageOutDto response = orderService.placeOrder(orderInDto);
    logger.info("Order placed successfully with ID {}", response.getMessage());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Cancels an existing order.
   *
   * @param orderId The ID of the order to cancel.
   * @return Response entity containing a success message.
   */
  @DeleteMapping("/cancel/{orderId}")
  public ResponseEntity<MessageOutDto> cancelOrder(@Valid @PathVariable Integer orderId) {
    logger.info("Cancelling order with ID {}", orderId);
    MessageOutDto response = orderService.cancelOrder(orderId);
    logger.info("Order with ID {} cancelled successfully", orderId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves all orders placed by a specific user.
   *
   * @param userId The ID of the user.
   * @return Response entity containing a list of orders.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderOutDto>> getOrdersByUserId(@PathVariable Integer userId) {
    logger.info("Fetching orders for user ID {}", userId);
    List<OrderOutDto> orders = orderService.getOrdersByUserId(userId);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  /**
   * Retrieves all orders associated with a specific restaurant.
   *
   * @param restaurantId The ID of the restaurant.
   * @return Response entity containing a list of orders.
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<OrderOutDto>> getOrdersByRestaurantId(@PathVariable Integer restaurantId) {
    logger.info("Fetching orders for restaurant ID {}", restaurantId);
    List<OrderOutDto> orders = orderService.getOrdersByRestaurantId(restaurantId);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  /**
   * Marks an order as completed.
   *
   * @param orderId The ID of the order.
   * @param userId  The ID of the user who completed the order.
   * @return Response entity with HTTP status OK.
   */
  @PostMapping("/complete/{orderId}/user/{userId}")
  public ResponseEntity<Void> markOrderAsCompleted(@PathVariable Integer orderId, @PathVariable Integer userId) {
    logger.info("Marking order with ID {} as completed by user ID {}", orderId, userId);
    orderService.markOrderAsCompleted(orderId, userId);
    logger.info("Order with ID {} marked as completed successfully", orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
