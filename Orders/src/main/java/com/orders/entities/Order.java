package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.utils.OrderStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing an order.
 */
@Entity
@Data
@Table(name = "orders")
public class Order {

  /**
   * ObjectMapper for converting between JSON and Java objects.
   */
  @Transient
  private static final ObjectMapper objectMapper = new ObjectMapper();
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

  /**
   * Converts the JSON string of cart items to a list of Cart objects.
   *
   * @return List of Cart objects.
   * @throws JsonProcessingException If there is an error during JSON processing.
   */
  public List<Cart> getCartItemsAsList() throws JsonProcessingException {
    return objectMapper.readValue(cartItems, objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
  }

  /**
   * Converts a list of Cart objects to a JSON string.
   *
   * @param cartItems List of Cart objects.
   * @throws JsonProcessingException If there is an error during JSON processing.
   */
  public void setCartItemsFromList(List<Cart> cartItems) throws JsonProcessingException {
    this.cartItems = objectMapper.writeValueAsString(cartItems);
  }
}
