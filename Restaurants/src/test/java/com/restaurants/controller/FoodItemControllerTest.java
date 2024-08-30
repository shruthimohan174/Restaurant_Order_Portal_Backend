package com.restaurants.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FoodItemControllerTest {

  @InjectMocks
  private FoodItemController foodItemController;

  @Mock
  private FoodItemService foodItemService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private FoodItemInDto request;
  private FoodItemOutDto response;
  @Mock
  private MultipartFile image;

  @Mock
  private MultipartFile multipartFile;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders
      .standaloneSetup(foodItemController)
      .build();

    request = new FoodItemInDto();
    request.setCategoryId(1);
    request.setRestaurantId(1);
    request.setItemName("Pizza");
    request.setDescription("Delicious cheese pizza");
    request.setIsVeg(true);
    request.setPrice(BigDecimal.valueOf(9.99));

    response = new FoodItemOutDto();
    response.setId(1);
    response.setCategoryId(1);
    response.setRestaurantId(1);
    response.setItemName("Pizza");
    response.setDescription("Delicious cheese pizza");
    response.setIsVeg(true);
    response.setPrice(BigDecimal.valueOf(9.99));
  }

  @Test
  void testAddFoodItem() {
    when(foodItemService.addFoodItems(any(FoodItemInDto.class), any(MultipartFile.class)))
      .thenReturn(response);

    ResponseEntity<FoodItemOutDto> responseEntity = foodItemController.addFoodItems(request, image);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());

    verify(foodItemService, times(1)).addFoodItems(any(FoodItemInDto.class), any(MultipartFile.class));
  }



  @Test
  public void testUpdateFoodItem() throws Exception {

    when(foodItemService.updateFoodItems(any(FoodItemInDto.class), any(Integer.class))).thenReturn(response);

    mockMvc.perform(put("/foodItem/update/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.itemName").value("Pizza"));
  }

  @Test
  public void testGetAllFoodItems() throws Exception {

    FoodItemOutDto response2 = new FoodItemOutDto();
    response2.setId(2);
    response2.setItemName("Burger");

    List<FoodItemOutDto> responseList = Arrays.asList(response, response2);

    when(foodItemService.getAllFoodItems()).thenReturn(responseList);

    mockMvc.perform(get("/foodItem"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  public void testGetAllFoodItemsByRestaurantId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByRestaurantId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/restaurant/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  public void testGetAllFoodItemsByCategoryId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByCategoryId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/category/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1));
  }
}
