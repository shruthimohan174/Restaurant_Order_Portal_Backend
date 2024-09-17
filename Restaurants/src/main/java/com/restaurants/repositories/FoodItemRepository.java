package com.restaurants.repositories;

import com.restaurants.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link FoodItem} entity.
 * Provides CRUD operations and custom queries for food items.
 */
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

  /**
   * Finds food items by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food items for the given restaurant
   */
  List<FoodItem> findByRestaurantId(Integer restaurantId);

  /**
   * Finds food items by category ID.
   *
   * @param categoryId the ID of the category
   * @return a list of food items for the given category
   */
  List<FoodItem> findByCategoryId(Integer categoryId);

  /**
   * Checks if a food item exists for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant to check
   * @param itemName the name of the item to check, case-insensitive
   * @return {@code true} if a food item with the specified name exists for the given restaurant;
   *         {@code false} otherwise
   */
  boolean existsByRestaurantIdAndItemNameIgnoreCase(Integer restaurantId, String itemName);

}
