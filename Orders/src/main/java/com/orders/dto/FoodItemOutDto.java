package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for representing a food item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemOutDto {
  /**
   * The unique identifier of the food item.
   */
  private Integer id;

  /**
   * The ID of the restaurant that the food item belongs to.
   */
  private Integer restaurantId;

  /**
   * The name of the food item.
   */
  private String itemName;

  /**
   * The price of the food item.
   */
  private BigDecimal price;

}
