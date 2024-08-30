package com.restaurants.dto.indto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data transfer object for input data related to food categories.
 */
@Data
public class FoodCategoryInDto {

  /**
   * The ID of the restaurant that the category belongs to.
   * Cannot be blank.
   */
  @NotBlank(message = "Restaurant ID cannot be blank")
  private Integer restaurantId;

  /**
   * The name of the food category.
   * Cannot be blank.
   */
  @NotBlank(message = "Category name cannot be blank")
  private String categoryName;
}
