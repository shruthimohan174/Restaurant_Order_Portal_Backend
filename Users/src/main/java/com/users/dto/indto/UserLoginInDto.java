package com.users.dto.indto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user login information.
 * Contains fields for email and password required for user authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInDto {

  /**
   * The user's email address. Must not be blank.
   */
  @NotBlank(message = "Email is required")
  private String email;

  /**
   * The user's password. Must not be blank.
   */
  @NotBlank(message = "Password is required")
  private String password;
}
