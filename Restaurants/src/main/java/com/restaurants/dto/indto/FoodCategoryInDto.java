package com.restaurants.dto.indto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * Data transfer object for input data related to food categories.
 */
@Data
public class FoodCategoryInDto {

  /**
   * The ID of the restaurant that the category belongs to.
   * Cannot be null.
   */
  @NotNull(message = "Restaurant ID cannot be null")
  private Integer restaurantId;

  /**
   * The name of the food category.
   * Cannot be blank and must contain only alphabets.
   */
  @NotBlank(message = "Category name cannot be blank")
  @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Category name must contain only alphabets")
  private String categoryName;
}
