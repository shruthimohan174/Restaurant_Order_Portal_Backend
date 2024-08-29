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
 * for users.
 * </p>
 */
@Data
@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String street;

  private String city;

  private String state;

  private Integer pincode;

  private Integer userId;
}
