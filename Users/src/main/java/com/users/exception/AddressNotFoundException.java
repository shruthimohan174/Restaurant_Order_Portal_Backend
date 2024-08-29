package com.users.exception;

/**
 * Exception thrown when an address is not found.
 * <p>
 * Indicates that a requested address does not exist in the system.
 * </p>
 */
public class AddressNotFoundException extends RuntimeException {
  public AddressNotFoundException(String message) {
    super(message);
  }
}
