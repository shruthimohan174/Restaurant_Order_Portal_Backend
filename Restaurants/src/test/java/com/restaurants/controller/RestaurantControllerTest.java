package com.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.dto.RestaurantInDto;
import com.restaurants.dto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link RestaurantController}.
 * This class tests the endpoints of the {@link RestaurantController} class, ensuring
 * correct functionality and handling of various scenarios
 * related to restaurant operations such as adding, updating, and retrieving
 * restaurant details, as well as handling restaurant images.
 */
@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

  /**
   * Mock for the RestaurantService used to interact with restaurant-related services.
   */
  @Mock
  private RestaurantService restaurantService;

  /**
   * Mock for handling multipart file uploads representing restaurant images.
   */
  @Mock
  private MultipartFile image;

  /**
   * Instance of {@link RestaurantController} to be tested.
   */
  @InjectMocks
  private RestaurantController restaurantController;

  /**
   * Data Transfer Object for restaurant input.
   */
  private RestaurantInDto restaurantInDto;

  /**
   * Data Transfer Object for restaurant output.
   */
  private RestaurantOutDto restaurantOutDto;

  /**
   * Data Transfer Object for message output.
   */
  private MessageOutDto messageOutDto;

  /**
   * Sets up the test environment by initializing the necessary objects and mocks.
   * This method is executed before each test method to ensure a clean state.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    restaurantInDto = new RestaurantInDto();
    restaurantInDto.setUserId(123);
    restaurantInDto.setRestaurantName("Test Restaurant");
    restaurantInDto.setAddress("123 Test Street");
    restaurantInDto.setContactNumber("9876543210");
    restaurantInDto.setOpeningHours("9 AM - 9 PM");

    restaurantOutDto = new RestaurantOutDto();
    restaurantOutDto.setId(1);
    restaurantOutDto.setUserId(123);
    restaurantOutDto.setRestaurantName("Test Restaurant");
    restaurantOutDto.setAddress("123 Test Street");
    restaurantOutDto.setContactNumber("9876543210");
    restaurantOutDto.setOpeningHours("9 AM - 9 PM");
    messageOutDto = new MessageOutDto();

  }

  @Test
  void testAddRestaurant() {
    messageOutDto.setMessage(RestaurantConstants.RESTAURANT_ADDED_SUCCESSFULLY);
    when(restaurantService.addRestaurant(any(RestaurantInDto.class), any(MultipartFile.class)))
      .thenReturn(messageOutDto);

    ResponseEntity<MessageOutDto> response = restaurantController.addRestaurant(restaurantInDto, image);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(RestaurantConstants.RESTAURANT_ADDED_SUCCESSFULLY, response.getBody().getMessage());
    verify(restaurantService, times(1)).addRestaurant(any(RestaurantInDto.class), any(MultipartFile.class));
  }

  @Test
  void testGetAllRestaurants() {
    List<RestaurantOutDto> restaurantOutDtoList = new ArrayList<>();
    restaurantOutDtoList.add(restaurantOutDto);
    when(restaurantService.getALlRestaurants()).thenReturn(restaurantOutDtoList);

    ResponseEntity<List<RestaurantOutDto>> response = restaurantController.getAllRestaurants();

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(restaurantOutDtoList, response.getBody());
    verify(restaurantService, times(1)).getALlRestaurants();
  }

  @Test
  void testGetRestaurantById() {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1);
    restaurant.setUserId(123);
    restaurant.setRestaurantName("Test Restaurant");
    when(restaurantService.findRestaurantById(1)).thenReturn(restaurant);

    ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(restaurant, response.getBody());
    verify(restaurantService, times(1)).findRestaurantById(1);
  }

  @Test
  void testGetAllRestaurantByUserId() {
    List<RestaurantOutDto> restaurantOutDtoList = new ArrayList<>();
    restaurantOutDtoList.add(restaurantOutDto);
    when(restaurantService.getALlRestaurantsByUserId(123)).thenReturn(restaurantOutDtoList);

    ResponseEntity<List<RestaurantOutDto>> response = restaurantController.getAllRestaurantByUserId(123);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(restaurantOutDtoList, response.getBody());
    verify(restaurantService, times(1)).getALlRestaurantsByUserId(123);
  }

  @Test
  public void testGetFoodItemImage() throws Exception {
    byte[] imageData = new byte[] {1, 2, 3, 4, 5};

    when(restaurantService.getRestaurantImage(1)).thenReturn(imageData);
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders
      .standaloneSetup(restaurantController)
      .build();
    mockMvc.perform(get("/restaurant/1/image"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.IMAGE_JPEG))
      .andExpect(content().bytes(imageData));
  }
}
