package com.restaurants.service;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.MessageOutDto;
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
   * @return a {@link MessageOutDto} containing the success message
   */
  MessageOutDto addRestaurant(RestaurantInDto request, MultipartFile image);

  /**
   * Retrieves all restaurants.
   *
   * @return a list of {@link RestaurantOutDto} representing all restaurants
   */
  List<RestaurantOutDto> getALlRestaurants();

  /**
   * Retrieves a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the {@link Restaurant} with the specified ID
   */
  Restaurant findRestaurantById(Integer id);

  /**
   * Retrieves all restaurants associated with a specific user.
   *
   * @param userId the ID of the user
   * @return a list of {@link RestaurantOutDto} representing restaurants associated with the specified user
   */
  List<RestaurantOutDto> getALlRestaurantsByUserId(Integer userId);

  /**
   * Retrieves the image data for a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the image data as a byte array
   */
  byte[] getRestaurantImage(Integer id);

  /**
   * Validates the image file to ensure it is of a valid type (JPEG or PNG).
   *
   * @param image the image file to validate
   */
  void validateImageFile(MultipartFile image);
}
