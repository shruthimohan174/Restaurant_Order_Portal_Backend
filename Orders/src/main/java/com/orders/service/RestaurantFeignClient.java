package com.orders.service;

import com.orders.dto.FoodItemOutDto;
import com.orders.service.impl.RestaurantFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "${restaurant.microservice.url}", fallback = RestaurantFeignClientFallback.class)
public interface RestaurantFeignClient {

  /**
   * Retrieves restaurant details by restaurant ID.
   *
   * @param id the ID of the restaurant
   * @return the {@link FoodItemOutDto} containing restaurant details
   */
  @GetMapping("/restaurant/{id}")
  FoodItemOutDto getRestaurantById(@PathVariable("id") Integer id);

  /**
   * Retrieves food item details by food item ID.
   *
   * @param id the ID of the food item
   * @return the {@link FoodItemOutDto} containing food item details
   */
  @GetMapping("/foodItem/{id}")
  FoodItemOutDto getFoodItemById(@PathVariable("id") Integer id);
}
