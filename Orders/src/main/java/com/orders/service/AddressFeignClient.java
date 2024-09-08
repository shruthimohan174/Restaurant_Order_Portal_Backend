package com.orders.service;

import com.orders.dto.outdto.AddressOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="address-service", url = "${user.microservice.url}")
public interface AddressFeignClient {
  /**
   * Retrieves all addresses associated with the user by user ID.
   *
   * @param userId the ID of the user
   * @return the list of {@link AddressOutDto} containing address details
   */
  @GetMapping("/user/{userId}/addresses")
  List<AddressOutDto> getAddressesByUserId(@PathVariable("userId") Integer userId);

  @GetMapping("/addresses/{addressId}")
  AddressOutDto getAddressById(@PathVariable("addressId") Integer addressId);
}
