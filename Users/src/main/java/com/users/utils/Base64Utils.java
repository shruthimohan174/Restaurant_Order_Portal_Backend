package com.users.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Base64Utils {
  private Base64Utils() {
    throw new AssertionError("Cannot instantiate utility class");
  }

  public static String encodePassword(String password) {
    return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
  }

  public static String decodePassword(String encodedPassword) {
    return new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);
  }
}
