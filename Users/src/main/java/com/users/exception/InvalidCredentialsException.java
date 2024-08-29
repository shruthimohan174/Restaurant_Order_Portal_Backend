package com.users.exception;

/**
 * Exception thrown when invalid credentials are provided during user authentication.
 * <p>
 * Indicates that the provided login details are incorrect or invalid.
 * </p>
 */
public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
