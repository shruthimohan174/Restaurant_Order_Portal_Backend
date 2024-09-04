package com.restaurants.exception;

/**
 * Exception thrown when the file type is invalid.
 */
public class InvalidFileTypeException extends RuntimeException {
  public InvalidFileTypeException(String message) {
    super(message);
  }
}
