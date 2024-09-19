package com.users.exception;

/**
 * Exception thrown when a request is invalid or contains invalid data.
 * <p>
 * This exception is used to signal that the request cannot be processed
 * due to some issues with the data or parameters provided.
 * </p>
 */
public class InvalidRequestException extends RuntimeException {

  /**
   * Constructs a new InvalidRequestException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidRequestException(final String message) {
    super(message);
  }
}
