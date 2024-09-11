package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a cart is not found.
 */
public class CartNotFoundException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(CartNotFoundException.class);

  public CartNotFoundException(String message) {
    super(message);
    logger.error("Cart not found: {}", message);
  }
}
