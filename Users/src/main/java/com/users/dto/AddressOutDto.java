package com.users.dto;

import lombok.Data;

/**
 * Response DTO for Address.
 * Contains information about an address.
 */
@Data
public class AddressOutDto {

  /**
   * Unique identifier for the address.
   */
  private Integer id;

  /**
   * Street name of the address.
   */
  private String street;

  /**
   * City of the address.
   */
  private String city;

  /**
   * State of the address.
   */
  private String state;

  /**
   * Pincode of the address.
   */
  private Integer pincode;

  /**
   * User ID associated with the address.
   */
  private Integer userId;

}
