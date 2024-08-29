package com.users.exception;

/**
 * Exception thrown when a user is not found in the system.
 * <p>
 * Indicates that the requested user does not exist in the database.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
