package com.users.exception;

/**
 * Exception thrown when there is insufficient balance in a user's wallet.
 * <p>
 * Indicates that a requested operation cannot be performed due to insufficient wallet balance.
 * </p>
 */
public class InsufficientBalanceException extends RuntimeException {
  public InsufficientBalanceException(String message) {
    super(message);
  }
}
