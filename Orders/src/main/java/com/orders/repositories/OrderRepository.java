package com.orders.repositories;

import com.orders.entities.Order;

import java.util.List;

public interface OrderRepository {
  List<Order> findByUserId(Integer userId);

}
