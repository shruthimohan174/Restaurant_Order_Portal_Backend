package com.restaurants.exception;

/**
 * Exception thrown when a food item already exists.
 */
public class FoodItemAlreadyExistsException extends RuntimeException {
  public FoodItemAlreadyExistsException(String message) {
    super(message);
  }
}
