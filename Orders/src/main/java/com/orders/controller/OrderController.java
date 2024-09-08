package com.orders.controller;

import com.orders.dto.indto.OrderInDto;
import com.orders.dto.outdto.MessageOutDto;
import com.orders.dto.outdto.OrderOutDto;
import com.orders.service.OrderService;
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

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/place")
  public ResponseEntity<MessageOutDto> placeOrder(@RequestBody OrderInDto orderInDto) {
    MessageOutDto response = orderService.placeOrder(orderInDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @DeleteMapping("/cancel/{orderId}")
  public ResponseEntity<MessageOutDto> cancelOrder(@PathVariable Integer orderId) {
    MessageOutDto response = orderService.cancelOrder(orderId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderOutDto>> getOrdersByUserId(@PathVariable Integer userId) {
    List<OrderOutDto> orders = orderService.getOrdersByUserId(userId);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<OrderOutDto>> getOrdersByRestaurantId(@PathVariable Integer restaurantId) {
    List<OrderOutDto> orders = orderService.getOrdersByRestaurantId(restaurantId);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PostMapping("/complete/{orderId}")
  public ResponseEntity<Void> markOrderAsCompleted(@PathVariable Integer orderId) {
    orderService.markOrderAsCompleted(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}