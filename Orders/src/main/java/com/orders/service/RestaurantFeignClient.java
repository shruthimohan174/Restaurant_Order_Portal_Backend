package com.orders.service;

import com.orders.dto.FoodItemOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "${restaurant.microservice.url}")
public interface RestaurantFeignClient {

  /**
   * Retrieves restaurant details by restaurant ID.
   *
   * @param id the ID of the restaurant
   * @return the {@link FoodItemOutDto} containing restaurant details
   */
  @GetMapping("/restaurant/{id}")
  FoodItemOutDto getRestaurantById(@PathVariable("id") Integer id);
}
