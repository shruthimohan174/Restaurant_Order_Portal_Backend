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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
  private   FoodItemInDto request;
  private   FoodItemOutDto response;

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
  public void testAddFoodItems() throws Exception {
    MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image data".getBytes());
    when(foodItemService.addFoodItems(any(FoodItemInDto.class), any(MultipartFile.class))).thenReturn(response);

    mockMvc.perform(multipart("/foodItem/add")
        .file(imageFile) // Add the file to the request
        .param("categoryId", "1")
        .param("restaurantId", "1")
        .param("itemName", "Pizza")
        .param("description", "Delicious cheese pizza")
        .param("isVeg", "true")
        .param("price", "9.99")
        .contentType(MediaType.MULTIPART_FORM_DATA))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.itemName").value("Pizza"))
      .andExpect(jsonPath("$.description").value("Delicious cheese pizza"))
      .andExpect(jsonPath("$.isVeg").value(true))
      .andExpect(jsonPath("$.price").value(9.99));
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
    List<FoodItemOutDto> responseList = Arrays.asList(response);

    when(foodItemService.getAllByRestaurantId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/restaurant/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  public void testGetAllFoodItemsByCategoryId() throws Exception {
    List<FoodItemOutDto> responseList = Arrays.asList(response);

    when(foodItemService.getAllByCategoryId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/category/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1));
  }
}
