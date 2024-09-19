package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for representing  address.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
