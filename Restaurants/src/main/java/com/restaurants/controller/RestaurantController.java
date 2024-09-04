package com.restaurants.controller;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.MessageOutDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing restaurant-related operations.
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

  private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

  @Autowired
  private RestaurantService restaurantService;

  /**
   * Creates a new restaurant with the provided details and image.
   *
   * @param request the details of the restaurant to be added
   * @param image   the image representing the restaurant
   * @return a {@link ResponseEntity} containing a {@link MessageOutDto} with the result of the operation
   */
  @PostMapping("/add")
  public ResponseEntity<MessageOutDto> addRestaurant(@Valid @ModelAttribute RestaurantInDto request,
                                                     @RequestParam("image") MultipartFile image) {
    logger.info("Received request to add restaurant: {}", request);
    MessageOutDto message = restaurantService.addRestaurant(request, image);
    logger.info(String.valueOf(message));

    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  /**
   * Retrieves all restaurants.
   *
   * @return a {@link ResponseEntity} containing a list of {@link RestaurantOutDto} with all restaurants
   */
  @GetMapping("")
  public ResponseEntity<List<RestaurantOutDto>> getAllRestaurants() {
    logger.info("Retrieving all restaurants");
    List<RestaurantOutDto> response = restaurantService.getALlRestaurants();
    logger.info("Retrieved {} restaurants", response.size());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves a restaurant by its ID.
   *
   * @param id the ID of the restaurant to retrieve
   * @return a {@link ResponseEntity} containing the {@link Restaurant} with the specified ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
    logger.info("Retrieving restaurant with ID: {}", id);
    Restaurant response = restaurantService.findRestaurantById(id);
    logger.info("Retrieved restaurant: {}", response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves all restaurants associated with a specific user.
   *
   * @param userId the ID of the user whose restaurants to retrieve
   * @return a {@link ResponseEntity} containing a list of {@link RestaurantOutDto} associated with the specified user
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RestaurantOutDto>> getAllRestaurantByUserId(@PathVariable Integer userId) {
    logger.info("Retrieving restaurants for user ID: {}", userId);
    List<RestaurantOutDto> response = restaurantService.getALlRestaurantsByUserId(userId);
    logger.info("Retrieved {} restaurants for user ID: {}", response.size(), userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves the image data for a restaurant by its ID.
   *
   * @param id the ID of the restaurant
   * @return a {@link ResponseEntity} containing the image data as a byte array
   */
  @GetMapping("/{id}/image")
  public ResponseEntity<byte[]> getRestaurantImage(@PathVariable Integer id) {
    logger.info("Retrieving image for restaurant with ID: {}", id);
    byte[] imageData = restaurantService.getRestaurantImage(id);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
  }
}
