package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.indto.AddressInDto;
import com.users.dto.outdto.AddressOutDto;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.exception.AddressNotFoundException;
import com.users.repositories.AddressRepository;
import com.users.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AddressServiceImpl implements AddressService {
  private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public AddressOutDto addAddress(AddressInDto request) {
    logger.info("Adding new address for user ID: {}", request.getUserId());
    Address address = DtoConversion.convertAddressRequestToAddress(request);
    Address saved = addressRepository.save(address);
    logger.info("Address added successfully with ID: {}", saved.getId());
    return DtoConversion.convertAddressToAddressResponse(saved);
  }

  @Override
  public List<AddressOutDto> getAllAddress() {
    logger.info("Fetching all addresses");
    List<Address> addresses = addressRepository.findAll();
    List<AddressOutDto> addressOutDtoList = new ArrayList<>();
    for (Address address : addresses) {
      addressOutDtoList.add(DtoConversion.convertAddressToAddressResponse(address));
    }
    logger.info("Number of addresses fetched: {}", addressOutDtoList.size());
    return addressOutDtoList;
  }

  @Override
  public List<AddressOutDto> getAddressByUserId(Integer userId) {
    logger.info("Fetching addresses for user ID: {}", userId);
    List<Address> addresses = addressRepository.findByUserId(userId);
    List<AddressOutDto> addressOutDtoList = new ArrayList<>();
    for (Address address : addresses) {
      addressOutDtoList.add(DtoConversion.convertAddressToAddressResponse(address));
    }
    logger.info("Number of addresses fetched for user ID {}: {}", userId, addressOutDtoList.size());
    return addressOutDtoList;
  }

  @Override
  public AddressOutDto updateAddress(Integer id, AddressInDto request) {
    logger.info("Updating address with ID: {}", id);
    Address existingAddress = findAddressById(id);
    DtoConversion.convertUpdateAddressRequestToAddress(request, existingAddress);
    Address updatedAddress = addressRepository.save(existingAddress);
    logger.info("Address updated successfully with ID: {}", updatedAddress.getId());
    return DtoConversion.convertAddressToAddressResponse(updatedAddress);
  }

  @Override
  public void deleteAdderess(Integer id) {
    logger.info("Deleting address with ID: {}", id);
    Address address = findAddressById(id);
    addressRepository.delete(address);
    logger.info("Address deleted successfully with ID: {}", id);
  }

  @Override
  public Address findAddressById(Integer id) {
    logger.info("Finding address by ID: {}", id);
    return addressRepository.findById(id)
      .orElseThrow(() -> {
        logger.error("Address not found with ID: {}", id);
        return new AddressNotFoundException(UserConstants.ADDRESS_NOT_FOUND);
      });
  }
}
