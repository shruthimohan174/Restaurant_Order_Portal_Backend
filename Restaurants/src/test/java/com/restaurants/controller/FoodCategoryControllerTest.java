package com.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.dto.indto.FoodCategoryInDto;
import com.restaurants.dto.outdto.FoodCategoryOutDto;
import com.restaurants.service.FoodCategoryService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FoodCategoryControllerTest {

  @InjectMocks
  private FoodCategoryController foodCategoryController;

  @Mock
  private FoodCategoryService foodCategoryService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private FoodCategoryInDto foodCategoryInDto;
  private FoodCategoryOutDto foodCategoryOutDto;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(foodCategoryController).build();
    objectMapper = new ObjectMapper();

    foodCategoryInDto = new FoodCategoryInDto();
    foodCategoryInDto.setRestaurantId(1);
    foodCategoryInDto.setCategoryName("Desserts");

    foodCategoryOutDto = new FoodCategoryOutDto();
    foodCategoryOutDto.setId(1);
    foodCategoryOutDto.setRestaurantId(1);
    foodCategoryOutDto.setCategoryName("Desserts");
  }

  @Test
  void addFoodCategoryTest() throws Exception {
    when(foodCategoryService.addCategory(any(FoodCategoryInDto.class))).thenReturn(foodCategoryOutDto);

    mockMvc.perform(post("/category/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(foodCategoryInDto)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.restaurantId").value(1))
      .andExpect(jsonPath("$.categoryName").value("Desserts"));
  }

  @Test
  void updateFoodCategoryTest() throws Exception {
    when(foodCategoryService.updateCategory(any(FoodCategoryInDto.class), anyInt())).thenReturn(foodCategoryOutDto);

    mockMvc.perform(put("/category/update/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(foodCategoryInDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.restaurantId").value(1))
      .andExpect(jsonPath("$.categoryName").value("Desserts"));
  }

  @Test
  void getAllCategoryTest() throws Exception {
    List<FoodCategoryOutDto> categories = Collections.singletonList(foodCategoryOutDto);

    when(foodCategoryService.viewAllCategory()).thenReturn(categories);

    mockMvc.perform(get("/category")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].restaurantId").value(1))
      .andExpect(jsonPath("$[0].categoryName").value("Desserts"));
  }

  @Test
  void getAllCategoryByRestaurantIdTest() throws Exception {
    List<FoodCategoryOutDto> categories = Collections.singletonList(foodCategoryOutDto);

    when(foodCategoryService.findCategoryByRestaurantId(anyInt())).thenReturn(categories);

    mockMvc.perform(get("/category/restaurant/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].restaurantId").value(1))
      .andExpect(jsonPath("$[0].categoryName").value("Desserts"));
  }
}
