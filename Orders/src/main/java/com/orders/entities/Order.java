package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.dto.outdto.CartOutDto;
import com.orders.utils.OrderStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Data
@Table(name="orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer userId;

  private Integer deliveryAddressId;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

private String cartItems;

  private LocalDateTime orderTime;

  private BigDecimal totalPrice;

  private Integer restaurantId;

  @Transient
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public List<Cart> getCartItemsAsList() throws JsonProcessingException {
    return objectMapper.readValue(cartItems, objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
  }

  public void setCartItemsFromList(List<Cart> cartItems) throws JsonProcessingException {
    this.cartItems = objectMapper.writeValueAsString(cartItems);
  }
}
