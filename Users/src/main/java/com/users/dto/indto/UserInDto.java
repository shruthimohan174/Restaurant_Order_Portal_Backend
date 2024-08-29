package com.users.dto.indto;

import com.users.entities.UserRole;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserInDto {

  @NotBlank(message = "First name is required")
  @Pattern(
    regexp = "^[a-zA-Z]+$",
    message = "First name must contain only alphabets."
  )
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Pattern(
    regexp = "^[a-zA-Z]+$",
    message = "Last name must contain only alphabets."
  )
  private String lastName;

  @Email(message = "Invalid email format.")
  @Pattern(
    regexp = "^[a-zA-Z]+[a-zA-Z0-9._%+-]*@(gmail\\.com|nucleusteq\\.com)$",
    message = "Email must be a valid, @gmail.com or @nucleusteq.com and contain at least one alphabet before the '@' symbol."
  )
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 5, message = "Password must be at least 5 characters long")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{5,}$",
    message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
  )
  private String password;

  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
  @Pattern(
    regexp = "^[789]\\d{9}$",
    message = "Phone number must start with 7, 8, or 9 and contain exactly 10 digits."
  )
  private String phoneNumber;

  @NotBlank(message = "User Role is required")
  private UserRole userRole;
}
