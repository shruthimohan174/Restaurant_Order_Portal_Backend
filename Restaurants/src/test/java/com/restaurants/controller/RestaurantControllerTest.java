package com.restaurants.controller;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

  @Mock
  private RestaurantService restaurantService;

  @Mock
  private MultipartFile image;

  @InjectMocks
  private RestaurantController restaurantController;

  private RestaurantInDto restaurantInDto;
  private RestaurantOutDto restaurantOutDto;

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
  }

  @Test
  void testAddRestaurant() {
    when(restaurantService.addRestaurant(any(RestaurantInDto.class), any(MultipartFile.class)))
      .thenReturn(restaurantOutDto);

    ResponseEntity<RestaurantOutDto> response = restaurantController.addRestaurant(restaurantInDto, image);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(restaurantOutDto, response.getBody());
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
}
