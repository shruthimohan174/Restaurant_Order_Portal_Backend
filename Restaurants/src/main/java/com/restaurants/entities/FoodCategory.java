package com.restaurants.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class representing a food category.
 */
@Data
@Entity
public class FoodCategory {
  /**
   * The unique identifier for the food category.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * The ID of the restaurant to which the food category belongs.
   */
  private Integer restaurantId;
  /**
   * The name of the food category.
   */
  private String categoryName;
}
