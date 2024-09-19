package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.dto.AddressInDto;
import com.users.dto.AddressOutDto;
import com.users.dto.MessageOutDto;
import com.users.entities.Address;
import com.users.exception.ResourceNotFoundException;
import com.users.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Unit tests for {@link AddressServiceImpl}.
 * <p>
 * This class contains test methods for verifying the behavior of the
 * {@link AddressServiceImpl} class, including testing various methods
 * related to address management, such as adding, updating, and retrieving
 * addresses.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

  /**
   * The service under test.
   * <p>
   * {@code AddressServiceImpl} is the class that contains the business logic
   * for managing addresses.
   * </p>
   */
  @InjectMocks
  private AddressServiceImpl addressService;

  /**
   * Mock of the {@link AddressRepository}.
   * <p>
   * This mock is used to simulate interactions with the address repository
   * and verify the behavior of the service without depending on the actual
   * repository implementation.
   * </p>
   */
  @Mock
  private AddressRepository addressRepository;

  /**
   * An instance of {@link Address} used for testing.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of the {@link AddressServiceImpl} methods that interact with
   * address entities.
   * </p>
   */
  private Address address;

  /**
   * An instance of {@link AddressInDto} used for testing input data.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that process input data for address creation or
   * updates.
   * </p>
   */
  private AddressInDto addressInDto;

  /**
   * An instance of {@link AddressOutDto} used for testing output data.
   * <p>
   * This field is initialized with sample data and is used to test the
   * behavior of methods that generate or process output data for addresses.
   * </p>
   */
  private AddressOutDto addressOutDto;

  /**
   * Sets up the test environment by initializing test objects before each test.
   * <p>
   * This method is executed before each test method and is used to create
   * instances of {@link Address}, {@link AddressInDto}, and {@link AddressOutDto}
   * with predefined values.
   * </p>
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    addressInDto = new AddressInDto();
    addressInDto.setUserId(1);
    addressInDto.setStreet("street");
    addressInDto.setCity("city");
    addressInDto.setPincode(123456);

    addressOutDto = new AddressOutDto();
    addressOutDto.setId(1);
    addressOutDto.setUserId(1);
    addressOutDto.setStreet("street");
    addressOutDto.setCity("city");
    addressOutDto.setPincode(123456);

    address = new Address();
    address.setId(1);
    address.setUserId(1);
    address.setStreet("street");
    address.setCity("city");
    address.setPincode(123456);
  }

  @Test
  void addAddressTest() {
    when(addressRepository.save(any(Address.class))).thenReturn(address);

    MessageOutDto response = addressService.addAddress(addressInDto);

    assertEquals(UserConstants.ADDRESS_ADDED_SUCCESS, response.getMessage());
  }

  @Test
  void getAllAddressTest() {
    when(addressRepository.findAll()).thenReturn(Collections.singletonList(address));

    List<AddressOutDto> addresses = addressService.getAllAddress();

    assertNotNull(addresses);
    assertFalse(addresses.isEmpty());
    assertEquals(1, addresses.size());
    assertEquals(address.getId(), addresses.get(0).getId());
  }

  @Test
  void getAddressByUserIdTest() {
    when(addressRepository.findByUserId(anyInt())).thenReturn(Collections.singletonList(address));

    List<AddressOutDto> addresses = addressService.getAddressByUserId(1);

    assertNotNull(addresses);
    assertFalse(addresses.isEmpty());
    assertEquals(1, addresses.size());
    assertEquals(address.getId(), addresses.get(0).getId());
  }

  @Test
  void updateAddressTest() {
    when(addressRepository.findById(anyInt())).thenReturn(Optional.of(address));
    when(addressRepository.save(any(Address.class))).thenReturn(address);

    MessageOutDto response = addressService.updateAddress(1, addressInDto);

    assertEquals(UserConstants.ADDRESS_UPDATE_SUCCESS, response.getMessage());
  }

  @Test
  void deleteAddressTest() {
    when(addressRepository.findById(anyInt())).thenReturn(Optional.of(address));

    MessageOutDto response = addressService.deleteAdderess(1);

    assertEquals(UserConstants.ADDRESS_DELETION_SUCCESS, response.getMessage());
    verify(addressRepository, times(1)).delete(address);
  }

  @Test
  void findAddressByIdTest() {
    when(addressRepository.findById(anyInt())).thenReturn(Optional.of(address));

    Address result = addressService.findAddressById(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("street", result.getStreet());
    assertEquals("city", result.getCity());
    assertEquals(123456, result.getPincode());
  }

  @Test
  void findAddressByIdAddressNotFoundTest() {
    when(addressRepository.findById(anyInt())).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      addressService.findAddressById(1);
    });

    assertEquals(UserConstants.ADDRESS_NOT_FOUND, exception.getMessage());
  }
}
