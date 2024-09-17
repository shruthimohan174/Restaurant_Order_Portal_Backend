package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.dto.AddressInDto;
import com.users.dto.AddressOutDto;
import com.users.dto.MessageOutDto;
import com.users.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link AddressController}.
 * <p>
 * This class contains test cases for CRUD operations on the Address entity.
 * It uses Mockito to mock dependencies and Spring's MockMvc to perform HTTP requests and verify responses.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

  /**
   * Injects the AddressController instance into the test class.
   */
  @InjectMocks
  private AddressController addressController;

  /**
   * Mocks the AddressService dependency for the controller.
   */
  @Mock
  private AddressService addressService;

  /**
   * MockMvc instance for performing HTTP requests and verifying responses.
   */
  private MockMvc mockMvc;

  /**
   * ObjectMapper instance for converting objects to/from JSON.
   */
  private ObjectMapper objectMapper;

  /**
   * DTO for address input data.
   */
  private AddressInDto addressInDto;

  /**
   * DTO for address output data.
   */
  private AddressOutDto addressResponse;

  /**
   * DTO for response messages.
   */
  private MessageOutDto messageResponse;

  /**
   * Initializes MockMvc, ObjectMapper, and sets up test data.
   */
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    objectMapper = new ObjectMapper();

    addressInDto = new AddressInDto();
    addressInDto.setUserId(1);
    addressInDto.setStreet("street");
    addressInDto.setCity("city");
    addressInDto.setPincode(123456);

    addressResponse = new AddressOutDto();
    addressResponse.setId(1);
    addressResponse.setUserId(1);
    addressResponse.setStreet("street");
    addressResponse.setCity("city");
    addressResponse.setPincode(123456);

    messageResponse = new MessageOutDto();
    messageResponse.setMessage("Success");

  }

  @Test
  void addAddressTest() throws Exception {
    when(addressService.addAddress(any(AddressInDto.class))).thenReturn(messageResponse);

    mockMvc.perform(post("/address")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(addressInDto)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Success"));
  }

  @Test
  void updateAddressTests() throws Exception {
    int addressId = 1;
    when(addressService.updateAddress(eq(1), any(AddressInDto.class))).thenReturn(messageResponse);

    mockMvc.perform(put("/address/update/" + addressId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(addressInDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Success"));
  }

  @Test
  void deleteAddressTests() throws Exception {
    int addressId = 1;
    when(addressService.deleteAdderess(1)).thenReturn(messageResponse);

    mockMvc.perform(delete("/address/delete/" + addressId))
      .andExpect(status().isNoContent());
  }

  @Test
  void getAllAddressesTests() throws Exception {
    when(addressService.getAllAddress()).thenReturn(Collections.singletonList(addressResponse));

    mockMvc.perform(get("/address"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].userId").value(1))
      .andExpect(jsonPath("$[0].street").value("street"))
      .andExpect(jsonPath("$[0].city").value("city"))
      .andExpect(jsonPath("$[0].pincode").value(123456));
  }

  @Test
  void findAddressesByUserId() throws Exception {
    int userId = 1;
    when(addressService.getAddressByUserId(1)).thenReturn(Collections.singletonList(addressResponse));

    mockMvc.perform(get("/address/user/" + userId))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].userId").value(1))
      .andExpect(jsonPath("$[0].street").value("street"))
      .andExpect(jsonPath("$[0].city").value("city"))
      .andExpect(jsonPath("$[0].pincode").value(123456));
  }
}
