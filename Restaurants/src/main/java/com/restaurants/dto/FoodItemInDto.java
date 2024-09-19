package com.restaurants.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * Data transfer object for input data related to food items.
 */
@Data
public class FoodItemInDto {

  /**
   * The ID of the category to which the food item belongs.
   * Cannot be blank.
   */
  @NotNull(message = "Category ID cannot be blank")
  private Integer categoryId;

  /**
   * The ID of the restaurant that the food item belongs to.
   * Cannot be blank.
   */
  @NotNull(message = "Restaurant ID cannot be blank")
  private Integer restaurantId;

  /**
   * The name of the food item.
   * Cannot be blank and contain alphabets.
   */
  @NotBlank(message = "Item name cannot be blank")
  @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must contain only alphabets")
  private String itemName;

  /**
   * A description of the food item.
   * Cannot be blank and must contain only alphabets.
   */
  @NotBlank(message = "Description cannot be blank")
  @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Description must contain only alphabets")
  private String description;

  /**
   * Indicates whether the food item is vegetarian.
   * Cannot be blank.
   */
  @NotBlank(message = "isVeg cannot be blank")
  private Boolean isVeg;

  /**
   * The price of the food item.
   * Cannot be blank and price must be greater than 0.
   */
  @NotBlank(message = "Price cannot be blank")
  @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
  private BigDecimal price;

}
