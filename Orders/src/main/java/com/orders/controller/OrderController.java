package com.orders.controller;

import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;
import com.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderController {
  /**
   * Service layer dependency for order-related operations.
   */
  @Autowired
  private OrderService orderService;

  /**
   * Places a new order.
   *
   * @param orderInDto DTO containing order details.
   * @return Response entity containing a success message.
   */
  @PostMapping("")
  public ResponseEntity<MessageOutDto> placeOrder(final @Valid @RequestBody OrderInDto orderInDto) {
    log.info("Placing order");
    MessageOutDto response = orderService.placeOrder(orderInDto);
    log.info("Order placed successfully");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Cancels an existing order.
   *
   * @param orderId The ID of the order to cancel.
   * @return Response entity containing a success message.
   */
  @DeleteMapping("/cancel/{orderId}")
  public ResponseEntity<MessageOutDto> cancelOrder(final @Valid @PathVariable Integer orderId) {
    log.info("Cancelling order with ID {}", orderId);
    MessageOutDto response = orderService.cancelOrder(orderId);
    log.info("Order with ID {} cancelled successfully", orderId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves all orders placed by a specific user.
   *
   * @param userId The ID of the user.
   * @return Response entity containing a list of orders.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderOutDto>> getOrdersByUserId(@PathVariable final Integer userId) {
    log.info("Fetching orders for user ID {}", userId);
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
  public ResponseEntity<List<OrderOutDto>> getOrdersByRestaurantId(@PathVariable final Integer restaurantId) {
    log.info("Fetching orders for restaurant ID {}", restaurantId);
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
  public ResponseEntity<Void> markOrderAsCompleted(@PathVariable final Integer orderId, @PathVariable final Integer userId) {
    log.info("Marking order with ID {} as completed by user ID {}", orderId, userId);
    orderService.markOrderAsCompleted(orderId, userId);
    log.info("Order with ID {} marked as completed successfully", orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
