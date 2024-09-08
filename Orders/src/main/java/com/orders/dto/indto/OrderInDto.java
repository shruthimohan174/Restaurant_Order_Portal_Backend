package com.orders.dto.indto;

import com.orders.entities.Cart;
import com.orders.utils.OrderStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderInDto {

  private Integer userId;

  private Integer deliveryAddressId;

  private Integer restaurantId;

  private List<Cart> cartItems;

}
