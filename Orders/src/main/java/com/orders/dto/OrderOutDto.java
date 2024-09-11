package com.orders.dto;

import com.orders.entities.Cart;
import com.orders.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for representing a order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutDto {

  /**
   * Unique identifier for the order.
   */
  private Integer id;

  /**
   * ID of the user who placed the order.
   */
  private Integer userId;

  /**
   * ID of the delivery address for the order.
   */
  private Integer deliveryAddressId;

  /**
   * Current status of the order.
   */
  private OrderStatus orderStatus;

  /**
   * List of cart items associated with the order.
   */
  private List<Cart> cartItems;

  /**
   * Timestamp when the order was placed.
   */
  private LocalDateTime orderTime;

  /**
   * Total price of the order.
   */
  private BigDecimal totalPrice;

  /**
   * ID of the restaurant where the order was placed.
   */
  private Integer restaurantId;
}
