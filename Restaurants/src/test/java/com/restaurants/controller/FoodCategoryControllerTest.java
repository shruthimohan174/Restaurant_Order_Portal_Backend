package com.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.dto.FoodCategoryInDto;
import com.restaurants.dto.FoodCategoryOutDto;
import com.restaurants.dto.MessageOutDto;
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

/**
 * Unit tests for {@link FoodCategoryController}.
 * This class tests the endpoints of the {@link FoodCategoryController} class, ensuring correct functionality
 * and handling of various scenarios
 * related to food category operations such as adding, updating, retrieving, and deleting food categories.
 */
@ExtendWith(MockitoExtension.class)
public class FoodCategoryControllerTest {

  /**
   * Instance of {@link FoodCategoryController} to be tested.
   */
  @InjectMocks
  private FoodCategoryController foodCategoryController;

  /**
   * Mock for the {@link FoodCategoryService} used to interact with food category-related services.
   */
  @Mock
  private FoodCategoryService foodCategoryService;

  /**
   * MockMvc instance for performing HTTP requests and verifying responses.
   */
  private MockMvc mockMvc;

  /**
   * ObjectMapper instance for serializing and deserializing JSON data.
   */
  private ObjectMapper objectMapper;

  /**
   * Data Transfer Object for food category input.
   */
  private FoodCategoryInDto foodCategoryInDto;

  /**
   * Data Transfer Object for food category output.
   */
  private FoodCategoryOutDto foodCategoryOutDto;

  /**
   * Data Transfer Object for message output.
   */
  private MessageOutDto messageOutDto;

  /**
   * Sets up the test environment by initializing the {@link MockMvc} and {@link ObjectMapper} instances.
   * This method is executed before each test method to ensure a clean state.
   */
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(foodCategoryController).build();
    objectMapper = new ObjectMapper();

    foodCategoryInDto = new FoodCategoryInDto();
    foodCategoryInDto.setRestaurantId(1);
    foodCategoryInDto.setCategoryName("category");

    foodCategoryOutDto = new FoodCategoryOutDto();
    foodCategoryOutDto.setId(1);
    foodCategoryOutDto.setRestaurantId(1);
    foodCategoryOutDto.setCategoryName("category");

    messageOutDto = new MessageOutDto();
  }

  @Test
  void addFoodCategoryTest() throws Exception {
    messageOutDto.setMessage("Food category added successfully");

    when(foodCategoryService.addCategory(any(FoodCategoryInDto.class)))
      .thenReturn(messageOutDto);

    mockMvc.perform(post("/category")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(foodCategoryInDto)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Food category added successfully"));
  }

  @Test
  void updateFoodCategoryTest() throws Exception {
    int categoryId = 1;
    messageOutDto.setMessage("Food category updated successfully");
    when(foodCategoryService.updateCategory(any(FoodCategoryInDto.class), anyInt()))
      .thenReturn(messageOutDto);

    mockMvc.perform(put("/category/update/" + categoryId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(foodCategoryInDto)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Food category updated successfully"));
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
      .andExpect(jsonPath("$[0].categoryName").value("category"));
  }

  @Test
  void getAllCategoryByRestaurantIdTest() throws Exception {
    int restaurantId = 1;
    List<FoodCategoryOutDto> categories = Collections.singletonList(foodCategoryOutDto);

    when(foodCategoryService.findCategoryByRestaurantId(anyInt())).thenReturn(categories);

    mockMvc.perform(get("/category/restaurant/" + restaurantId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].restaurantId").value(1))
      .andExpect(jsonPath("$[0].categoryName").value("category"));
  }
}
