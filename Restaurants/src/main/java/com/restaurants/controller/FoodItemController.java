package com.restaurants.controller;

import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.service.FoodItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing food items.
 */
@RestController
@RequestMapping("/foodItem")
public class FoodItemController {
  private static final Logger logger = LoggerFactory.getLogger(FoodItemController.class);

  @Autowired
  private FoodItemService foodItemService;

  /**
   * Creates a new food item.
   *
   * @param request the details of the food item to be added
   * @return the created food item
   */
  @PostMapping("/add")
  public ResponseEntity<FoodItemOutDto> addFoodItems(@Valid @RequestBody FoodItemInDto request) {
    logger.info("Received request to add food item: {}", request);
    FoodItemOutDto response = foodItemService.addFoodItems(request);
    logger.info("Food item added successfully: {}", response);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates an existing food item.
   *
   * @param id      the ID of the food item to be updated
   * @param request the updated details of the food item
   * @return the updated food item
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<FoodItemOutDto> updateFoodItem(@Valid @RequestBody FoodItemInDto request, @PathVariable Integer id) {
    logger.info("Received request to update food item with ID: {}", id);
    FoodItemOutDto response = foodItemService.updateFoodItems(request, id);
    logger.info("Food item updated successfully: {}", response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves all food items.
   *
   * @return a list of all food items
   */
  @GetMapping("")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItems() {
    logger.info("Retrieving all food items");
    List<FoodItemOutDto> response = foodItemService.getAllFoodItems();
    logger.info("Retrieved {} food items", response.size());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food items by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food items for the specified restaurant
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItemsByRestaurantId(@PathVariable Integer restaurantId) {
    logger.info("Retrieving food items for restaurant ID: {}", restaurantId);
    List<FoodItemOutDto> response = foodItemService.getAllByRestaurantId(restaurantId);
    logger.info("Retrieved {} food items for restaurant ID: {}", response.size(), restaurantId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food items by category ID.
   *
   * @param categoryId the ID of the food category
   * @return a list of food items for the specified category
   */
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItemsByCategoryId(@PathVariable Integer categoryId) {
    logger.info("Retrieving food items for category ID: {}", categoryId);
    List<FoodItemOutDto> response = foodItemService.getAllByCategoryId(categoryId);
    logger.info("Retrieved {} food items for category ID: {}", response.size(), categoryId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
