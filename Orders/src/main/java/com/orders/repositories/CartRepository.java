package com.orders.repositories;

import com.orders.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  List<Cart> findByUserId(Integer userId);

  Optional<Cart> findByUserIdAndFoodItemIdAndRestaurantId(Integer userId, Integer foodItemId, Integer restaurantId);

  List<Cart> findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);

//  Optional<Cart> findByUserIdAndFoodItemIdAndOrderId(Integer userId, Integer foodItemId, Integer orderId);

}
