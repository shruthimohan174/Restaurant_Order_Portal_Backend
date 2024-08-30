package com.restaurants.dto.outdto;

import lombok.Data;

/**
 * Data transfer object for output data related to food categories.
 */
@Data
public class FoodCategoryOutDto {

  /**
   * The unique identifier of the food category.
   */
  private Integer id;

  /**
   * The ID of the restaurant that the category belongs to.
   */
  private Integer restaurantId;

  /**
   * The name of the food category.
   */
  private String categoryName;
}
