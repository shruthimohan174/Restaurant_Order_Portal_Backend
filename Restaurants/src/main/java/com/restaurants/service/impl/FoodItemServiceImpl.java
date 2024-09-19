package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.FoodItemInDto;
import com.restaurants.dto.FoodItemUpdateInDto;
import com.restaurants.dto.FoodItemOutDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.converter.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.FoodItem;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.ResourceConflictException;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.repositories.FoodItemRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.FoodItemService;
import com.restaurants.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link FoodItemService} interface.
 * Provides methods to manage food items.
 */
@Service
@Slf4j
public class FoodItemServiceImpl implements FoodItemService {

  /**
   * The maximum allowed file size for uploads, set to 5 megabytes (5 MB).
   */
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

  /**
   * Service layer dependency for foodItem-related operations.
   */
  @Autowired
  private FoodItemRepository foodItemRepository;

  /**
   * Service layer dependency for restaurant-related operations.
   */
  @Autowired
  private RestaurantService restaurantService;

  /**
   * Service layer dependency for category-related operations.
   */
  @Autowired
  private FoodCategoryService foodCategoryService;

  /**
   * Adds a new food item.
   *
   * @param request the details of the food item to be added
   * @param image   the image representing the food item
   * @return the success message wrapped in {@link MessageOutDto}
   */
  @Override
  public MessageOutDto addFoodItems(final FoodItemInDto request, final MultipartFile image) {
    log.info("Adding food item");
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
    FoodCategory category = foodCategoryService.findCategoryById(request.getCategoryId());

    String normalizedItemName = normalizeItemName(request.getItemName());
    if (itemExistsInRestaurant(restaurant.getId(), normalizedItemName)) {
      throw new ResourceConflictException(RestaurantConstants.ITEM_ALREADY_EXISTS);
    }

    FoodItem foodItem = DtoConversion.convertFoodItemRequestToFoodItem(request);
    foodItem.setItemName(normalizedItemName);

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFile(image);
        foodItem.setImageData(image.getBytes());
      } catch (IOException e) {
        log.error("Error occurred while processing the image file for food item: {}", normalizedItemName, e);
      }
    }

    foodItemRepository.save(foodItem);
    log.info("Food item added successfully: {}", normalizedItemName);
    return new MessageOutDto(RestaurantConstants.FOOD_ITEM_ADDED_SUCCESSFULLY);
  }
  /**
   * Updates an existing food item.
   *
   * @param request the updated details of the food item
   * @param id      the ID of the food item to be updated
   * @return the success message wrapped in {@link MessageOutDto}
   */
  @Override
  public MessageOutDto updateFoodItems(final FoodItemUpdateInDto request, final Integer id,
                                       final MultipartFile image) {
    log.info("Updating food item with ID: {}", id);
    FoodItem existingItem = findFoodItemsById(id);

    String normalizedItemName = normalizeItemName(request.getItemName());
    if (!existingItem.getItemName().equals(normalizedItemName)
      && itemExistsInRestaurant(existingItem.getRestaurantId(), normalizedItemName)) {
      throw new ResourceConflictException(RestaurantConstants.ITEM_ALREADY_EXISTS);
    }

    existingItem.setItemName(normalizedItemName);
    existingItem.setDescription(request.getDescription());
    existingItem.setPrice(request.getPrice());
    existingItem.setIsVeg(request.getIsVeg());

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFile(image);
        existingItem.setImageData(image.getBytes());
      } catch (IOException e) {
        log.error("Error occurred while processing the image file for food item: {}", normalizedItemName, e);
      }
    }

    foodItemRepository.save(existingItem);
    log.info("Food item updated successfully: {}", normalizedItemName);
    return new MessageOutDto(RestaurantConstants.FOOD_ITEM_UPDATED_SUCCESSFULLY);
  }

  /**
   * Finds a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the food item
//   * @throws FoodItemNotFoundException if the food item is not found
   */
  @Override
  public FoodItem findFoodItemsById(final Integer id) {
    log.info("Finding food item by ID: {}", id);
    return foodItemRepository.findById(id).orElseThrow(() -> {
      log.error("Food item not found for ID: {}", id);
      return new ResourceNotFoundException(RestaurantConstants.FOOD_ITEM_NOT_FOUND);
    });
  }

  /**
   * Retrieves all food items.
   *
   * @return a list of all food items
   */
  @Override
  public List<FoodItemOutDto> getAllFoodItems() {
    log.info("Retrieving all food items");
    List<FoodItem> foodItemList = foodItemRepository.findAll();
    List<FoodItemOutDto> foodItemResponsesList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      foodItemResponsesList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    log.info("Retrieved {} food items", foodItemResponsesList.size());
    return foodItemResponsesList;
  }

  /**
   * Retrieves food items for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food items for the given restaurant
   */
  @Override
  public List<FoodItemOutDto> getAllByRestaurantId(final Integer restaurantId) {
    log.info("Retrieving food items for restaurant ID: {}", restaurantId);
    List<FoodItem> foodItemList = foodItemRepository.findByRestaurantId(restaurantId);
    List<FoodItemOutDto> responseList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      responseList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    log.info("Retrieved {} food items for restaurant ID: {}", responseList.size(), restaurantId);
    return responseList;
  }

  /**
   * Retrieves food items for a specific category.
   *
   * @param categoryId the ID of the category
   * @return a list of food items for the given category
   */
  @Override
  public List<FoodItemOutDto> getAllByCategoryId(final Integer categoryId) {
    log.info("Retrieving food items for category ID: {}", categoryId);
    List<FoodItem> foodItemList = foodItemRepository.findByCategoryId(categoryId);
    List<FoodItemOutDto> responseList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      responseList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    log.info("Retrieved {} food items for category ID: {}", responseList.size(), categoryId);
    return responseList;
  }

  /**
   * Retrieves the image data for a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the image data as a byte array
   */
  @Override
  public byte[] getFoodItemImage(final Integer id) {
    log.info("Fetching image for Food Item with ID: {}", id);
    FoodItem foodItem = findFoodItemsById(id);
    return foodItem.getImageData();
  }

  /**
   * Checks if a food item with the same name exists in the same restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @param itemName     the name of the food item
   * @return true if the item exists, false otherwise
   */
  public boolean itemExistsInRestaurant(final Integer restaurantId, final String itemName) {
    String normalizedItemName = normalizeItemName(itemName);
    return foodItemRepository.existsByRestaurantIdAndItemNameIgnoreCase(restaurantId, normalizedItemName);
  }
  /**
   * Validates the image file to ensure it is of a valid type (JPEG or PNG).
   *
   * @param image the image file to validate
   */
  @Override
  public void validateImageFile(final MultipartFile image) {
    String contentType = image.getContentType();
    if (contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
      throw new InvalidRequestException(RestaurantConstants.INVALID_FILE_TYPE);
    }

    if (image.getSize() > MAX_FILE_SIZE) {
      throw new InvalidRequestException(RestaurantConstants.FILE_SIZE_EXCEEDED);
    }
  }
  /**
   * Normalizes a category name by trimming whitespace and replacing multiple spaces with a single space.
   *
   * @param itemName the category name to normalize; can be {@code null}
   * @return the normalized category name, or {@code null} if the input was {@code null}
   */
  private String normalizeItemName(final String itemName) {
    return itemName.trim().replaceAll("\\s+", "").toLowerCase();
  }

}
