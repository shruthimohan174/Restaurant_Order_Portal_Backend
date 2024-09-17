package com.users.controller;

import com.users.dto.AddressInDto;
import com.users.dto.AddressOutDto;
import com.users.dto.MessageOutDto;
import com.users.entities.Address;
import com.users.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing addresses.
 * <p>
 * This controller provides endpoints to add, update, delete, and retrieve addresses
 * associated with users. It handles HTTP requests and interacts with the {@link AddressService}
 * to perform these operations.
 * </p>
 */
@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

  /**
   * Service layer dependency for addressService-related operations.
   */
  @Autowired
  private AddressService addressService;

  /**
   * Adds a new address.
   * <p>
   * This endpoint receives an address request and delegates the addition operation
   * to the {@link AddressService}. On success, it returns the address response with HTTP 201 Created.
   * </p>
   *
   * @param addressInDto the request body containing address details
   * @return a {@link ResponseEntity} containing the address response and HTTP status
   */
  @PostMapping("")
  public ResponseEntity<MessageOutDto> addAddress(final @RequestBody AddressInDto addressInDto) {
    log.info("Adding new address for user ID: {}", addressInDto.getUserId());

    MessageOutDto response = addressService.addAddress(addressInDto);
    log.info("Address added successfully for user ID: {}", addressInDto.getUserId());

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates an existing address.
   * <p>
   * This endpoint updates an address identified by the provided ID using the details
   * from the request body. It returns the updated address response with HTTP 200 OK on success.
   * </p>
   *
   * @param addressInDto the request body containing the updated address details
   * @param id             the ID of the address to update
   * @return a {@link ResponseEntity} containing the updated address response and HTTP status
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<MessageOutDto> updateAddress(final @RequestBody AddressInDto addressInDto,
                                                     final @PathVariable Integer id) {
    log.info("Updating address with ID: {}", id);
    MessageOutDto response = addressService.updateAddress(id, addressInDto);
    log.info("Address updated successfully with ID: {}", id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Deletes an address.
   * <p>
   * This endpoint deletes an address identified by the provided ID. It returns HTTP 204 No Content
   * on successful deletion.
   * </p>
   *
   * @param id the ID of the address to delete
   * @return a {@link ResponseEntity} with HTTP status
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<MessageOutDto> deleteAddress(final @PathVariable Integer id) {
    log.info("Deleting address with ID: {}", id);

    MessageOutDto response = addressService.deleteAdderess(id);
    log.info("Address deleted successfully with ID: {}", id);

    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves all addresses.
   * <p>
   * This endpoint fetches all addresses and returns them in the response body with HTTP 200 OK.
   * </p>
   *
   * @return a {@link ResponseEntity} containing a list of addresses and HTTP status
   */
  @GetMapping
  public ResponseEntity<List<AddressOutDto>> getAllAddresses() {
    log.info("Fetching all addresses");
    List<AddressOutDto> addressList = addressService.getAllAddress();
    return new ResponseEntity<>(addressList, HttpStatus.OK);
  }

  /**
   * Finds addresses by user ID.
   * <p>
   * This endpoint retrieves addresses associated with the specified user ID and returns
   * them in the response body with HTTP 200 OK.
   * </p>
   *
   * @param userId the ID of the user whose addresses are to be fetched
   * @return a {@link ResponseEntity} containing a list of addresses and HTTP status
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<AddressOutDto>> findAddressesByUserId(final @PathVariable Integer userId) {
    log.info("Fetching addresses for user ID: {}", userId);

    List<AddressOutDto> addressList = addressService.getAddressByUserId(userId);
    log.info("Retrieved {} addresses for user ID: {}", addressList.size(), userId);

    return new ResponseEntity<>(addressList, HttpStatus.OK);
  }

  /**
   * Retrieves an address by its unique identifier.
   *
   * @param id the unique identifier of the address to retrieve
   * @return a {@link ResponseEntity} containing the {@link Address} object and an HTTP status of 200 OK
   */
  @GetMapping("/{id}")
  public ResponseEntity<Address> getAddressById(final @PathVariable Integer id) {
    log.info("Fetching addresses with id{}", id);
    Address addressList = addressService.findAddressById(id);
    return new ResponseEntity<>(addressList, HttpStatus.OK);
  }

}
