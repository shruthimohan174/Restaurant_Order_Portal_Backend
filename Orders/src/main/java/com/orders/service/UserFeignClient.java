package com.orders.service;

import com.orders.dto.outdto.AddressOutDto;
import com.orders.dto.outdto.UserOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "user-service", url = "${user.microservice.url}")
public interface UserFeignClient {

  /**
   * Retrieves user details by ID.
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   * @param id the ID of the user
   * @return the {@link UserOutDto} containing user details
   */
  @GetMapping("/user/{id}")
  UserOutDto getUserById(@PathVariable("id") Integer id);

  @PutMapping("/user/wallet/{id}")
  void updateWalletBalance(@PathVariable("id") Integer id, @RequestBody BigDecimal amount);

}