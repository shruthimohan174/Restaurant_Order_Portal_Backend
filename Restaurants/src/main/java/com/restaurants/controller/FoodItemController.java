package com.restaurants.controller;

import com.restaurants.dto.FoodItemInDto;
import com.restaurants.dto.FoodItemUpdateInDto;
import com.restaurants.dto.FoodItemOutDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.entities.FoodItem;
import com.restaurants.service.FoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing food items.
 */
@RestController
@RequestMapping("/foodItem")
@Slf4j
public class FoodItemController {
  /**
   * Service layer dependency for foodItem-related operations.
   */
  @Autowired
  private FoodItemService foodItemService;

  /**
   * Creates a new food item.
   *
   * @param request the details of the food item to be added
   * @param image the image representing the food item
   * @return a response entity containing a message indicating the result of the operation
   */
  @PostMapping("")
  public ResponseEntity<MessageOutDto> addFoodItems(final @Valid @ModelAttribute FoodItemInDto request,
                                                    final @RequestParam("image") MultipartFile image) {
    log.info("Received request to add food item");
    MessageOutDto message = foodItemService.addFoodItems(request, image);
    log.info("Food items added successfully");
    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  /**
   * Updates an existing food item.
   *
   * @param request the details of the food item to be updated
   * @param id      the ID of the food item to be updated
   * @param image  the image of food item
   * @return a response entity containing a message indicating the result of the operation
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<MessageOutDto> updateFoodItem(final @Valid @ModelAttribute FoodItemUpdateInDto request,
                                                      final @PathVariable Integer id,
                                                      final @RequestParam MultipartFile image) {
    log.info("Received request to update food item with ID: {}", id);
    MessageOutDto message = foodItemService.updateFoodItems(request, id, image);
    log.info("Food item updated successfully");
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  /**
   * Retrieves all food items.
   *
   * @return a response entity containing a list of all food items
   */
  @GetMapping("")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItems() {
    log.info("Retrieving all food items");
    List<FoodItemOutDto> response = foodItemService.getAllFoodItems();
    log.info("Retrieved {} food items", response.size());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food items by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a response entity containing a list of food items for the specified restaurant
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItemsByRestaurantId(final @PathVariable Integer restaurantId) {
    log.info("Retrieving food items for restaurant ID: {}", restaurantId);
    List<FoodItemOutDto> response = foodItemService.getAllByRestaurantId(restaurantId);
    log.info("Retrieved {} food items for restaurant ID: {}", response.size(), restaurantId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food items by category ID.
   *
   * @param categoryId the ID of the food category
   * @return a response entity containing a list of food items for the specified category
   */
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<FoodItemOutDto>> getAllFoodItemsByCategoryId(final @PathVariable Integer categoryId) {
    log.info("Retrieving food items for category ID: {}", categoryId);
    List<FoodItemOutDto> response = foodItemService.getAllByCategoryId(categoryId);
    log.info("Retrieved {} food items for category ID: {}", response.size(), categoryId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves the image data for a food item by its ID.
   *
   * @param id the ID of the food item
   * @return a response entity containing the image data as a byte array
   */
  @GetMapping("/{id}/image")
  public ResponseEntity<byte[]> getFoodItemImage(final @PathVariable Integer id) {
    log.info("Retrieving image for Food Item with ID: {}", id);
    byte[] imageData = foodItemService.getFoodItemImage(id);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
  }

  /**
   * Retrieves the image data for a food item by its ID.
   *
   * @param id the ID of the food item
   * @return a response entity containing the data as a food item
   */
  @GetMapping("/{id}")
  public ResponseEntity<FoodItem> getFoodItemById(final @PathVariable Integer id) {
    log.info("Retrieving food items for category ID: {}", id);
    FoodItem response = foodItemService.findFoodItemsById(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
