package com.orders.entities;

import com.orders.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing an order.
 */
@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  /**
   * Unique identifier for the order.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * Unique identifier for the order.
   */
  private Integer userId;
  /**
   * ID of the delivery address for the order.
   */
  private Integer deliveryAddressId;
  /**
   * Current status of the order.
   */
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
  /**
   * JSON string representing the cart items associated with the order.
   */
  private String cartItems;
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
