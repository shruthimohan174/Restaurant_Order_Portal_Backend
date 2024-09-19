package com.restaurants.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.math.BigDecimal;

/**
 * Data transfer object for output data related to food items.
 */
@Data
public class FoodItemOutDto {
  /**
   * The unique identifier of the food item.
   */
  private Integer id;

  /**
   * The ID of the category to which the food item belongs.
   */
  private Integer categoryId;

  /**
   * The ID of the restaurant that the food item belongs to.
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
   * The binary data of the image representing the food item.
   * This field stores the image in byte array format.
   */
  @Lob
  private byte[] imageData;
}
