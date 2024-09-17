package com.users.exception;

/**
 * Exception thrown when an operation is attempted without proper authorization.
 * <p>
 * This exception is used to signal that the user does not have the necessary
 * permissions or credentials to perform the requested operation.
 * </p>
 */
public class UnauthorizedAccessException extends RuntimeException {

  /**
   * Constructs a new UnauthorizedException with the specified detail message.
   *
   * @param message the detail message
   */
  public UnauthorizedAccessException(final String message) {
    super(message);
  }
}
