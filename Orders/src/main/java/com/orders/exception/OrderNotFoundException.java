package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(OrderNotFoundException.class);

  public OrderNotFoundException(String message) {
    super(message);
    logger.error("Order not found: {}", message);
  }
}
