package com.users.dto.indto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class AddressInDto {

  @NotBlank(message = "Street is required")
  private String street;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "State is required")
  private String state;

  @NotBlank(message = "Pincode is required")
  private Integer pincode;

  @NotBlank(message = "UserId is required")
  private Integer userId;
}
