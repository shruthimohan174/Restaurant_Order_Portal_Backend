package com.restaurants.controller;

import com.restaurants.dto.indto.FoodCategoryInDto;
import com.restaurants.dto.outdto.FoodCategoryOutDto;
import com.restaurants.service.FoodCategoryService;
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
 * Controller for managing food categories.
 */
@RestController
@RequestMapping("/category")
public class FoodCategoryController {

  private static final Logger logger = LoggerFactory.getLogger(FoodCategoryController.class);

  @Autowired
  private FoodCategoryService foodCategoryService;

  /**
   * Creates a new food cactegory.
   *
   * @param request the details of the food category to be added
   * @return the created food category
   */
  @PostMapping("/add")
  public ResponseEntity<FoodCategoryOutDto> addFoodCategory(@Valid @RequestBody FoodCategoryInDto request){
    logger.info("Received request to add food category: {}", request);
    FoodCategoryOutDto response = foodCategoryService.addCategory(request);
    logger.info("Food category added successfully: {}", response);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates an existing food category.
   *
   * @param id      the ID of the food category to be updated
   * @param request the updated details of the food category
   * @return the updated food category
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<FoodCategoryOutDto> addFoodCategory(@PathVariable Integer id, @Valid @RequestBody FoodCategoryInDto request){
    logger.info("Received request to update food category with ID: {}", id);
    FoodCategoryOutDto response = foodCategoryService.updateCategory(request, id);
    logger.info("Food category updated successfully: {}", response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves all food categories.
   *
   * @return a list of all food categories
   */
  @GetMapping("")
  public ResponseEntity<List<FoodCategoryOutDto>> getAllCategory(){
    logger.info("Retrieving all food categories");
    List<FoodCategoryOutDto> response = foodCategoryService.viewAllCategory();
    logger.info("Retrieved {} food categories", response.size());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves food categories by restaurant ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food categories for the specified restaurant
   */
  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<FoodCategoryOutDto>> getAllCategoryByRestaurantId(@PathVariable Integer restaurantId) {
    logger.info("Retrieving food categories for restaurant ID: {}", restaurantId);
    List<FoodCategoryOutDto> response = foodCategoryService.findCategoryByRestaurantId(restaurantId);
    logger.info("Retrieved {} food categories for restaurant ID: {}", response.size(), restaurantId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}

