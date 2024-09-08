package com.orders.dto.indto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartInDto {

  private Integer userId;

  private Integer foodItemId;

  private Integer quantity;

  private BigDecimal price;

  private Integer restaurantId;
}
