package com.users.constants;

/**
 * This class contains constants related to user operations.
 * <p>
 * It includes predefined error messages used throughout the application
 * for user-related operations. The class is marked final to prevent subclassing
 * and has a private constructor to prevent instantiation.
 * </p>
 */
public final class UserConstants {

  /**
   * Error message indicating that the user already exists with the provided email.
   */
  public static final String USER_ALREADY_EXISTS = "User already exists with this email.";

  /** Error message indicating invalid email or password. */
  public static final String INVALID_CREDENTIALS = "Invalid email or password";

  /** Error message indicating that the user does not exist. */
  public static final String USER_NOT_FOUND = "User does not exist";

  /** Error message indicating that no address was found with the given ID. */
  public static final String ADDRESS_NOT_FOUND = "No Address found with this id";

  /**
   * Error message indicating insufficient balance.
   */
  public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";

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
