package com.restaurants.repositories;

import com.restaurants.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link Restaurant} entity.
 * Provides CRUD operations and custom queries for restaurants.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

  /**
   * Finds restaurants by user ID.
   *
   * @param userId the ID of the user.
   * @return a list of restaurants for the given user.
   */
  List<Restaurant> findByUserId(Integer userId);

  /**
   * Checks if a restaurant exists with the specified name.
   *
   * @param normalizedRestaurantName the name of the restaurant to check, case-insensitive.
   * @return {@code true} if a restaurant with the specified name exists; {@code false} otherwise.
   */
  boolean existsByRestaurantNameIgnoreCase(String normalizedRestaurantName);
}
