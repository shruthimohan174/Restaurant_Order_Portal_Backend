package com.users.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class for encoding and decoding passwords using Base64.
 * <p>
 * This class provides static methods to encode a password to Base64 format and decode a Base64 encoded password
 * back to its original format. It cannot be instantiated.
 * </p>
 */
public final class PasswordEncodingDecodingUtils {

  /**
   * Encodes the given password to Base64 format.
   * <p>
   * This method converts the password string to bytes using UTF-8 encoding, then encodes the bytes to a Base64
   * encoded string.
   * </p>
   *
   * @param password the password to encode
   * @return the Base64 encoded password string
   */
  public static String encodePassword(final String password) {
    return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Decodes the given Base64 encoded password back to its original format.
   * <p>
   * This method decodes the Base64 encoded string to bytes, then converts the bytes back to a string using UTF-8
   * encoding.
   * </p>
   *
   * @param encodedPassword the Base64 encoded password to decode
   * @return the decoded password string
   */
  public static String decodePassword(final String encodedPassword) {
    return new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);
  }
  /**
   * Private constructor to prevent instantiation of this utility class.
   * <p>
   * This constructor throws an {@link AssertionError} to prevent instantiation.
   * </p>
   */
  private PasswordEncodingDecodingUtils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

}
