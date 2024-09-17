package com.orders.service.impl;

import com.orders.dto.AddressOutDto;
import com.orders.dto.UserOutDto;
import com.orders.service.UserFeignClient;
import com.orders.utils.UserRole;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Fallback implementation of {@link UserFeignClient} to handle scenarios where the User service is unavailable.
 * <p>
 * This class provides default responses for the methods in {@link UserFeignClient} when the main service fails.
 * </p>
 */
@Slf4j
public class UserFeignClientFallback implements UserFeignClient {

  /**
   * Fallback method for retrieving a user by their ID.
   * <p>
   * Provides a default response when the User service is down or unreachable.
   * </p>
   *
   * @param id The ID of the user.
   * @return A {@link UserOutDto} object with default values.
   */
  @Override
  public UserOutDto getUserById(final Integer id) {
    log.error("Fallback triggered for getUserById with id: {}", id);
    UserOutDto fallbackUser = new UserOutDto();
    fallbackUser.setId(id);
    fallbackUser.setUserRole(UserRole.CUSTOMER);
    fallbackUser.setWalletBalance(BigDecimal.ZERO);
    return fallbackUser;
  }

  /**
   * Fallback method for updating the wallet balance of a user.
   * <p>
   * This method does not perform any operation in fallback mode as it's not possible to update the balance
   * without the main service.
   * </p>
   *
   * @param id     The ID of the user whose wallet balance is to be updated.
   * @param amount The amount to be added to the wallet balance.
   */
  @Override
  public void updateWalletBalance(final Integer id, final BigDecimal amount) {
    log.error("Fallback triggered for updateWalletBalance with user id: {}", id);
  }

  /**
   * Fallback method for retrieving addresses by user ID.
   * <p>
   * Provides an empty list as a fallback response when the User service is down or unreachable.
   * </p>
   *
   * @param userId The ID of the user whose addresses are to be retrieved.
   * @return An empty list of {@link AddressOutDto}.
   */
  @Override
  public List<AddressOutDto> getAddressesByUserId(final Integer userId) {
    log.error("Fallback triggered for getAddressesByUserId with user id: {}", userId);
    return Collections.emptyList();
  }

  /**
   * Fallback method for retrieving an address by its ID.
   * <p>
   * Provides a default response when the User service is down or unreachable.
   * </p>
   *
   * @param addressId The ID of the address.
   * @return An {@link AddressOutDto} object with default values.
   */
  @Override
  public AddressOutDto getAddressById(final Integer addressId) {
    log.error("Fallback triggered for getAddressById with address id: {}", addressId);
    AddressOutDto fallbackAddress = new AddressOutDto();
    fallbackAddress.setId(addressId);
    fallbackAddress.setStreet("Fallback Street");
    fallbackAddress.setCity("Fallback City");
    fallbackAddress.setState("Fallback State");
    fallbackAddress.setPincode(000000);
    return fallbackAddress;
  }
}
