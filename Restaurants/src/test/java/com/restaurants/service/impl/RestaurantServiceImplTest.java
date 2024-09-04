package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.MessageOutDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.dto.outdto.UserOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.InvalidFileTypeException;
import com.restaurants.exception.NotRestaurantOwnerException;
import com.restaurants.exception.RestaurantNotFoundException;
import com.restaurants.repositories.RestaurantRepository;
import com.restaurants.service.UserFeignClient;
import com.restaurants.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantServiceImplTest {
  @Mock
  private UserFeignClient userFeignClient;

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private MultipartFile image;

  @InjectMocks
  private RestaurantServiceImpl restaurantService;

  private Restaurant restaurant;
  private RestaurantInDto restaurantInDto;
  private RestaurantOutDto restaurantOutDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    restaurant = new Restaurant();
    restaurant.setId(1);
    restaurant.setUserId(123);
    restaurant.setRestaurantName("Test Restaurant");
    restaurant.setAddress("123 Test Street");
    restaurant.setContactNumber("9876543210");
    restaurant.setOpeningHours("9 AM - 9 PM");

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
  void testAddRestaurant() throws IOException {
    when(userFeignClient.getUserById(123)).thenReturn(new UserOutDto(123, UserRole.RESTAURANT_OWNER));
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
    when(image.getBytes()).thenReturn(new byte[] {});
    when(image.getContentType()).thenReturn("image/jpeg");

    MessageOutDto result = restaurantService.addRestaurant(restaurantInDto, image);

    assertNotNull(result);
    assertEquals(RestaurantConstants.RESTAURANT_ADDED_SUCCESSFULLY, result.getMessage());
    verify(restaurantRepository, times(1)).save(any(Restaurant.class));
  }

  @Test
  void testGetAllRestaurants() {
    List<Restaurant> restaurants = new ArrayList<>();
    restaurants.add(restaurant);
    when(restaurantRepository.findAll()).thenReturn(restaurants);

    List<RestaurantOutDto> result = restaurantService.getALlRestaurants();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Test Restaurant", result.get(0).getRestaurantName());
  }

  @Test
  void testGetAllRestaurantsByUserId() {
    List<Restaurant> restaurants = new ArrayList<>();
    restaurants.add(restaurant);
    when(restaurantRepository.findByUserId(123)).thenReturn(restaurants);

    List<RestaurantOutDto> result = restaurantService.getALlRestaurantsByUserId(123);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Test Restaurant", result.get(0).getRestaurantName());
  }

  @Test
  void testAddRestaurant_NotRestaurantOwnerException() {
    when(userFeignClient.getUserById(123)).thenReturn(new UserOutDto(123, UserRole.CUSTOMER));

    assertThrows(NotRestaurantOwnerException.class, () -> restaurantService.addRestaurant(restaurantInDto, image));
  }

  @Test
  void testFindRestaurantById() {
    when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

    Restaurant result = restaurantService.findRestaurantById(1);

    assertNotNull(result);
    assertEquals("Test Restaurant", result.getRestaurantName());
  }

  @Test
  void testFindRestaurantById_RestaurantNotFoundException() {
    when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(RestaurantNotFoundException.class, () -> restaurantService.findRestaurantById(1));
  }

  @Test
  void testValidateImageFile_InvalidFileTypeException() {
    when(image.getContentType()).thenReturn("application/pdf");

    InvalidFileTypeException exception = assertThrows(
      InvalidFileTypeException.class,
      () -> restaurantService.validateImageFile(image)
    );

    assertEquals(RestaurantConstants.INVALID_FILE_TYPE, exception.getMessage());
  }

  @Test
  void testGetRestaurantImage_Success() {
    int foodItemId = 1;
    Restaurant restaurant1 = new Restaurant();
    byte[] imageData = "imageData".getBytes();
    restaurant1.setImageData(imageData);

    when(restaurantRepository.findById(foodItemId)).thenReturn(Optional.of(restaurant1));
    byte[] result = restaurantService.getRestaurantImage(foodItemId);

    assertEquals(imageData, result);
  }

  @Test
  void testGetRestaurantImage_RestaurantNotFoundException() {
    int foodItemId = 1;
    when(restaurantRepository.findById(foodItemId)).thenReturn(Optional.empty());

    RestaurantNotFoundException exception = assertThrows(
      RestaurantNotFoundException.class,
      () -> restaurantService.getRestaurantImage(foodItemId)
    );

    assertEquals(RestaurantConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
  }
}
