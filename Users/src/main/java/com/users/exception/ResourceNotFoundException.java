package com.users.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Constructs a new ResourceNotFoundException with the specified detail message.
   *
   * @param message The detail message.
   */
  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
