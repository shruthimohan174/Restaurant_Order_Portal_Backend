package com.restaurants.exception;

/**
 * Exception thrown when a restaurant is not found.
 */
public class RestaurantNotFoundException extends RuntimeException {

  /**
   * Constructs a new RestaurantNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public RestaurantNotFoundException(String message) {
    super(message);
  }
}
