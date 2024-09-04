package com.restaurants.exception;

public class FoodItemAlreadyExistsException extends RuntimeException {
  public FoodItemAlreadyExistsException(String message) {
    super(message);
  }
}
