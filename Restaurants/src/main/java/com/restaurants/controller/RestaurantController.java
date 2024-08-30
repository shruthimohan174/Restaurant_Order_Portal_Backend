package com.restaurants.controller;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * Controller for managing restaurants.
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

  private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

  @Autowired
  private RestaurantService restaurantService;

  /**
   * Creates a new restaurant.
   *
   * @param request the details of the restaurant to be added
   * @param image   the image representing the restaurant
   * @return the created restaurant
   */
  @PostMapping("/restaurant/add")
  public ResponseEntity<RestaurantOutDto> addRestaurant(@Valid @ModelAttribute RestaurantInDto request,
                                                        @RequestParam("image") MultipartFile image) {
    logger.info("Received request to add restaurant: {}", request);
    RestaurantOutDto response = restaurantService.addRestaurant(request, image);
    logger.info("Restaurant added successfully: {}", response);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Retrieves all restaurants.
   *
   * @return a list of all restaurants
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
   * @param id the ID of the restaurant
   * @return the restaurant with the specified ID
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
   * @param userId the ID of the user
   * @return a list of restaurants associated with the specified user
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RestaurantOutDto>> getAllRestaurantByUserId(@PathVariable Integer userId) {
    logger.info("Retrieving restaurants for user ID: {}", userId);
    List<RestaurantOutDto> response = restaurantService.getALlRestaurantsByUserId(userId);
    logger.info("Retrieved {} restaurants for user ID: {}", response.size(), userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
