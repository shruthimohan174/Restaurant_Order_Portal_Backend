package com.restaurants.exception;

/**
 * Exception thrown when the user is not the owner of the restaurant.
 */
public class NotRestaurantOwnerException extends RuntimeException {
  public NotRestaurantOwnerException(String message) {
    super(message);
  }
}
