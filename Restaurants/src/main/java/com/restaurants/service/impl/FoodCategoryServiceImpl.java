package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.FoodCategoryInDto;
import com.restaurants.dto.outdto.FoodCategoryOutDto;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.CategoryNotFoundException;
import com.restaurants.repositories.FoodCategoryRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link FoodCategoryService} interface.
 * Provides methods to manage food categories.
 */
@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

  private static final Logger logger = LoggerFactory.getLogger(FoodCategoryServiceImpl.class);

  @Autowired
  private FoodCategoryRepository foodCategoryRepository;

  @Autowired
  private RestaurantService restaurantService;

  /**
   * Adds a new food category.
   *
   * @param request the food category data to be added
   * @return the added food category
   */
  @Override
  public FoodCategoryOutDto addCategory(FoodCategoryInDto request) {
    logger.info("Adding category: {}", request);
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
    FoodCategory category = DtoConversion.convertCategoryRequestToCategory(request);
    FoodCategory savedCategory = foodCategoryRepository.save(category);
    logger.info("Category added successfully: {}", savedCategory);
    return DtoConversion.convertCategoryToResponse(savedCategory);
  }

  /**
   * Updates an existing food category.
   *
   * @param request the food category data to update
   * @param id      the ID of the food category to update
   * @return the updated food category
   */
  @Override
  public FoodCategoryOutDto updateCategory(FoodCategoryInDto request, Integer id) {
    logger.info("Updating category with ID: {}", id);
    FoodCategory existingCategory = findCategoryById(id);
    existingCategory.setCategoryName(request.getCategoryName());
    FoodCategory updatedCategory = foodCategoryRepository.save(existingCategory);
    logger.info("Category updated successfully: {}", updatedCategory);
    return DtoConversion.convertCategoryToResponse(updatedCategory);
  }

  /**
   * Retrieves all food categories.
   *
   * @return a list of all food categories
   */
  @Override
  public List<FoodCategoryOutDto> viewAllCategory() {
    logger.info("Retrieving all categories");
    List<FoodCategory> categoryList = foodCategoryRepository.findAll();
    List<FoodCategoryOutDto> responseList = new ArrayList<>();
    for (FoodCategory category : categoryList)
      responseList.add(DtoConversion.convertCategoryToResponse(category));
    logger.info("Retrieved {} categories", responseList.size());
    return responseList;
  }

  /**
   * Retrieves food categories for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food categories for the given restaurant
   */
  @Override
  public List<FoodCategoryOutDto> findCategoryByRestaurantId(Integer restaurantId) {
    logger.info("Retrieving categories for restaurant ID: {}", restaurantId);
    List<FoodCategory> categoryList = foodCategoryRepository.findByRestaurantId(restaurantId);
    List<FoodCategoryOutDto> responseList = new ArrayList<>();
    for (FoodCategory category : categoryList)
      responseList.add(DtoConversion.convertCategoryToResponse(category));
    logger.info("Retrieved {} categories for restaurant ID: {}", responseList.size(), restaurantId);
    return responseList;
  }

  /**
   * Finds a food category by its ID.
   *
   * @param id the ID of the food category
   * @return the food category
   * @throws CategoryNotFoundException if the food category is not found
   */
  @Override
  public FoodCategory findCategoryById(Integer id) {
    logger.info("Finding category by ID: {}", id);
    return foodCategoryRepository.findById(id).orElseThrow(() -> {
      logger.error("Category not found for ID: {}", id);
      return new CategoryNotFoundException(RestaurantConstants.CATEGORY_NOT_FOUND);
    });
  }
}
