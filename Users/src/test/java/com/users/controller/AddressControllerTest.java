package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.dto.indto.AddressInDto;
import com.users.dto.outdto.AddressOutDto;
import com.users.entities.Address;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

  @InjectMocks
  private AddressController addressController;

  @Mock
  private AddressService addressService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private Address address;
  private AddressInDto addressInDto;
  private AddressOutDto addressResponse;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    objectMapper = new ObjectMapper();

    addressInDto = new AddressInDto();
    addressInDto.setUserId(1);
    addressInDto.setStreet("Plot No 147, Sector 74");
    addressInDto.setCity("Indore");
    addressInDto.setPincode(123456);

    addressResponse = new AddressOutDto();
    addressResponse.setId(1);
    addressResponse.setUserId(1);
    addressResponse.setStreet("Plot No 147, Sector 74");
    addressResponse.setCity("Indore");
    addressResponse.setPincode(123456);

    address = new Address();
    address.setId(1);
    address.setUserId(1);
    address.setStreet("Plot No 147, Sector 74");
    address.setCity("Indore");
    address.setPincode(123456);
  }

  @Test
  void addAddress_ShouldReturnCreated() throws Exception {
    when(addressService.addAddress(any(AddressInDto.class))).thenReturn(addressResponse);

    mockMvc.perform(post("/address/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(addressInDto)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.userId").value(1))
      .andExpect(jsonPath("$.street").value("Plot No 147, Sector 74"))
      .andExpect(jsonPath("$.city").value("Indore"))
      .andExpect(jsonPath("$.pincode").value(123456));
  }

  @Test
  void updateAddressTests() throws Exception {
    when(addressService.updateAddress(eq(1), any(AddressInDto.class))).thenReturn(addressResponse);

    mockMvc.perform(put("/address/update/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(addressInDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.userId").value(1))
      .andExpect(jsonPath("$.street").value("Plot No 147, Sector 74"))
      .andExpect(jsonPath("$.city").value("Indore"))
      .andExpect(jsonPath("$.pincode").value(123456));

  }

  @Test
  void deleteAddressTests() throws Exception {
    doNothing().when(addressService).deleteAdderess(1);

    mockMvc.perform(delete("/address/delete/{id}", 1))
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
      .andExpect(jsonPath("$[0].street").value("Plot No 147, Sector 74"))
      .andExpect(jsonPath("$[0].city").value("Indore"))
      .andExpect(jsonPath("$[0].pincode").value(123456));
  }

  @Test
  void findAddressesByUserIdTests() throws Exception {
    when(addressService.getAddressByUserId(1)).thenReturn(Collections.singletonList(addressResponse));

    mockMvc.perform(get("/address/user/{userId}", 1))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].userId").value(1))
      .andExpect(jsonPath("$[0].street").value("Plot No 147, Sector 74"))
      .andExpect(jsonPath("$[0].city").value("Indore"))
      .andExpect(jsonPath("$[0].pincode").value(123456));
  }
}
