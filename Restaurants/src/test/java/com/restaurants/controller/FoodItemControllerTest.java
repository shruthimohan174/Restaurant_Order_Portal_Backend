package com.restaurants.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.indto.FoodItemUpdateInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.dto.outdto.MessageOutDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
  private MessageOutDto messageOutDto;

  @Mock
  private MultipartFile image;

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

    messageOutDto = new MessageOutDto();

  }
  @Test
  void testAddFoodItem() throws Exception {
    messageOutDto.setMessage("Food item added successfully");
    when(foodItemService.addFoodItems(any(FoodItemInDto.class), any(MultipartFile.class)))
      .thenReturn(messageOutDto);

    ResponseEntity<MessageOutDto> responseEntity = foodItemController.addFoodItems(request, image);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Food item added successfully", responseEntity.getBody().getMessage());

    verify(foodItemService, times(1)).addFoodItems(any(FoodItemInDto.class), any(MultipartFile.class));
  }

  @Test
  public void testUpdateFoodItem() throws Exception {
    messageOutDto.setMessage("Food item updated successfully");

    when(foodItemService.updateFoodItems(any(FoodItemUpdateInDto.class), any(Integer.class)))
      .thenReturn(messageOutDto);

    mockMvc.perform(put("/foodItem/update/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isOk())
      .andExpect(content().json(objectMapper.writeValueAsString(messageOutDto)));
  }


  @Test
  public void testGetAllFoodItems() throws Exception {
    FoodItemOutDto response2 = new FoodItemOutDto();
    response2.setId(2);
    response2.setCategoryId(1);
    response2.setRestaurantId(1);
    response2.setItemName("Burger");
    response2.setDescription("Delicious beef burger");
    response2.setIsVeg(false);
    response2.setPrice(BigDecimal.valueOf(5.99));

    List<FoodItemOutDto> responseList = Arrays.asList(response, response2);

    when(foodItemService.getAllFoodItems()).thenReturn(responseList);

    mockMvc.perform(get("/foodItem"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].itemName").value("Pizza"))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[1].itemName").value("Burger"));
  }

  @Test
  public void testGetAllFoodItemsByRestaurantId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByRestaurantId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/restaurant/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].itemName").value("Pizza"));
  }

  @Test
  public void testGetAllFoodItemsByCategoryId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByCategoryId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/category/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].itemName").value("Pizza"));
  }

  @Test
  public void testGetFoodItemImage() throws Exception {
    byte[] imageData = new byte[] {1, 2, 3, 4, 5}; // Example byte array

    when(foodItemService.getFoodItemImage(1)).thenReturn(imageData);

    mockMvc.perform(get("/foodItem/1/image"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.IMAGE_JPEG))
      .andExpect(content().bytes(imageData));
  }
}
