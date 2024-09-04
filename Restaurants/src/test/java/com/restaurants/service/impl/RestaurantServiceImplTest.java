package com.restaurants.service.impl;

import com.restaurants.dto.indto.RestaurantInDto;
import com.restaurants.dto.outdto.RestaurantOutDto;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.RestaurantNotFoundException;
import com.restaurants.repositories.RestaurantRepository;
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
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
    when(image.getBytes()).thenReturn(new byte[] {});

    String result = restaurantService.addRestaurant(restaurantInDto, null);

    assertNotNull(result);
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
}
