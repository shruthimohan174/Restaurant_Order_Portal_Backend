package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(CustomerNotFoundException.class);

  public CustomerNotFoundException(String message) {
    super(message);
    logger.error("Customer not found: {}", message);
  }
}
