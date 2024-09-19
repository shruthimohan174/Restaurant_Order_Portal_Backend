package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.FoodCategoryInDto;
import com.restaurants.dto.FoodCategoryOutDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.converter.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.ResourceConflictException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.repositories.FoodCategoryRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link FoodCategoryService} interface.
 * Provides methods to manage food categories.
 */
@Service
@Slf4j
public class FoodCategoryServiceImpl implements FoodCategoryService {

  /**
   * Service layer dependency for category repository-related operations.
   */
  @Autowired
  private FoodCategoryRepository foodCategoryRepository;

  /**
   * Service layer dependency for restaurant-related operations.
   */
  @Autowired
  private RestaurantService restaurantService;

  /**
   * Adds a new food category.
   *
   * @param request the food category data to be added
   * @return the response message indicating success or failure
   */
  @Override
  public MessageOutDto addCategory(final FoodCategoryInDto request) {
    log.info("Adding category ");
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
    String sanitizedCategoryName = normalizedCategoryName(request.getCategoryName());

    if (categoryExistsRestaurant(restaurant.getId(), sanitizedCategoryName)) {
      throw new ResourceConflictException(RestaurantConstants.CATEGORY_ALREADY_EXISTS);
    }

    FoodCategory category = DtoConversion.convertCategoryRequestToCategory(request);
    category.setCategoryName(sanitizedCategoryName);
    foodCategoryRepository.save(category);

    log.info("Category added successfully: {}", request.getCategoryName());
    return new MessageOutDto(RestaurantConstants.FOOD_CATEGORY_ADDED_SUCCESSFULLY);
  }

  /**
   * Updates an existing food category.
   *
   * @param request the updated food category data
   * @param id      the ID of the food category to be updated
   * @return the response message indicating success or failure
   */
  @Override
  public MessageOutDto updateCategory(final FoodCategoryInDto request, final Integer id) {
    log.info("Updating category with ID: {}", id);
    FoodCategory existingCategory = findCategoryById(id);
    String sanitizedCategoryName = normalizedCategoryName(request.getCategoryName());

    if (!existingCategory.getCategoryName().equalsIgnoreCase(sanitizedCategoryName)
      && categoryExistsRestaurant(request.getRestaurantId(), sanitizedCategoryName)) {
      throw new ResourceConflictException(RestaurantConstants.CATEGORY_ALREADY_EXISTS);
    }

    existingCategory.setCategoryName(sanitizedCategoryName);
    foodCategoryRepository.save(existingCategory);

    log.info("Category updated successfully: {}", request.getCategoryName());
    return new MessageOutDto(RestaurantConstants.FOOD_CATEGORY_UPDATED_SUCCESSFULLY);
  }

  /**
   * Retrieves all food categories.
   *
   * @return a list of all food categories
   */
  @Override
  public List<FoodCategoryOutDto> viewAllCategory() {
    log.info("Retrieving all categories");
    List<FoodCategory> categoryList = foodCategoryRepository.findAll();
    List<FoodCategoryOutDto> responseList = new ArrayList<>();

    for (FoodCategory category : categoryList) {
      responseList.add(DtoConversion.convertCategoryToResponse(category));
    }

    log.info("Retrieved {} categories", responseList.size());
    return responseList;
  }

  /**
   * Retrieves food categories for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food categories for the given restaurant
   */
  @Override
  public List<FoodCategoryOutDto> findCategoryByRestaurantId(final Integer restaurantId) {
    log.info("Retrieving categories for restaurant ID: {}", restaurantId);
    List<FoodCategory> categoryList = foodCategoryRepository.findByRestaurantId(restaurantId);
    List<FoodCategoryOutDto> responseList = new ArrayList<>();

    for (FoodCategory category : categoryList) {
      responseList.add(DtoConversion.convertCategoryToResponse(category));
    }

    log.info("Retrieved {} categories for restaurant ID: {}", responseList.size(), restaurantId);
    return responseList;
  }


  /**
   * Finds a food category by its ID.
   *
   * @param id the ID of the food category
   * @return the food category
   */
  @Override
  public FoodCategory findCategoryById(final Integer id) {
    log.info("Finding category by ID: {}", id);
    return foodCategoryRepository.findById(id).orElseThrow(() -> {
      log.error("Category not found for ID: {}", id);
      return new ResourceNotFoundException(RestaurantConstants.CATEGORY_NOT_FOUND);
    });
  }

  /**
   * Checks if a food category with the given name exists for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @param categoryName the name of the food category
   * @return true if the category exists, false otherwise
   */
  private boolean categoryExistsRestaurant(final Integer restaurantId, final String categoryName) {
    return foodCategoryRepository.existsByRestaurantIdAndCategoryNameIgnoreCase(
      restaurantId, categoryName.trim().replaceAll("\\s+", " ")
    );
  }

  /**
   * Normalizes a category name by trimming whitespace and replacing multiple spaces with a single space.
   *
   * @param categoryName the category name to normalize; can be {@code null}
   * @return the normalized category name, or {@code null} if the input was {@code null}
   */
  private String normalizedCategoryName(final String categoryName) {
    if (categoryName == null) {
      return null;
    }
    return categoryName.trim().replaceAll("\\s+", " ");
  }
}
