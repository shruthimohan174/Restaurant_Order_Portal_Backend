package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when an address is not found.
 */
public class AddressNotFoundException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(AddressNotFoundException.class);

  public AddressNotFoundException(String message) {
    super(message);
    logger.error("Address not found: {}", message);
  }
}
