package com.restaurants.service;

import com.restaurants.dto.UserOutDto;
import com.restaurants.service.impl.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with the user service.
 */
@FeignClient(name = "user-service", url = "${user.service.url}", fallback = UserFeignClientFallback.class
)
public interface UserFeignClient {

  /**
   * Retrieves user details by ID.
   *
   * @param id the ID of the user
   * @return the {@link UserOutDto} containing user details
   */
  @GetMapping("/user/{id}")
  UserOutDto getUserById(@PathVariable("id") Integer id);
}
