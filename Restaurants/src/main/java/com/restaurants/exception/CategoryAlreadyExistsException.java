package com.restaurants.exception;

/**
 * Exception thrown when a food category already exists.
 */
public class CategoryAlreadyExistsException extends RuntimeException {
  public CategoryAlreadyExistsException(String message) {
    super(message);
  }
}
