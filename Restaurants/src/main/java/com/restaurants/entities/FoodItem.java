package com.restaurants.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Entity class representing a food item.
 */
@Entity
@Data
public class FoodItem {
  /**
   * The unique identifier for the food item.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * The ID of the category to which the food item belongs.
   */
  private Integer categoryId;

  /**
   * The ID of the restaurant to which the food item belongs.
   */
  private Integer restaurantId;

  /**
   * The name of the food item.
   */
  private String itemName;

  /**
   * A description of the food item.
   */
  private String description;

  /**
   * Indicates whether the food item is vegetarian.
   */
  private Boolean isVeg;

  /**
   * The price of the food item.
   */
  private BigDecimal price;

  /**
   * URL of the image representing the food item.
   */
  private String imageUrl;

}
