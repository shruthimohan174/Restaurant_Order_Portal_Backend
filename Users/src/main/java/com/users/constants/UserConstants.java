package com.users.constants;

/**
 * This class contains constants related to user operations.
 * <p>
 * It includes predefined messages used throughout the application
 * for user-related operations. The class is marked final to prevent subclassing
 * and has a private constructor to prevent instantiation.
 * </p>
 */
public final class UserConstants {

  /**
   * Error message indicating that the user already exists with the provided email.
   */
  public static final String USER_ALREADY_EXISTS = "User already exists with this email.";

  /**
   * Error message indicating invalid email or password.
   */
  public static final String INVALID_CREDENTIALS = "Invalid Eemail or password";

  /**
   * Error message indicating that the user does not exist.
   */
  public static final String USER_NOT_FOUND = "User does not exist";

  /**
   * Error message indicating that no address was found with the given ID.
   */
  public static final String ADDRESS_NOT_FOUND = "No Address found with this id";

  /**
   * Error message indicating insufficient balance.
   */
  public static final String INSUFFICIENT_BALANCE = "Insufficient balance in wallet to place the order.";

  /**
   * Message indicating that an email was sent successfully.
   */
  public static final String EMAIL_SENT_SUCCESS = "Email sent successfully.";

  /**
   * Message indicating that sending an email failed.
   */
  public static final String EMAIL_SENT_FAILURE = "Failed to send email.";

  /**
   * Error message indicating an invalid user role.
   */
  public static final String INVALID_USER_ROLE = "Invalid user role. Must be either CUSTOMER or RESTAURANT_OWNER.";

  /**
   * Error message indicating that the amount must be greater than zero.
   */
  public static final String INVALID_AMOUNT = "Amount must be greater than zero.";

  /**
   * Message indicating successful user registration.
   */
  public static final String USER_REGISTRATION_SUCCESS = "User registered successfully.";

  /**
   * Message indicating successful user profile update.
   */
  public static final String USER_PROFILE_UPDATE_SUCCESS = "User profile updated successfully.";

  /**
   * Message indicating successful addition of an address.
   */
  public static final String ADDRESS_ADDED_SUCCESS = "Address added successfully.";

  /**
   * Message indicating successful update of an address.
   */
  public static final String ADDRESS_UPDATE_SUCCESS = "Address updated successfully.";

  /**
   * Message indicating successful deletion of an address.
   */
  public static final String ADDRESS_DELETION_SUCCESS = "Address deleted successfully.";

  /**
   * Message indicating successful addition of funds to the wallet.
   */
  public static final String WALLET_ADDITION_SUCCESS = "Funds added to wallet successfully.";

  /**
   * Message indicating successful withdrawal of funds from the wallet.
   */
  public static final String WALLET_FUND_WITHDRAWAL_SUCCESS = "Funds withdrawn from wallet successfully.";

  /**
   * Private constructor to prevent instantiation of the utility class.
   * <p>
   * This class is designed to be used only for holding constants and should not
   * be instantiated.
   * </p>
   *
   * @throws UnsupportedOperationException if an attempt is made to instantiate the class
   */
  private UserConstants() {
    throw new UnsupportedOperationException("Utility class");
  }
}
