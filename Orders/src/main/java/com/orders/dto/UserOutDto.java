package com.orders.dto;

import com.orders.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for representing a user
 * This class encapsulates the message content to be transferred between different layers or systems.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDto {
  /**
   * Unique identifier for the user.
   */
  private Integer id;

  /**
   * Role of the user in the system.
   */
  private UserRole userRole;

  /**
   * Wallet balance of the user.
   */
  private BigDecimal walletBalance;
}
