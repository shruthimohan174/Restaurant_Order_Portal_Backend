package com.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object for representing Orders.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInDto {

  /**
   * The ID of the user placing the order.
   * This field is required and cannot be null.
   */
  @NotNull(message = "User ID cannot be null")
  private Integer userId;

  /**
   * The ID of the delivery address for the order.
   * This field is required and cannot be null.
   */
  @NotNull(message = "Delivery Address ID cannot be null")
  private Integer deliveryAddressId;

  /**
   * The ID of the restaurant from which the order is placed.
   * This field is required and cannot be null.
   */
  @NotNull(message = "Restaurant ID cannot be null")
  private Integer restaurantId;

  /**
   * A list of cart items associated with the order.
   * The list cannot be null and must contain valid Cart objects.
   */
  @NotNull(message = "Cart items cannot be null")
  @Valid
  private List<CartItemDto> cartItems;
}
