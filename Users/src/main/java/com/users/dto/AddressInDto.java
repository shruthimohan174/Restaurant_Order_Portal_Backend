package com.users.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user address information.
 * Contains fields for street, city, state, pincode, and userId, with validation constraints.
 */
@Data
public class AddressInDto {

  /**
   * The street address. Must not be blank.
   */
  @NotBlank(message = "Street is required")
  private String street;

  /**
   * The city. Must not be blank.
   */
  @NotBlank(message = "City is required")
  private String city;

  /**
   * The state. Must not be blank.
   */
  @NotBlank(message = "State is required")
  private String state;

  /**
   * The pincode. Must not be blank. It should be a numeric value.
   */
  @NotBlank(message = "Pincode is required")
  private Integer pincode;

  /**
   * The ID of the user associated with this address. Must not be blank.
   */
  @NotBlank(message = "UserId is required")
  private Integer userId;
}
