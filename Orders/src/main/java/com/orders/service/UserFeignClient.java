package com.orders.service;

import com.orders.dto.AddressOutDto;
import com.orders.dto.UserOutDto;
import com.orders.service.impl.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "user-service", url = "${user.microservice.url}", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

  /**
   * Retrieves user details by user ID.
   *
   * @param id the ID of the user
   * @return the {@link UserOutDto} containing user details
   */
  @GetMapping("/user/{id}")
  UserOutDto getUserById(@PathVariable("id") Integer id);

  /**
   * Updates the wallet balance for a user.
   *
   * @param id     the ID of the user
   * @param amount the amount to update (positive for credit, negative for debit)
   */
  @PutMapping("/user/wallet/{id}")
  void updateWalletBalance(@PathVariable("id") Integer id, @RequestBody BigDecimal amount);

  /**
   * Retrieves all addresses associated with the user by user ID.
   *
   * @param userId the ID of the user
   * @return the list of {@link AddressOutDto} containing address details
   */
  @GetMapping("address/user/{userId}")
  List<AddressOutDto> getAddressesByUserId(@PathVariable("userId") Integer userId);

  /**
   * Retrieves the details of a specific address by address ID.
   *
   * @param addressId the ID of the address
   * @return the {@link AddressOutDto} containing address details
   */
  @GetMapping("/address/{addressId}")
  AddressOutDto getAddressById(@PathVariable("addressId") Integer addressId);
}
