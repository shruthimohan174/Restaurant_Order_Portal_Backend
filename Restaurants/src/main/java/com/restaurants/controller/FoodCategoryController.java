package com.restaurants.controller;

import com.restaurants.dto.FoodCategoryInDto;
import com.restaurants.dto.FoodCategoryOutDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.service.FoodCategoryService;
import lombok.extern.slf4j.Slf4j;
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
 * Controller for managing food categories.
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class FoodCategoryController {

  /**
   * Service layer dependency for service-related operations.
   */
  @Autowired
  private FoodCategoryService foodCategoryService;

  /**
   * Creates a new food category.
   *
   * @param request the details of the food category to be added
   * @return a response entity containing a message indicating the result of the operation
   */
  @PostMapping("")
  public ResponseEntity<MessageOutDto> addCategory(final @Valid @RequestBody FoodCategoryInDto request) {
    log.info("Received request to add category");
    MessageOutDto message = foodCategoryService.addCategory(request);
    log.info("Food category added successfully");
    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  /**
   * Updates an existing food category.
   *
   * @param request the details of the food category to be updated
   * @param id      the ID of the food category to be updated
   * @return a response entity containing a message indicating the result of the operation
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<MessageOutDto> updateCategory(final @Valid @RequestBody FoodCategoryInDto request,
                                                      final @PathVariable Integer id) {
    log.info("Received request to update category with ID: {}", id);
    MessageOutDto message = foodCategoryService.updateCategory(request, id);
    log.info("Category updated successfully");
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  /**
   * Retrieves all food categories.
   *
   * @return a response entity containing a list of all food categories
   */
  @GetMapping("")
  public ResponseEntity<List<FoodCategoryOutDto>> getAllCategory() {
    log.info("Retrieving all food categories");
    List<FoodCategoryOutDto> response = foodCategoryService.viewAllCategory();
    log.info("Retrieved {} food categories", response.size());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food categories by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a response entity containing a list of food categories for the specified restaurant
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<FoodCategoryOutDto>> getAllCategoryByRestaurantId(final @PathVariable Integer restaurantId) {
    log.info("Retrieving food categories for restaurant ID: {}", restaurantId);
    List<FoodCategoryOutDto> response = foodCategoryService.findCategoryByRestaurantId(restaurantId);
    log.info("Retrieved {} food categories for restaurant ID: {}", response.size(), restaurantId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
