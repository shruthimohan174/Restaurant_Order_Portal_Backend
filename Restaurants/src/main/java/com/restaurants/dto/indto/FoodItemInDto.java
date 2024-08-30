package com.restaurants.dto.indto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

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
  @NotBlank(message="Category ID cannot be blank")
  private Integer categoryId;

  /**
   * The ID of the restaurant that the food item belongs to.
   * Cannot be blank.
   */
  @NotBlank(message="Restaurant ID cannot be blank")
  private Integer restaurantId;

  /**
   * The name of the food item.
   * Cannot be blank.
   */
  @NotBlank(message="Item name cannot be blank")
  private String itemName;

  /**
   * A description of the food item.
   * Cannot be blank.
   */
  @NotBlank(message="Description cannot be blank")
  private String description;

  /**
   * Indicates whether the food item is vegetarian.
   * Cannot be blank.
   */
  @NotBlank(message="isVeg cannot be blank")
  private Boolean isVeg;

  /**
   * The price of the food item.
   * Cannot be blank.
   */
  @NotBlank(message="Price cannot be blank")
  private BigDecimal price;

  /**
   * URL of the image representing the food item.
   */
  private MultipartFile image;
}
