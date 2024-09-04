package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.indto.FoodItemUpdateInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.dto.outdto.MessageOutDto;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.FoodItem;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.FoodItemAlreadyExistsException;
import com.restaurants.exception.FoodItemNotFoundException;
import com.restaurants.exception.InvalidFileTypeException;
import com.restaurants.repositories.FoodItemRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.FoodItemService;
import com.restaurants.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FoodItemServiceImpl implements FoodItemService {

  private static final Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);

  @Autowired
  private FoodItemRepository foodItemRepository;

  @Autowired
  private RestaurantService restaurantService;

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
  public MessageOutDto addFoodItems(FoodItemInDto request, MultipartFile image) {
    logger.info("Adding food item: {}", request);
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
    FoodCategory category = foodCategoryService.findCategoryById(request.getCategoryId());

    if (itemExistsInRestaurant(restaurant.getId(), request.getItemName())) {
      throw new FoodItemAlreadyExistsException(RestaurantConstants.ITEM_ALREADY_EXISTS);
    }

    FoodItem foodItem = DtoConversion.convertFoodItemRequestToFoodItem(request);

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFile(image);
        foodItem.setImageData(image.getBytes());
      } catch (IOException e) {
        logger.error("Error occurred while processing the image file for food item: {}", request.getItemName(), e);
      }
    }

    foodItemRepository.save(foodItem);
    logger.info("Food item added successfully: {}", request.getItemName());
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
  public MessageOutDto updateFoodItems(FoodItemUpdateInDto request, Integer id) {
    logger.info("Updating food item with ID: {}", id);
    FoodItem existingItem = findFoodItemsById(id);

    if (!existingItem.getItemName().equals(request.getItemName()) &&
      itemExistsInRestaurant(existingItem.getRestaurantId(), request.getItemName())) {
      throw new FoodItemAlreadyExistsException(RestaurantConstants.ITEM_ALREADY_EXISTS);
    }

    existingItem.setItemName(request.getItemName());
    existingItem.setDescription(request.getDescription());
    existingItem.setPrice(request.getPrice());

    foodItemRepository.save(existingItem);
    logger.info("Food item updated successfully: {}", request.getItemName());
    return new MessageOutDto(RestaurantConstants.FOOD_ITEM_UPDATED_SUCCESSFULLY);
  }

  /**
   * Finds a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the food item
   * @throws FoodItemNotFoundException if the food item is not found
   */
  @Override
  public FoodItem findFoodItemsById(Integer id) {
    logger.info("Finding food item by ID: {}", id);
    return foodItemRepository.findById(id).orElseThrow(() -> {
      logger.error("Food item not found for ID: {}", id);
      return new FoodItemNotFoundException(RestaurantConstants.FOOD_ITEM_NOT_FOUND);
    });
  }

  /**
   * Retrieves all food items.
   *
   * @return a list of all food items
   */
  @Override
  public List<FoodItemOutDto> getAllFoodItems() {
    logger.info("Retrieving all food items");
    List<FoodItem> foodItemList = foodItemRepository.findAll();
    List<FoodItemOutDto> foodItemResponsesList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      foodItemResponsesList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    logger.info("Retrieved {} food items", foodItemResponsesList.size());
    return foodItemResponsesList;
  }

  /**
   * Retrieves food items for a specific restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return a list of food items for the given restaurant
   */
  @Override
  public List<FoodItemOutDto> getAllByRestaurantId(Integer restaurantId) {
    logger.info("Retrieving food items for restaurant ID: {}", restaurantId);
    List<FoodItem> foodItemList = foodItemRepository.findByRestaurantId(restaurantId);
    List<FoodItemOutDto> responseList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      responseList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    logger.info("Retrieved {} food items for restaurant ID: {}", responseList.size(), restaurantId);
    return responseList;
  }

  /**
   * Retrieves food items for a specific category.
   *
   * @param categoryId the ID of the category
   * @return a list of food items for the given category
   */
  @Override
  public List<FoodItemOutDto> getAllByCategoryId(Integer categoryId) {
    logger.info("Retrieving food items for category ID: {}", categoryId);
    List<FoodItem> foodItemList = foodItemRepository.findByCategoryId(categoryId);
    List<FoodItemOutDto> responseList = new ArrayList<>();
    for (FoodItem foodItem : foodItemList) {
      responseList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    logger.info("Retrieved {} food items for category ID: {}", responseList.size(), categoryId);
    return responseList;
  }

  /**
   * Retrieves the image data for a food item by its ID.
   *
   * @param id the ID of the food item
   * @return the image data as a byte array
   */
  @Override
  public byte[] getFoodItemImage(Integer id) {
    logger.info("Fetching image for Food Item with ID: {}", id);
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
  @Override
  public boolean itemExistsInRestaurant(Integer restaurantId, String itemName) {
    return foodItemRepository.existsByRestaurantIdAndItemNameIgnoreCase(restaurantId, itemName);
  }

  /**
   * Validates the image file to ensure it is of a valid type (JPEG or PNG).
   *
   * @param image the image file to validate
   * @throws InvalidFileTypeException if the file type is not valid
   */
  @Override
  public void validateImageFile(MultipartFile image) {
    String contentType = image.getContentType();
    if (contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
      throw new InvalidFileTypeException(RestaurantConstants.INVALID_FILE_TYPE);
    }
  }
}
