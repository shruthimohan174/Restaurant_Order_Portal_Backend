package com.restaurants.dto;

import lombok.Data;

import javax.persistence.Lob;

/**
 * Data transfer object for output data related to restaurants.
 */
@Data
public class RestaurantOutDto {
  /**
   * The unique identifier of the restaurant.
   */
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
