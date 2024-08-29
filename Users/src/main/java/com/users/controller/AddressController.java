package com.users.controller;

import com.users.dto.indto.AddressInDto;
import com.users.dto.outdto.AddressOutDto;
import com.users.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AddressController {

  private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

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
  @PostMapping("/add")
  public ResponseEntity<AddressOutDto> addAddress(@RequestBody AddressInDto addressInDto) {
    logger.info("Adding new address for user ID: {}", addressInDto.getUserId());

    AddressOutDto response = addressService.addAddress(addressInDto);
    logger.info("Address added successfully for user ID: {}", addressInDto.getUserId());

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
  public ResponseEntity<AddressOutDto> updateAddress(@RequestBody AddressInDto addressInDto, @PathVariable Integer id) {
    logger.info("Updating address with ID: {}", id);

    AddressOutDto response = addressService.updateAddress(id, addressInDto);
    logger.info("Address updated successfully with ID: {}", id);

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
  public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
    logger.info("Deleting address with ID: {}", id);

    addressService.deleteAdderess(id);
    logger.info("Address deleted successfully with ID: {}", id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    logger.info("Fetching all addresses");

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
  public ResponseEntity<List<AddressOutDto>> findAddressesByUserId(@PathVariable Integer userId) {
    logger.info("Fetching addresses for user ID: {}", userId);

    List<AddressOutDto> addressList = addressService.getAddressByUserId(userId);
    logger.info("Retrieved {} addresses for user ID: {}", addressList.size(), userId);

    return new ResponseEntity<>(addressList, HttpStatus.OK);
  }
}
