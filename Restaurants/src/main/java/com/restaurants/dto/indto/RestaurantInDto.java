package com.restaurants.dto.indto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data transfer object for input data related to restaurants.
 */
@Data
public class RestaurantInDto {

  /**
   * The ID of the user who owns the restaurant.
   * Cannot be blank.
   */
  @NotBlank(message="user ID cannot be blank")
  private Integer userId;

  /**
   * The name of the restaurant.
   * Cannot be blank.
   */
  @NotBlank(message="Restaurant name cannot be blank")
  private String restaurantName;

  /**
   * The address of the restaurant.
   * Cannot be blank.
   */
  @NotBlank(message="Address cannot be blank")
  private String address;

  /**
   * The contact number of the restaurant.
   * Must be exactly 10 digits long and start with 7, 8, or 9.
   */
  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
  @Pattern(
    regexp = "^[789]\\d{9}$",
    message = "Phone number must start with 7, 8, or 9."
  )
  private String contactNumber;

  /**
   * The opening hours of the restaurant.
   */
  private String openingHours;

  private MultipartFile image;

}
