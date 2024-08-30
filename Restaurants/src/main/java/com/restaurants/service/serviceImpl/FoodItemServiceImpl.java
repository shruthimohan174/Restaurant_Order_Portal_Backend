package com.restaurants.service.serviceImpl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.FoodItem;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.FoodItemNotFoundException;
import com.restaurants.exception.ImageProcessingException;
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
 private  FoodItemRepository foodItemRepository;

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private FoodCategoryService foodCategoryService;

  /**
   * Adds a new food item.
   *
   * @param request the food item data to be added
   * @return the added food item
   */
  @Override
  public FoodItemOutDto addFoodItems(FoodItemInDto request, MultipartFile image) {
    logger.info("Adding food item: {}", request);
    Restaurant restaurantResponse=restaurantService.findRestaurantById(request.getRestaurantId());
    FoodCategory categoryResponse=foodCategoryService.findCategoryById(request.getCategoryId());
    FoodItem foodItem= DtoConversion.convertFoodItemRequestToFoodItem(request);
    if (image != null && !image.isEmpty()) {
      try{
        foodItem.setImageData(request.getImage().getBytes());
      }catch(IOException e){
        throw new ImageProcessingException(RestaurantConstants.ERROR_PROCESSING_IMAGE);
      }
    }
    FoodItem saved=foodItemRepository.save(foodItem);
    logger.info("Food item added successfully: {}", saved);
    return  DtoConversion.convertFoodItemToFoodItemResponse(saved);
  }

  /**
   * Updates an existing food item.
   *
   * @param request the food item data to update
   * @param id the ID of the food item to update
   * @return the updated food item
   */
  @Override
  public FoodItemOutDto updateFoodItems(FoodItemInDto request, Integer id) {
    logger.info("Updating food item with ID: {}", id);
    FoodItem existingItems=findFoodItemsById(id);
    DtoConversion.updateFoodItemRequest(request,existingItems);
    FoodItem updatedItems=foodItemRepository.save(existingItems);
    logger.info("Food item updated successfully: {}", updatedItems);
    return DtoConversion.convertFoodItemToFoodItemResponse(updatedItems);
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
    return foodItemRepository.findById(id).orElseThrow(()->{
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
    List<FoodItem>foodItemList=foodItemRepository.findByRestaurantId(restaurantId);
    List<FoodItemOutDto> responseList=new ArrayList<>();
    for(FoodItem foodItem:foodItemList){
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
    List<FoodItem>foodItemList=foodItemRepository.findByCategoryId(categoryId);
    List<FoodItemOutDto> responseList=new ArrayList<>();
    for(FoodItem foodItem:foodItemList){
      responseList.add(DtoConversion.convertFoodItemToFoodItemResponse(foodItem));
    }
    logger.info("Retrieved {} food items for category ID: {}", responseList.size(), categoryId);
    return responseList;
  }
}
