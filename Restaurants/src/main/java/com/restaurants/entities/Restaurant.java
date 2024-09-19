package com.restaurants.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Entity class representing a restaurant.
 */
@Entity
@Data
public class Restaurant {

  /**
   * The unique identifier for the restaurant.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * The ID of the user who owns the restaurant.
   */
  private Integer userId;

  /**
   * The name of the restaurant.
   */
  private String restaurantName;

  /**
   * The address of the restaurant.
   */
  private String address;

  /**
   * The contact number of the restaurant.
   */
  private String contactNumber;

  /**
   * The opening hours of the restaurant.
   */
  private String openingHours;

  /**
   * The binary data of the image representing the food item.
   * This field stores the image in byte array format.
   */
  @Lob
  private byte[] imageData;

}
