package com.orders.dto.outdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartOutDto {

  private Integer id;

  private Integer userId;

  private Integer foodItemId;

  private Integer quantity;

  private BigDecimal price;

  private Integer restaurantId;

}
