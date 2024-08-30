package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.RestaurantNotFoundException;
import com.restaurants.repositories.RestaurantRepository;
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
 * Implementation of the {@link RestaurantService} interface.
 * Provides methods to manage restaurants.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

  private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

  @Autowired
  private RestaurantRepository restaurantRepository;

  /**
   * Adds a new restaurant.
   *
   * @param request the details of the restaurant to be added
   * @param image   the image representing the restaurant
   * @return the created restaurant
   */
  @Override
  public RestaurantOutDto addRestaurant(RestaurantInDto request, MultipartFile image) {
    logger.info("Adding restaurant: {}", request);
    Restaurant restaurant = DtoConversion.convertRestaurantRequestToRestaurant(request);

    if (image != null && !image.isEmpty()) {
      try {
        restaurant.setImageData(image.getBytes());
      } catch (IOException e) {
        logger.error("Error occurred while processing the image file for restaurant: {}", request.getRestaurantName(), e);
      }
    }

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    logger.info("Restaurant added successfully: {}", savedRestaurant);
    return DtoConversion.convertRestaurantToRestaurantResponse(savedRestaurant);
  }

  /**
   * Retrieves all restaurants.
   *
   * @return a list of all restaurants
   */
  @Override
  public List<RestaurantOutDto> getALlRestaurants() {
    logger.info("Retrieving all restaurants");
    List<Restaurant> restaurants = restaurantRepository.findAll();
    List<RestaurantOutDto> responseList = new ArrayList<>();
    for (Restaurant restaurant : restaurants) {
      responseList.add(DtoConversion.convertRestaurantToRestaurantResponse(restaurant));
    }
    logger.info("Retrieved {} restaurants", responseList.size());
    return responseList;
  }

  /**
   * Retrieves all restaurants associated with a specific user.
   *
   * @param userId the ID of the user
   * @return a list of restaurants for the given user
   */
  @Override
  public List<RestaurantOutDto> getALlRestaurantsByUserId(Integer userId) {
    logger.info("Retrieving restaurants for user ID: {}", userId);
    List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
    List<RestaurantOutDto> responseList = new ArrayList<>();
    for (Restaurant restaurant : restaurants) {
      responseList.add(DtoConversion.convertRestaurantToRestaurantResponse(restaurant));
    }
    logger.info("Retrieved {} restaurants for user ID: {}", responseList.size(), userId);
    return responseList;
  }

  /**
   * Finds a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the restaurant
   * @throws RestaurantNotFoundException if the restaurant is not found
   */
  @Override
  public Restaurant findRestaurantById(Integer id) {
    logger.info("Finding restaurant by ID: {}", id);
    return restaurantRepository.findById(id).orElseThrow(() -> {
      logger.error("Restaurant not found for ID: {}", id);

      return new RestaurantNotFoundException(RestaurantConstants.RESTAURANT_NOT_FOUND);
    });
  }
}
