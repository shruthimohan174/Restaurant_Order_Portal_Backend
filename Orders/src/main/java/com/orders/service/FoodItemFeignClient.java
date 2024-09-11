package com.orders.service;

import com.orders.dto.FoodItemOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foodItemClient", url = "http://localhost:8082")
public interface FoodItemFeignClient {

  /**
   * Retrieves food item details by food item ID.
   *
   * @param id the ID of the food item
   * @return the {@link FoodItemOutDto} containing food item details
   */
  @GetMapping("/foodItem/{id}")
  FoodItemOutDto getFoodItemById(@PathVariable("id") Integer id);

  /**
   * Retrieves restaurant details by restaurant ID.
   *
   * @param id the ID of the restaurant
   * @return the {@link FoodItemOutDto} containing restaurant details
   */
  @GetMapping("/restaurant/{id}")
  FoodItemOutDto getRestaurantById(@PathVariable("id") Integer id);
}
