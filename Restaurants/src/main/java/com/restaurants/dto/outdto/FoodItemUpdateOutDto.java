package com.restaurants.dto.outdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodItemUpdateOutDto {
  /**
   * The id of the food item.
   */
  private Integer id;
  /**
   * The name of the food item.
   */
  private String itemName;

  /**
   * A description of the food item.
   */
  private String description;
  /**
   * The price of the food item.
   */
  private BigDecimal price;
}
