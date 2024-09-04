package com.restaurants.dto.outdto;

import com.restaurants.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing user information in the response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

  /**
   * Unique identifier for the user.
   */
  private Integer id;

  /**
   * Role of the user within the application.
   */
  private UserRole userRole;
}
