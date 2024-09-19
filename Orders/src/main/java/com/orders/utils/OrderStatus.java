package com.orders.utils;

/**
 * Enum representing different statuses an order can have.
 * <p>
 * The {@code OrderStatus} enum defines the various states an order can be in throughout its lifecycle.
 * These statuses help in tracking the current state of an order and managing order processing.
 * </p>
 * <ul>
 *   <li>{@code PLACED} - Indicates that the order has been successfully placed but not yet processed.</li>
 *   <li>{@code CANCELLED} - Indicates that the order has been cancelled and will not be processed further.</li>
 *   <li>{@code COMPLETED} - Indicates that the order has been processed and completed successfully.</li>
 * </ul>
 */
public enum OrderStatus {
  /**
   * Order has been placed but not yet processed.
   */
  PLACED,

  /**
   * Order has been cancelled and will not be processed.
   */
  CANCELLED,

  /**
   * Order has been processed and completed successfully.
   */
  COMPLETED
}
