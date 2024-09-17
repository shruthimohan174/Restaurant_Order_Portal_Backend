package com.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.dto.FoodItemInDto;
import com.restaurants.dto.FoodItemOutDto;
import com.restaurants.dto.FoodItemUpdateInDto;
import com.restaurants.dto.MessageOutDto;
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
import org.springframework.mock.web.MockMultipartFile;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test class for {@link FoodItemController}.
 * <p>
 * This test class uses Mockito for mocking dependencies and
 * Spring's MockMvc for testing web layer components.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class FoodItemControllerTest {

  /**
   * The controller being tested.
   */
  @InjectMocks
  private FoodItemController foodItemController;

  /**
   * Mock of the {@link FoodItemService} used in the controller.
   */
  @Mock
  private FoodItemService foodItemService;

  /**
   * MockMvc instance for performing HTTP requests and verifying responses.
   */
  private MockMvc mockMvc;

  /**
   * ObjectMapper instance for serializing and deserializing JSON.
   */
  private ObjectMapper objectMapper;

  /**
   * Data transfer object for input operations related to food items.
   */
  private FoodItemInDto request;

  /**
   * Data transfer object for output operations related to food items.
   */
  private FoodItemOutDto response;

  /**
   * Data transfer object for messages.
   */
  private MessageOutDto messageOutDto;

  /**
   * Mock of the {@link MultipartFile} used for file uploads in tests.
   */
  @Mock
  private MultipartFile image;

  /**
   * Sets up the testing environment before each test case.
   * <p>
   * Initializes MockMvc and ObjectMapper instances and sets up
   * sample data transfer objects for use in test cases.
   * </p>
   */
  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders
      .standaloneSetup(foodItemController)
      .build();

    request = new FoodItemInDto();
    request.setCategoryId(1);
    request.setRestaurantId(1);
    request.setItemName("dummy");
    request.setDescription("dummy description");
    request.setIsVeg(true);
    request.setPrice(BigDecimal.valueOf(9.99));

    response = new FoodItemOutDto();
    response.setId(1);
    response.setCategoryId(1);
    response.setRestaurantId(1);
    response.setItemName("dummy");
    response.setDescription("dummy description");
    response.setIsVeg(true);
    response.setPrice(BigDecimal.valueOf(9.99));

    messageOutDto = new MessageOutDto();
  }

  /**
   * Tests the {@link FoodItemController#addFoodItems(FoodItemInDto, MultipartFile)} method.
   * <p>
   * Verifies that a food item is added successfully and that the service method is called
   * with the correct parameters.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
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

  /**
   * Tests the {@link FoodItemController#updateFoodItem(FoodItemUpdateInDto, Integer, MultipartFile)} method.
   * <p>
   * Verifies that a food item is updated successfully and that the service method is called
   * with the correct parameters.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
  @Test
  public void testUpdateFoodItem() throws Exception {
    FoodItemUpdateInDto updateRequest = new FoodItemUpdateInDto();

    updateRequest.setItemName("dummy");
    updateRequest.setDescription("dummy description");
    updateRequest.setIsVeg(true);
    updateRequest.setPrice(BigDecimal.valueOf(9.99));
    messageOutDto.setMessage("Food item updated successfully");

    MockMultipartFile image = new MockMultipartFile("image", "image.jpg",
      MediaType.IMAGE_JPEG_VALUE,
      "test image content".getBytes());

    when(foodItemService.updateFoodItems(any(FoodItemUpdateInDto.class), any(Integer.class), any(MultipartFile.class)))
      .thenReturn(messageOutDto);

    ResponseEntity<MessageOutDto> responseEntity = foodItemController.updateFoodItem(updateRequest, 1, image);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Food item updated successfully", responseEntity.getBody().getMessage());

    verify(foodItemService, times(1)).updateFoodItems(any(FoodItemUpdateInDto.class), any(Integer.class),
      any(MultipartFile.class));
  }

  /**
   * Tests the {@link FoodItemController#getAllFoodItems()} method.
   * <p>
   * Verifies that all food items are retrieved successfully and that the response contains
   * the expected data.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
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
      .andExpect(jsonPath("$[0].itemName").value("dummy"))
      .andExpect(jsonPath("$[1].id").value(2))
      .andExpect(jsonPath("$[1].itemName").value("Burger"));
  }

  /**
   * Tests the {@link FoodItemController#getAllFoodItemsByRestaurantId(Integer)} method.
   * <p>
   * Verifies that food items for a specific restaurant are retrieved successfully and that
   * the response contains the expected data.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
  @Test
  public void testGetAllFoodItemsByRestaurantId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByRestaurantId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/restaurant/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].itemName").value("dummy"));
  }

  /**
   * Tests the {@link FoodItemController#getAllFoodItemsByCategoryId(Integer)} method.
   * <p>
   * Verifies that food items for a specific category are retrieved successfully and that
   * the response contains the expected data.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
  @Test
  public void testGetAllFoodItemsByCategoryId() throws Exception {
    List<FoodItemOutDto> responseList = Collections.singletonList(response);

    when(foodItemService.getAllByCategoryId(1)).thenReturn(responseList);

    mockMvc.perform(get("/foodItem/category/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[0].itemName").value("dummy"));
  }

  /**
   * Tests the {@link FoodItemController#getFoodItemImage(Integer)} method.
   * <p>
   * Verifies that the image of a food item is retrieved successfully and that the response
   * contains the expected image data.
   * </p>
   * @throws Exception if an error occurs during the test execution
   */
  @Test
  public void testGetFoodItemImage() throws Exception {
    byte[] imageData = new byte[] {1, 2, 3, 4, 5};

    when(foodItemService.getFoodItemImage(1)).thenReturn(imageData);

    mockMvc.perform(get("/foodItem/1/image"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.IMAGE_JPEG))
      .andExpect(content().bytes(imageData));
  }
}
