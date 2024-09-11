package com.orders.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Entity representing a cart item.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
  /**
   * Unique identifier for the cart item.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

