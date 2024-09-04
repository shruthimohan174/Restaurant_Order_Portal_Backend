package com.restaurants.service;

import com.restaurants.dto.indto.FoodCategoryInDto;
import com.restaurants.dto.outdto.FoodCategoryOutDto;
import com.restaurants.entities.FoodCategory;

import java.util.List;

/**
 * Service interface for managing food categories.
 */
public interface FoodCategoryService {

  /**
   * Adds a new food category.
   *
   * @param request the details of the food category to be added
   * @return the created food category
   */
  String addCategory(FoodCategoryInDto request);

  /**
   * Updates an existing food category.
   *
   * @param request the updated details of the food category
   * @param id      the ID of the food category to be updated
   * @return the updated food category
   */
  String updateCategory(FoodCategoryInDto request, Integer id);

  /**
   * Retrieves all food categories.
   *
   * @return a list of all food categories
   */
  List<FoodCategoryOutDto> viewAllCategory();

  /**
   * Retrieves food categories by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food categories for the specified restaurant
   */
  List<FoodCategoryOutDto> findCategoryByRestaurantId(Integer restaurantId);

  /**
   * Retrieves a food category by its ID.
   *
   * @param id the ID of the food category
   * @return the food category with the specified ID
   */
  FoodCategory findCategoryById(Integer id);

}
