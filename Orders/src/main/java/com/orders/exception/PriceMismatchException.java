package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when there is a price mismatch.
 */
public class PriceMismatchException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(PriceMismatchException.class);

  public PriceMismatchException(String message) {
    super(message);
    logger.error("Price mismatch: {}", message);
  }
}
