package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Data Transfer Object for representing a cart item.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInDto {

  /**
   * The ID of the user adding items to the cart.
   * This field is required and cannot be null.
   */
  @NotNull(message = "User ID cannot be null")
  private Integer userId;

  /**
   * The ID of the food item being added to the cart.
   * This field is required and cannot be null.
   */
  @NotNull(message = "Food Item ID cannot be null")
  private Integer foodItemId;
  /**
   * The price of the food item.
   * The price must be a positive value (greater than 0).
   */
  @NotNull(message = "Price cannot be null")
  @DecimalMin(value = "0.01", inclusive = false, message = "Price must be greater than 0")
  private BigDecimal price;

  /**
   * The ID of the restaurant associated with the food item.
   * This field is required and cannot be null.
   */
  @NotNull(message = "Restaurant ID cannot be null")
  private Integer restaurantId;
}
