package com.orders.dto.outdto;

import com.orders.utils.UserRole;
import lombok.Data;

@Data
public class UserOutDto {

  private Integer id;

  private UserRole userRole;
}
