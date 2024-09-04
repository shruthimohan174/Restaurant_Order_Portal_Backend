package com.restaurants.dto.indto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class FoodItemUpdateInDto {


  /**
   * The name of the food item.
   * Cannot be blank and must contain only alphabets.
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
   * The price of the food item.
   * Cannot be blank.
   */
  @NotBlank(message = "Price cannot be blank and it must be greater than 0")
  @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
  private BigDecimal price;
}
