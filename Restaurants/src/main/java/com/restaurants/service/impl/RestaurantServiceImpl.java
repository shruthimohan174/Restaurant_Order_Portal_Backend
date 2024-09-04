package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.MessageOutDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.dto.outdto.UserOutDto;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.InvalidFileTypeException;
import com.restaurants.exception.NotRestaurantOwnerException;
import com.restaurants.exception.RestaurantNotFoundException;
import com.restaurants.repositories.RestaurantRepository;
import com.restaurants.service.RestaurantService;
import com.restaurants.service.UserFeignClient;
import com.restaurants.utils.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Autowired
  private UserFeignClient userFeignClient;

  /**
   * Adds a new restaurant.
   *
   * @param request the details of the restaurant to be added
   * @param image   the image representing the restaurant
   * @return the created restaurant
   */
  @Override
  @Transactional
  public MessageOutDto addRestaurant(RestaurantInDto request, MultipartFile image) {
    logger.info("Adding restaurant: {}", request);
    UserOutDto user = userFeignClient.getUserById(request.getUserId());
    if (user.getUserRole() != UserRole.RESTAURANT_OWNER) {
      logger.error("User {} is not a restaurant owner", request.getUserId());
      throw new NotRestaurantOwnerException(RestaurantConstants.NOT_RESTAURANT_OWNER);
    }
    Restaurant restaurant = DtoConversion.convertRestaurantRequestToRestaurant(request);

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFile(image);
        restaurant.setImageData(image.getBytes());
      } catch (IOException e) {
        logger.error("Error occurred while processing the image file for restaurant: {}", request.getRestaurantName(), e);
        throw new NotRestaurantOwnerException(RestaurantConstants.ERROR_PROCESSING_IMAGE);
      }
    }

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    logger.info("Restaurant added successfully: {}", savedRestaurant);

    return new MessageOutDto(RestaurantConstants.RESTAURANT_ADDED_SUCCESSFULLY);
  }

  /**.
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
  @Transactional(readOnly = true)
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
   * Retrieves the image data for a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the image data as a byte array
   */
  @Override
  public byte[] getRestaurantImage(Integer id) {
    logger.info("Fetching image for restaurant with ID: {}", id);
    Restaurant restaurant = findRestaurantById(id);
    return restaurant.getImageData();
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

  @Override
  public void validateImageFile(MultipartFile image) {
    String contentType = image.getContentType();
    if (contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
      throw new InvalidFileTypeException(RestaurantConstants.INVALID_FILE_TYPE);
    }
  }
}
