package com.orders.service;

import com.orders.dto.AddressOutDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "address-service", url = "${user.microservice.url}")
public interface AddressFeignClient {
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