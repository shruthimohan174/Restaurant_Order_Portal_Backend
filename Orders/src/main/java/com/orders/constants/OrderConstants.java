package com.orders.constants;

/**
 * This class contains constant messages used throughout the order processing system.
 * These constants represent various statuses and messages related to orders and carts.
 */
public final class OrderConstants {
  /**
   * Message indicating that there is insufficient balance in wallet.
   */
  public static final String INSUFFICIENT_BALANCE = "Insufficient balance to place the order.";

  /**
   * Message indicating that an order was placed successfully.
   */
  public static final String ORDER_PLACED_SUCCESSFULLY = "Order placed successfully";

  /**
   * Message indicating that an item was added to the cart successfully.
   */
  public static final String CART_ADDED_SUCCESSFULLY = " Cart Added successfully";

  /**
   * Message indicating that the cart was updated successfully.
   */
  public static final String CART_UPDATED_SUCCESSFULLY = "Cart Updated successfully";

  /**
   * Message indicating that the cart was deleted successfully.
   */
  public static final String CART_DELETED_SUCCESSFULLY = "Cart Deleted successfully";

  /** Message indicating that no cart was found with the specified id. */
  public static final String CART_NOT_FOUND = "Cart not found with this id";

  /**
   * Message indicating that an item was removed from the cart successfully.
   */
  public static final String ITEM_REMOVED_SUCCESSFULLY = "This item has been removed successfully";

  /** Message indicating that an order was cancelled successfully. */
  public static final String ORDER_CANCELLED_SUCCESSFULLY = "Order has been cancelled";

  /** Message indicating that an order was not cancelled due to failure. */
  public static final String ORDER_CANCELLED_FAILURE = "Order cannot be cancelled after 30 seconds.";

  /** Message indicating that an order was completed successfully. */
  public static final String ORDER_COMPLETED_SUCCESSFULLY = "Order has been completed";

  /**
   * Message indicating that the cart is already empty.
   */
  public static final String CART_ALREADY_EMPTY = "Your cart is already empty!";

  /**
   * Message indicating that no customer was found with the specified id.
   */
  public static final String CUSTOMER_NOT_FOUND = "This id does not belongs to customer";

  /**
   * Message indicating that no address was found with the specified id.
   */
  public static final String ADDRESS_NOT_FOUND = "Address not found with this id";

  /**
   * Message indicating a price mismatch for a food item.
   */
  public static final String PRICE_MISMATCH = "This is not the current price of this food item.";
  /**
   * Message indicating that no order was found with the specified id.
   */
  public static final String ORDER_NOT_FOUND = "Order not found with this id";
  /**
   * Message indicating that no cart items was found with the specified id.
   */
  public static final String INVALID_CART_ITEMS = "Invalid items present in cart";
  private OrderConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
