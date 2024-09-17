package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for a cart item.
 * <p>
 * Represents the information about an item in the cart, including its ID, quantity, and price.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

  /**
   * The unique identifier of the food item.
   */
  private Integer foodItemId;

  /**
   * The quantity of the food item in the cart.
   */
  private Integer quantity;

  /**
   * The price of the food item.
   */
  private BigDecimal price;
}
