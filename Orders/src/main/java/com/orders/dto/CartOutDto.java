package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for representing a cart item.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartOutDto {

  /**
   * Unique identifier for the cart item.
   */
  private Integer id;

  /**
   * ID of the user who owns the cart item.
   */
  private Integer userId;

  /**
   * ID of the food item in the cart.
   */
  private Integer foodItemId;

  /**
   * Quantity of the food item in the cart.
   */
  private Integer quantity;

  /**
   * Price of the food item.
   */
  private BigDecimal price;

  /**
   * ID of the restaurant that offers the food item.
   */
  private Integer restaurantId;

}
