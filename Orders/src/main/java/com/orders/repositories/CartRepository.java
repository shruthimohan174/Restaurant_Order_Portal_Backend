package com.orders.repositories;

import com.orders.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Cart entity persistence.
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {


  /**
   * Finds carts by user ID.
   *
   * @param userId the user ID
   * @return list of carts
   */
  List<Cart> findByUserId(Integer userId);

  /**
   * Finds a cart by user ID, food item ID, and restaurant ID.
   *
   * @param userId       the user ID
   * @param foodItemId   the food item ID
   * @param restaurantId the restaurant ID
   * @return an optional cart
   */
  Optional<Cart> findByUserIdAndFoodItemIdAndRestaurantId(Integer userId, Integer foodItemId, Integer restaurantId);

  /**
   * Finds carts by user ID and restaurant ID.
   *
   * @param userId       the user ID
   * @param restaurantId the restaurant ID
   * @return list of carts
   */
  List<Cart> findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
}
