package com.restaurants.repositories;

import com.restaurants.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link FoodCategory} entity.
 * Provides CRUD operations and custom queries for food categories.
 */
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {

  /**
   * Finds food categories by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food categories for the given restaurant
   */
  List<FoodCategory> findByRestaurantId(Integer restaurantId);

  boolean existsByRestaurantIdAndCategoryNameIgnoreCase(Integer restaurantId, String categoryName);
}
