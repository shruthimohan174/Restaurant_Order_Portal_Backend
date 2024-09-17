package com.restaurants.service;

import com.restaurants.dto.FoodCategoryInDto;
import com.restaurants.dto.FoodCategoryOutDto;
import com.restaurants.dto.MessageOutDto;
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
   * @return a {@link MessageOutDto} containing the success message
   */
  MessageOutDto addCategory(FoodCategoryInDto request);

  /**
   * Updates an existing food category.
   *
   * @param request the updated details of the food category
   * @param id      the ID of the food category to be updated
   * @return a {@link MessageOutDto} containing the success message
   */
  MessageOutDto updateCategory(FoodCategoryInDto request, Integer id);

  /**
   * Retrieves all food categories.
   *
   * @return a list of {@link FoodCategoryOutDto} representing all food categories
   */
  List<FoodCategoryOutDto> viewAllCategory();

  /**
   * Retrieves food categories associated with a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of {@link FoodCategoryOutDto} representing food categories for the specified restaurant
   */
  List<FoodCategoryOutDto> findCategoryByRestaurantId(Integer restaurantId);

  /**
   * Retrieves a food category by its ID.
   *
   * @param id the ID of the food category
   * @return the {@link FoodCategory} with the specified ID
   */
  FoodCategory findCategoryById(Integer id);
}
