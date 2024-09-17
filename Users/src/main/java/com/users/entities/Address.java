package com.users.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity representing an address.
 * <p>
 * This entity maps to the 'Address' table in the database and holds address-related information
 * for users, including the street, city, state, and pincode.
 * </p>
 */
@Data
@Entity
public class Address {

  /**
   * Unique identifier for the address.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * The street address.
   */
  private String street;

  /**
   * The city of the address.
   */
  private String city;

  /**
   * The state of the address.
   */
  private String state;

  /**
   * The postal code (pincode) of the address.
   */
  private Integer pincode;

  /**
   * The ID of the user associated with this address.
   */
  private Integer userId;
}
