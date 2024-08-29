package com.users.dto.indto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserInDto {

  private String firstName;

  private String lastName;

  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
  @Pattern(
    regexp = "^[789]\\d{9}$",
    message = "Phone number must start with 7, 8, or 9 and be exactly 10 digits long."
  )
  private String phoneNumber;
}
