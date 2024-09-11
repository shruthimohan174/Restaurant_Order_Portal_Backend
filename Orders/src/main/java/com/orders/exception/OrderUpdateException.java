package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when an error occurs during order update.
 */
public class OrderUpdateException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(OrderUpdateException.class);

  public OrderUpdateException(String message) {
    super(message);
    logger.error("Order update error: {}", message);
  }
}
