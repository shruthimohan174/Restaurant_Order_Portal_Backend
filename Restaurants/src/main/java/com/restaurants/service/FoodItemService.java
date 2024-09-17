package com.restaurants.service;

import com.restaurants.dto.FoodItemInDto;
import com.restaurants.dto.FoodItemUpdateInDto;
import com.restaurants.dto.FoodItemOutDto;
import com.restaurants.dto.MessageOutDto;
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
   * @return a {@link MessageOutDto} containing the success message
   */
  MessageOutDto addFoodItems(FoodItemInDto request, MultipartFile image);

  /**
   * Updates an existing food item.
   *
   * @param request the updated details of the food item
   * @param id      the ID of the food item to be updated
   * @param image the image of food item
   * @return a {@link MessageOutDto} containing the success message
   */
  MessageOutDto updateFoodItems(FoodItemUpdateInDto request, Integer id, MultipartFile image);

  /**
   * Retrieves a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the {@link FoodItem} with the specified ID
   */
  FoodItem findFoodItemsById(Integer id);

  /**
   * Retrieves all food items.
   *
   * @return a list of {@link FoodItemOutDto} representing all food items
   */
  List<FoodItemOutDto> getAllFoodItems();

  /**
   * Retrieves food items associated with a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of {@link FoodItemOutDto} representing food items for the specified restaurant
   */
  List<FoodItemOutDto> getAllByRestaurantId(Integer restaurantId);

  /**
   * Retrieves food items associated with a specific category.
   *
   * @param categoryId the ID of the food category
   * @return a list of {@link FoodItemOutDto} representing food items for the specified category
   */
  List<FoodItemOutDto> getAllByCategoryId(Integer categoryId);

  /**
   * Retrieves the image data for a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the image data as a byte array
   */
  byte[] getFoodItemImage(Integer id);

  /**
   * Checks if a food item with the same name exists in the specified restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @param itemName     the name of the food item
   * @return true if the item exists, false otherwise
   */
  boolean itemExistsInRestaurant(Integer restaurantId, String itemName);

  /**
   * Validates the image file to ensure it is of a valid type (JPEG or PNG).
   *
   * @param image the image file to validate
   */
  void validateImageFile(MultipartFile image);
}
