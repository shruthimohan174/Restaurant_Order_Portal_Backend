package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.AddressInDto;
import com.users.dto.AddressOutDto;
import com.users.dto.MessageOutDto;
import com.users.converter.DtoConversion;
import com.users.entities.Address;
import com.users.exception.ResourceNotFoundException;
import com.users.repositories.AddressRepository;
import com.users.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link AddressService} interface.
 * <p>
 * This service provides methods for managing addresses and interacting with the underlying
 * data repository.
 * </p>
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

  /**
   * Service layer dependency for addressRepository-related operations.
   */
  @Autowired
  private AddressRepository addressRepository;

  /**
   * Adds a new address to the system.
   *
   * @param request the address information to be added
   * @return a message indicating the success of the operation
   */
  @Override
  public MessageOutDto addAddress(final AddressInDto request) {
    log.info("Adding new address for user ID: {}", request.getUserId());
    Address address = DtoConversion.convertAddressRequestToAddress(request);
    Address saved = addressRepository.save(address);
    log.info("Address added successfully with ID: {}", saved.getId());
    DtoConversion.convertAddressToAddressResponse(saved);
    return new MessageOutDto(UserConstants.ADDRESS_ADDED_SUCCESS);
  }

  /**
   * Retrieves all addresses from the system.
   *
   * @return a list of all addresses
   */
  @Override
  public List<AddressOutDto> getAllAddress() {
    log.info("Fetching all addresses");
    List<Address> addresses = addressRepository.findAll();
    List<AddressOutDto> addressOutDtoList = new ArrayList<>();
    for (Address address : addresses) {
      addressOutDtoList.add(DtoConversion.convertAddressToAddressResponse(address));
    }
    log.info("Number of addresses fetched: {}", addressOutDtoList.size());
    return addressOutDtoList;
  }

  /**
   * Retrieves addresses for a specific user.
   *
   * @param userId the ID of the user whose addresses are to be retrieved
   * @return a list of addresses associated with the specified user
   */
  @Override
  public List<AddressOutDto> getAddressByUserId(final Integer userId) {
    log.info("Fetching addresses for user ID: {}", userId);
    List<Address> addresses = addressRepository.findByUserId(userId);
    List<AddressOutDto> addressOutDtoList = new ArrayList<>();
    for (Address address : addresses) {
      addressOutDtoList.add(DtoConversion.convertAddressToAddressResponse(address));
    }
    log.info("Number of addresses fetched for user ID {}: {}", userId, addressOutDtoList.size());
    return addressOutDtoList;
  }

  /**
   * Updates an existing address.
   *
   * @param id      the ID of the address to be updated
   * @param request the updated address information
   * @return a message indicating the success of the operation
   */
  @Override
  public MessageOutDto updateAddress(final Integer id, final AddressInDto request) {
    log.info("Updating address with ID: {}", id);
    Address existingAddress = findAddressById(id);
    DtoConversion.convertUpdateAddressRequestToAddress(request, existingAddress);
    Address updatedAddress = addressRepository.save(existingAddress);
    log.info("Address updated successfully with ID: {}", updatedAddress.getId());
    DtoConversion.convertAddressToAddressResponse(updatedAddress);
    return new MessageOutDto(UserConstants.ADDRESS_UPDATE_SUCCESS);
  }

  /**
   * Deletes an address by its ID.
   *
   * @param id the ID of the address to be deleted
   * @return a message indicating the success of the operation
   */
  @Override
  public MessageOutDto deleteAdderess(final Integer id) {
    log.info("Deleting address with ID: {}", id);
    Address address = findAddressById(id);
    addressRepository.delete(address);
    log.info("Address deleted successfully with ID: {}", id);
    return new MessageOutDto(UserConstants.ADDRESS_DELETION_SUCCESS);
  }

  /**
   * Finds an address by its ID.
   *
   * @param id the ID of the address to be found
   * @return the address with the specified ID
   * @throws ResourceNotFoundException if the address is not found
   */
  @Override
  public Address findAddressById(final Integer id) {
    log.info("Finding address by ID: {}", id);
    return addressRepository.findById(id)
      .orElseThrow(() -> {
        log.error("Address not found with ID: {}", id);
        return new ResourceNotFoundException(UserConstants.ADDRESS_NOT_FOUND);
      });
  }
}
