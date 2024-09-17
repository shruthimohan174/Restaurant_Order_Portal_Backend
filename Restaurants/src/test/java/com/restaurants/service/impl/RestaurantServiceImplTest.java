package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.dto.RestaurantInDto;
import com.restaurants.dto.RestaurantOutDto;
import com.restaurants.dto.UserOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.AccessDeniedException;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
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

/**
 * Unit test class for {@link RestaurantServiceImpl}.
 * This class contains tests for the methods of the {@link RestaurantServiceImpl} class.
 */
public class RestaurantServiceImplTest {

  /**
   * Mocked {@link UserFeignClient} used to simulate interactions with the user service.
   */
  @Mock
  private UserFeignClient userFeignClient;

  /**
   * Mocked {@link RestaurantRepository} used to simulate interactions with the restaurant database.
   */
  @Mock
  private RestaurantRepository restaurantRepository;

  /**
   * Mocked {@link MultipartFile} used to simulate image file uploads.
   */
  @Mock
  private MultipartFile image;

  /**
   * Instance of {@link RestaurantServiceImpl} being tested.
   * This instance is injected with the mocked dependencies.
   */
  @InjectMocks
  private RestaurantServiceImpl restaurantService;

  /**
   * The {@link Restaurant} entity used for testing.
   * This field is initialized with a sample restaurant object for use in test cases.
   */
  private Restaurant restaurant;

  /**
   * The {@link RestaurantInDto} data transfer object used for testing.
   * This field is initialized with a sample input DTO for use in test cases involving restaurant creation or updates.
   */
  private RestaurantInDto restaurantInDto;

  /**
   * The {@link RestaurantOutDto} data transfer object used for testing.
   * This field is initialized with a sample output DTO for use in test cases involving responses from the service.
   */
  private RestaurantOutDto restaurantOutDto;

  /**
   * Sets up test data before each test case.
   * Initializes the instances of {@link Restaurant}, {@link RestaurantInDto}, and {@link RestaurantOutDto} with test data.
   */
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
  void testAddRestaurantNotRestaurantOwnerException() {
    when(userFeignClient.getUserById(123)).thenReturn(new UserOutDto(123, UserRole.CUSTOMER));

    assertThrows(AccessDeniedException.class, () -> restaurantService.addRestaurant(restaurantInDto, image));
  }

  @Test
  void testFindRestaurantById() {
    when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

    Restaurant result = restaurantService.findRestaurantById(1);

    assertNotNull(result);
    assertEquals("Test Restaurant", result.getRestaurantName());
  }

  @Test
  void testFindRestaurantByIdRestaurantNotFoundException() {
    when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantService.findRestaurantById(1));
  }

  @Test
  void testValidateImageFileInvalidFileTypeException() {
    when(image.getContentType()).thenReturn("application/pdf");

    InvalidRequestException exception = assertThrows(
      InvalidRequestException.class,
      () -> restaurantService.validateImageFile(image)
    );

    assertEquals(RestaurantConstants.INVALID_FILE_TYPE, exception.getMessage());
  }

  @Test
  void testGetRestaurantImageSuccess() {
    int foodItemId = 1;
    Restaurant restaurant1 = new Restaurant();
    byte[] imageData = "imageData".getBytes();
    restaurant1.setImageData(imageData);

    when(restaurantRepository.findById(foodItemId)).thenReturn(Optional.of(restaurant1));
    byte[] result = restaurantService.getRestaurantImage(foodItemId);

    assertEquals(imageData, result);
  }

  @Test
  void testGetRestaurantImageRestaurantNotFoundException() {
    int foodItemId = 1;
    when(restaurantRepository.findById(foodItemId)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(
      ResourceNotFoundException.class,
      () -> restaurantService.getRestaurantImage(foodItemId)
    );

    assertEquals(RestaurantConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
  }
}
