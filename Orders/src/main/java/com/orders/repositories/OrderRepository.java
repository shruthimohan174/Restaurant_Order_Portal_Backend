package com.orders.repositories;

import com.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing Order entity persistence.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

  /**
   * Finds orders by user ID.
   *
   * @param userId the user ID
   * @return list of orders
   */
  List<Order> findByUserId(Integer userId);

  /**
   * Finds orders by restaurant ID.
   *
   * @param restaurantId the restaurant ID
   * @return list of orders
   */
  List<Order> findByRestaurantId(Integer restaurantId);
}
