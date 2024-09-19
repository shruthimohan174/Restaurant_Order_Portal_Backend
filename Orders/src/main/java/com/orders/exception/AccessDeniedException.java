package com.orders.exception;

/**
 * Exception thrown when a user tries to access a resource they are not authorized to access.
 */
public class AccessDeniedException extends RuntimeException {

  /**
   * Constructs a new AccessDeniedException with the specified detail message.
   *
   * @param message The detail message.
   */
  public AccessDeniedException(final String message) {
    super(message);
  }
}
