package com.restaurants.service.impl;

import com.restaurants.dto.UserOutDto;
import com.restaurants.service.UserFeignClient;
import com.restaurants.utils.UserRole;
import lombok.extern.slf4j.Slf4j;

/**
 * Fallback implementation of {@link UserFeignClient} to handle scenarios where the User service is unavailable.
 * <p>
 * This class provides default responses for the methods in {@link UserFeignClient} when the main service fails.
 * </p>
 */
@Slf4j
public class UserFeignClientFallback implements UserFeignClient {

  /**
   * Fallback method for retrieving a user by their ID.
   * <p>
   * Provides a default response when the User service is down or unreachable.
   * </p>
   *
   * @param id The ID of the user.
   * @return A {@link UserOutDto} object with default values.
   */
  @Override
  public UserOutDto getUserById(final Integer id) {
    log.error("Fallback triggered for getUserById with id: {}", id);
    UserOutDto fallbackUser = new UserOutDto();
    fallbackUser.setId(id);
    fallbackUser.setUserRole(UserRole.RESTAURANT_OWNER);
    return fallbackUser;
  }
}
