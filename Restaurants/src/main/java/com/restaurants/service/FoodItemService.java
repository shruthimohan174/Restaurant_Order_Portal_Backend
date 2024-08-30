package com.restaurants.service;

import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.entities.FoodItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing food items.
 */
public interface FoodItemService {
  /**
   * Adds a new food item.
   *
   * @param request the details of the food item to be added
   * @param image   the image representing the food item
   * @return the created food item
   */
  FoodItemOutDto addFoodItems(FoodItemInDto request, MultipartFile image);
  /**
   * Updates an existing food item.
   *
   * @param request the updated details of the food item
   * @param id      the ID of the food item to be updated
   * @return the updated food item
   */
  FoodItemOutDto updateFoodItems(FoodItemInDto request, Integer id);

  /**
   * Retrieves a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the food item with the specified ID
   */
  FoodItem findFoodItemsById(Integer id);

  /**
   * Retrieves all food items.
   *
   * @return a list of all food items
   */
  List<FoodItemOutDto> getAllFoodItems();

  /**
   * Retrieves food items by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food items for the specified restaurant
   */
  List<FoodItemOutDto> getAllByRestaurantId(Integer restaurantId);

  /**
   * Retrieves food items by category ID.
   *
   * @param categoryId the ID of the food category
   * @return a list of food items for the specified category
   */
  List<FoodItemOutDto> getAllByCategoryId(Integer categoryId);

}
