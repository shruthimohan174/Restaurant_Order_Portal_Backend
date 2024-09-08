package com.orders.service;

import com.orders.dto.outdto.RestaurantOutDto;
import com.orders.dto.outdto.UserOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "${restaurant.service.url}")
public interface RestaurantFeignClient {

  @GetMapping("/restaurant/{id}")
  RestaurantOutDto getRestaurantById(@PathVariable("id") Integer id);
}