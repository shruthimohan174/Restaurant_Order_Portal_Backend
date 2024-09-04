package com.restaurants.exception;

public class InvalidFileTypeException extends RuntimeException {
  public InvalidFileTypeException(String message) {
    super(message);
  }
}
