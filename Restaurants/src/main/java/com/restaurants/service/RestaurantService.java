package com.restaurants.service;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing restaurants.
 */
public interface RestaurantService {

  /**
   * Adds a new restaurant.
   *
   * @param request the details of the restaurant to be added
   * @param image   the image representing the restaurant
   * @return the created restaurant
   */
  RestaurantOutDto addRestaurant(RestaurantInDto request, MultipartFile image);

  /**
   * Retrieves all restaurants.
   *
   * @return a list of all restaurants
   */
  List<RestaurantOutDto> getALlRestaurants();

  /**
   * Retrieves a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the restaurant with the specified ID
   */
  Restaurant findRestaurantById(Integer id);

  /**
   * Retrieves all restaurants associated with a specific user.
   *
   * @param userId the ID of the user
   * @return a list of restaurants associated with the specified user
   */
  List<RestaurantOutDto> getALlRestaurantsByUserId(Integer userId);

}
