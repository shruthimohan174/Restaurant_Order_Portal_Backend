package com.users.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object for updating user information.
 * Contains fields for first name, last name, phone number, and password,
 * with validation constraints to ensure data integrity.
 */
@Data
public class UpdateUserInDto {

  /**
   * The user's first name.
   */
  private String firstName;

  /**
   * The user's last name.
   */
  private String lastName;

  /**
   * The user's phone number. Must be exactly 10 digits long and start with 7, 8, or 9.
   */
  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
  @Pattern(
    regexp = "^[789]\\d{9}$",
    message = "Phone number must start with 7, 8, or 9 and be exactly 10 digits long."
  )
  private String phoneNumber;

  /**
   * The user's password. Must be at least 5 characters long and contain at least one digit,
   * one lowercase letter, one uppercase letter, and one special character.
   */
  @Size(min = 5, message = "Password must be at least 5 characters long")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{5,}$",
    message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
  )
  private String password;
}
