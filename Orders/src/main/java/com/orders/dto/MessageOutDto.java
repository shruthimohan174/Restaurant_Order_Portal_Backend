package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for representing a message
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageOutDto {
  /**
   * Message content.
   */
  private String message;
}
