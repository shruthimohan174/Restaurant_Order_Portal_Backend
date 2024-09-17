package com.users.dto;

import com.users.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for user information.
 * Contains detailed information about a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

  /**
   * Unique identifier for the user.
   */
  private Integer id;

  /**
   * First name of the user.
   */
  private String firstName;

  /**
   * Last name of the user.
   */
  private String lastName;

  /**
   * Email address of the user.
   */
  private String email;

  /**
   * Phone number of the user.
   */
  private String phoneNumber;

  /**
   * Role of the user within the application.
   */
  private UserRole userRole;

  /**
   * Wallet balance of the user.
   */
  private BigDecimal walletBalance;
}
