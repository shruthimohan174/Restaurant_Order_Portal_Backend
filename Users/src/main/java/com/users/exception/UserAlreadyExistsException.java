package com.users.exception;

/**
 * Exception thrown when attempting to register a user that already exists.
 * <p>
 * Indicates that a user with the provided details already exists in the system.
 * </p>
 */
public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
