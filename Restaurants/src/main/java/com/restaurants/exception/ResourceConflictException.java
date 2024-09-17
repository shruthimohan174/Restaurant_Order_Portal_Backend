package com.restaurants.exception;

/**
 * Exception thrown when there is a conflict with the current state of the resource.
 */
public class ResourceConflictException extends RuntimeException {

  /**
   * Constructs a new ConflictException with the specified detail message.
   *
   * @param message The detail message.
   */
  public ResourceConflictException(final String message) {
    super(message);
  }
}
