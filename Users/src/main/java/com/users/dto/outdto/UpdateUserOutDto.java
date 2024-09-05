package com.users.dto.outdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for updating user information.
 * Contains basic information about a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserOutDto {

  /**
   * Unique identifier for the user.
   */
  private Integer id;

  /**
   * First name of the user.
   */
  private String firstName;

  /**
   * Last name of the user.
   */
  private String lastName;

  /**
   * Phone number of the user.
   */
  private String phoneNumber;

}
