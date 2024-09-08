package com.orders.dto.outdto;

import com.orders.entities.Cart;
import com.orders.utils.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderOutDto {

  private Integer id;

  private Integer userId;

  private Integer deliveryAddressId;

  private OrderStatus orderStatus;

  private List<Cart> cartItems;

  private LocalDateTime orderTime;

  private BigDecimal totalPrice;

  private Integer restaurantId;
}
