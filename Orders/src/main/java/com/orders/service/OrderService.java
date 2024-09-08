package com.orders.service;

import com.orders.dto.indto.OrderInDto;
import com.orders.dto.outdto.MessageOutDto;
import com.orders.dto.outdto.OrderOutDto;

import java.util.List;

public interface OrderService {

  MessageOutDto placeOrder(OrderInDto orderInDto);

  MessageOutDto cancelOrder(Integer orderId);

  List<OrderOutDto> getOrdersByUserId(Integer userId);

  List<OrderOutDto> getOrdersByRestaurantId(Integer restaurantId);

   void markOrderAsCompleted(Integer orderId);

  }
