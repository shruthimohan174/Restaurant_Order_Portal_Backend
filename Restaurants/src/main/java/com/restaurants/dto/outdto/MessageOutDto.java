package com.restaurants.dto.outdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a response message.
 * <p>
 * This class is used to encapsulate a message that can be returned
 * as part of the response from various endpoints in the application.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageOutDto {

  /**
   * The message content.
   */
  private String message;

}
