package com.restaurants.exception;

/**
 * Exception thrown when a food item is not found.
 */
public class FoodItemNotFoundException extends RuntimeException {

  /**
   * Constructs a new FoodItemNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
 public FoodItemNotFoundException(String message){
    super(message);
  }
}
