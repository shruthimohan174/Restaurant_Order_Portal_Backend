package com.users.dtoconversion;

import com.users.dto.indto.AddressInDto;
import com.users.dto.indto.UpdateUserInDto;
import com.users.dto.indto.UserInDto;
import com.users.dto.outdto.AddressOutDto;
import com.users.dto.outdto.UpdateUserOutDto;
import com.users.dto.outdto.UserOutDto;
import com.users.entities.Address;
import com.users.entities.User;
import com.users.entities.UserRole;

import java.math.BigDecimal;

/**
 * Utility class for converting between different DTOs and entity objects.
 * <p>
 * This class provides static methods to convert between user and address related
 * DTOs and entities. The class is final and has a private constructor to prevent
 * instantiation.
 * </p>
 */
public final class DtoConversion {

  private DtoConversion() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Converts a {@link UserInDto} to a {@link User} entity.
   * <p>
   * Initializes the wallet balance to 1000.00 if the user role is CUSTOMER.
   * </p>
   *
   * @param userRequest the user request DTO to convert
   * @return the corresponding {@link User} entity
   */
  public static User convertUserRequestToUser(UserInDto userRequest) {
    User user = new User();
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setEmail(userRequest.getEmail());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    user.setUserRole(userRequest.getUserRole());
    if (user.getUserRole() == UserRole.CUSTOMER) {
      user.setWalletBalance(BigDecimal.valueOf(1000.00));
    }
    return user;
  }

  /**
   * Converts a {@link User} entity to a {@link UserOutDto} DTO.
   *
   * @param user the user entity to convert
   * @return the corresponding {@link UserOutDto} DTO
   */
  public static UserOutDto convertUserToUserResponse(User user) {
    UserOutDto userResponse = new UserOutDto();
    userResponse.setId(user.getId());
    userResponse.setFirstName(user.getFirstName());
    userResponse.setLastName(user.getLastName());
    userResponse.setEmail(user.getEmail());
    userResponse.setUserRole(user.getUserRole());
    userResponse.setPhoneNumber(user.getPhoneNumber());
    userResponse.setWalletBalance(user.getWalletBalance());
    return userResponse;
  }

  /**
   * Updates an existing {@link User} entity with details from an {@link UpdateUserInDto}.
   *
   * @param existingUser the existing user entity to update
   * @param request      the update user request DTO containing new details
   * @return the updated {@link User} entity
   */
  public static User convertUpdateUserRequestToUser(User existingUser, UpdateUserInDto request) {
    existingUser.setFirstName(request.getFirstName());
    existingUser.setLastName(request.getLastName());
    existingUser.setPhoneNumber(request.getPhoneNumber());
    return existingUser;
  }

  /**
   * Converts a {@link User} entity to an {@link UpdateUserOutDto} DTO.
   *
   * @param user the user entity to convert
   * @return the corresponding {@link UpdateUserOutDto} DTO
   */
  public static UpdateUserOutDto convertUserToUpdateUserResponse(User user) {
    UpdateUserOutDto response = new UpdateUserOutDto();
    response.setId(user.getId());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setPhoneNumber(user.getPhoneNumber());
    return response;
  }

  /**
   * Converts an {@link AddressInDto} to an {@link Address} entity.
   *
   * @param request the address request DTO to convert
   * @return the corresponding {@link Address} entity
   */
  public static Address convertAddressRequestToAddress(AddressInDto request) {
    Address address = new Address();
    address.setStreet(request.getStreet());
    address.setCity(request.getCity());
    address.setState(request.getState());
    address.setUserId(request.getUserId());
    address.setPincode(request.getPincode());
    return address;
  }

  /**
   * Converts an {@link Address} entity to an {@link AddressOutDto} DTO.
   *
   * @param address the address entity to convert
   * @return the corresponding {@link AddressOutDto} DTO
   */
  public static AddressOutDto convertAddressToAddressResponse(Address address) {
    AddressOutDto addressResponse = new AddressOutDto();
    addressResponse.setId(address.getId());
    addressResponse.setStreet(address.getStreet());
    addressResponse.setCity(address.getCity());
    addressResponse.setState(address.getState());
    addressResponse.setUserId(address.getUserId());
    addressResponse.setPincode(address.getPincode());
    return addressResponse;
  }

  /**
   * Updates an existing {@link Address} entity with details from an {@link AddressInDto}.
   *
   * @param request the address request DTO containing new details
   * @param existingAddress the existing address entity to update
   * @return the updated {@link Address} entity
   */
  public static Address convertUpdateAddressRequestToAddress(AddressInDto request, Address existingAddress) {
    existingAddress.setStreet(request.getStreet());
    existingAddress.setCity(request.getCity());
    existingAddress.setState(request.getState());
    existingAddress.setPincode(request.getPincode());
    return existingAddress;
  }
}
