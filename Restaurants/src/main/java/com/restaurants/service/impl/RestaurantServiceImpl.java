package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.RestaurantInDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.dto.RestaurantOutDto;
import com.restaurants.dto.UserOutDto;
import com.restaurants.converter.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.AccessDeniedException;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.repositories.RestaurantRepository;
import com.restaurants.service.RestaurantService;
import com.restaurants.service.UserFeignClient;
import com.restaurants.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

  /**
   * The maximum allowed file size for uploads, set to 5 megabytes (5 MB).
   */
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

  /**
   * Service layer dependency for restaurant repository-related operations.
   */
  @Autowired
  private RestaurantRepository restaurantRepository;

  /**
   * Service layer dependency for userFeignClient-related operations.
   */
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
  public MessageOutDto addRestaurant(final RestaurantInDto request, final MultipartFile image) {
    log.info("Adding restaurant");

    UserOutDto user = userFeignClient.getUserById(request.getUserId());
    if (user.getUserRole() != UserRole.RESTAURANT_OWNER) {
      log.error("User {} is not a restaurant owner", request.getUserId());
      throw new AccessDeniedException(RestaurantConstants.NOT_RESTAURANT_OWNER);
    }

    String normalizedRestaurantName = request.getRestaurantName().trim().toLowerCase();

    if (restaurantRepository.existsByRestaurantNameIgnoreCase(normalizedRestaurantName)) {
      log.error("Restaurant name {} already exists", request.getRestaurantName());
      throw new InvalidRequestException(RestaurantConstants.RESTAURANT_NAME_EXISTS);
    }

    Restaurant restaurant = DtoConversion.convertRestaurantRequestToRestaurant(request);
    restaurant.setRestaurantName(normalizedRestaurantName);

    if (image != null && !image.isEmpty()) {
      try {
        validateImageFile(image);
        restaurant.setImageData(image.getBytes());
      } catch (IOException e) {
        log.error("Error occurred while processing the image file for restaurant: {}", request.getRestaurantName(), e);
        throw new AccessDeniedException(RestaurantConstants.ERROR_PROCESSING_IMAGE);
      }
    }

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    log.info("Restaurant added successfully");

    return new MessageOutDto(RestaurantConstants.RESTAURANT_ADDED_SUCCESSFULLY);
  }
  /**
   * .
   * Retrieves all restaurants.
   *
   * @return a list of all restaurants
   */
  @Override
  public List<RestaurantOutDto> getALlRestaurants() {
    log.info("Retrieving all restaurants");
    List<Restaurant> restaurants = restaurantRepository.findAll();
    List<RestaurantOutDto> responseList = new ArrayList<>();
    for (Restaurant restaurant : restaurants) {
      responseList.add(DtoConversion.convertRestaurantToRestaurantResponse(restaurant));
    }
    log.info("Retrieved {} restaurants", responseList.size());
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
  public List<RestaurantOutDto> getALlRestaurantsByUserId(final Integer userId) {
    log.info("Retrieving restaurants for user ID: {}", userId);
    List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
    List<RestaurantOutDto> responseList = new ArrayList<>();
    for (Restaurant restaurant : restaurants) {
      responseList.add(DtoConversion.convertRestaurantToRestaurantResponse(restaurant));
    }
    log.info("Retrieved {} restaurants for user ID: {}", responseList.size(), userId);
    return responseList;
  }

  /**
   * Retrieves the image data for a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the image data as a byte array
   */
  @Override
  public byte[] getRestaurantImage(final Integer id) {
    log.info("Fetching image for restaurant with ID: {}", id);
    Restaurant restaurant = findRestaurantById(id);
    return restaurant.getImageData();
  }

  /**
   * Finds a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return the restaurant
   */
  @Override
  public Restaurant findRestaurantById(final Integer id) {
    log.info("Finding restaurant by ID: {}", id);
    return restaurantRepository.findById(id).orElseThrow(() -> {
      log.error("Restaurant not found for ID: {}", id);

      return new ResourceNotFoundException(RestaurantConstants.RESTAURANT_NOT_FOUND);
    });
  }
  /**
   * Validates an image file to ensure it meets the required criteria.
   *
   * @param image the image file to validate; must not be {@code null}
   * @throws InvalidRequestException if the file type is not JPEG or PNG, or if the file size exceeds the maximum limit
   */
  @Override
  public void validateImageFile(final MultipartFile image) {
    String contentType = image.getContentType();

    if (contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
      throw new InvalidRequestException(RestaurantConstants.INVALID_FILE_TYPE);
    }

    if (image.getSize() > MAX_FILE_SIZE) {
      throw new InvalidRequestException(RestaurantConstants.FILE_SIZE_EXCEEDED);
    }
  }
}
