package com.orders.service.impl;

import com.orders.dto.FoodItemOutDto;
import com.orders.service.RestaurantFeignClient;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Fallback implementation of {@link RestaurantFeignClient} to handle cases where the Restaurant service is unavailable.
 * <p>
 * This class provides default responses for the methods in {@link RestaurantFeignClient} when the main service fails.
 * </p>
 */
@Slf4j
public class RestaurantFeignClientFallback implements RestaurantFeignClient {

  /**
   * Fallback method for retrieving a restaurant by its ID.
   * <p>
   * Provides a default response when the Restaurant service is down or unreachable.
   * </p>
   *
   * @param id The ID of the restaurant.
   * @return A {@link FoodItemOutDto} object with default values.
   */
  @Override
  public FoodItemOutDto getRestaurantById(final Integer id) {
    log.error("Fallback method for getRestaurantById called. RestaurantId: {}", id);
    FoodItemOutDto fallbackRestaurant = new FoodItemOutDto();
    fallbackRestaurant.setId(id);
    fallbackRestaurant.setItemName("Fallback Restaurant");
    fallbackRestaurant.setPrice(BigDecimal.ZERO);
    return fallbackRestaurant;
  }

  /**
   * Fallback method for retrieving a food item by its ID.
   * <p>
   * Provides a default response when the Restaurant service is down or unreachable.
   * </p>
   *
   * @param id The ID of the food item.
   * @return A {@link FoodItemOutDto} object with default values.
   */
  @Override
  public FoodItemOutDto getFoodItemById(final Integer id) {
    log.error("Fallback method for getFoodItemById called. FoodItemId: {}", id);
    FoodItemOutDto fallbackFoodItem = new FoodItemOutDto();
    fallbackFoodItem.setId(id);
    fallbackFoodItem.setItemName("Fallback Food Item");
    fallbackFoodItem.setPrice(BigDecimal.ZERO);
    return fallbackFoodItem;
  }
}
