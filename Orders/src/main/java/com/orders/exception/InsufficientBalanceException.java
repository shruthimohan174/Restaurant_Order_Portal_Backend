package com.orders.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when there is insufficient balance in a user's wallet.
 * <p>
 * Indicates that a requested operation cannot be performed due to insufficient wallet balance.
 * </p>
 */
public class InsufficientBalanceException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(InsufficientBalanceException.class);

  public InsufficientBalanceException(String message) {
    super(message);
    logger.error("Insufficient balance: {}", message);
  }
}
