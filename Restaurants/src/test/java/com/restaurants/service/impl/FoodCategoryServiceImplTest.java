package com.restaurants.service.impl;

import com.restaurants.dto.FoodCategoryInDto;
import com.restaurants.dto.FoodCategoryOutDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.ResourceConflictException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.repositories.FoodCategoryRepository;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
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
 * Unit tests for the {@link FoodCategoryServiceImpl} class.
 * This class tests the methods and functionality of the {@link FoodCategoryServiceImpl} service class,
 * including its interaction with the {@link FoodCategoryRepository} and {@link RestaurantService}.
 */
@ExtendWith(MockitoExtension.class)
class FoodCategoryServiceImplTest {

  /**
   * Mocked instance of {@link FoodCategoryRepository}.
   * Used to simulate interactions with the food category data source.
   */
  @Mock
  private FoodCategoryRepository foodCategoryRepository;

  /**
   * Mocked instance of {@link RestaurantService}.
   * Used to simulate interactions with restaurant-related operations.
   */
  @Mock
  private RestaurantService restaurantService;

  /**
   * Instance of {@link FoodCategoryServiceImpl} under test.
   * This service class will be tested for its methods and functionality.
   */
  @InjectMocks
  private FoodCategoryServiceImpl foodCategoryService;

  /**
   * Data transfer object (DTO) for inputting food category data.
   */
  private FoodCategoryInDto categoryRequest;

  /**
   * Mocked instance of {@link Restaurant}.
   * Represents a restaurant used in the tests to validate interactions.
   */
  private Restaurant restaurant;

  /**
   * Mocked instance of {@link FoodCategory}.
   * Represents a food category used in the tests to validate interactions.
   */
  private FoodCategory foodCategory;

  /**
   * Data transfer object (DTO) for outputting food category data.
   */
  private FoodCategoryOutDto categoryResponse;

  /**
   * Data transfer object (DTO) for messages related to operations.
   */
  private MessageOutDto messageOutDto;

  /**
   * Sets up the test environment by initializing the objects required for the tests.
   * This method is called before each test to ensure a fresh state.
   */
  @BeforeEach
  void setUp() {
    categoryRequest = new FoodCategoryInDto();
    categoryRequest.setCategoryName("category");
    categoryRequest.setRestaurantId(1);

    restaurant = new Restaurant();
    restaurant.setId(1);

    foodCategory = new FoodCategory();
    foodCategory.setId(1);
    foodCategory.setCategoryName("category");
    foodCategory.setRestaurantId(1);

    categoryResponse = new FoodCategoryOutDto();
    categoryResponse.setCategoryName("category");

  }

  @Test
  void addCategoryTest() {
    when(restaurantService.findRestaurantById(1)).thenReturn(restaurant);
    when(foodCategoryRepository.save(any(FoodCategory.class))).thenReturn(foodCategory);

    MessageOutDto result = foodCategoryService.addCategory(categoryRequest);

    assertNotNull(result);
    verify(restaurantService, times(1)).findRestaurantById(1);
    verify(foodCategoryRepository, times(1)).save(any(FoodCategory.class));
  }

  @Test
  void updateCategoryTest() {
    FoodCategoryInDto updateRequest = new FoodCategoryInDto();
    updateRequest.setCategoryName("Updated Category");
    updateRequest.setRestaurantId(1);

    when(foodCategoryRepository.findById(1)).thenReturn(Optional.of(foodCategory));
    when(foodCategoryRepository.save(any(FoodCategory.class))).thenReturn(foodCategory);

    MessageOutDto result = foodCategoryService.updateCategory(updateRequest, 1);

    assertNotNull(result);
    verify(foodCategoryRepository, times(1)).findById(1);
    verify(foodCategoryRepository, times(1)).save(foodCategory);
  }

  @Test
  void addCategoryCategoryAlreadyExistsTest() {
    when(restaurantService.findRestaurantById(1)).thenReturn(restaurant);
    when(foodCategoryRepository.existsByRestaurantIdAndCategoryNameIgnoreCase(1, "category")).thenReturn(true);

    assertThrows(ResourceConflictException.class, () -> foodCategoryService.addCategory(categoryRequest));
    verify(restaurantService, times(1)).findRestaurantById(1);
  }

  @Test
  void findCategoryByIdNotFoundTest() {
    when(foodCategoryRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodCategoryService.findCategoryById(1));
    verify(foodCategoryRepository, times(1)).findById(1);
  }

  @Test
  void viewAllCategoryTest() {
    FoodCategory category1 = new FoodCategory();
    category1.setId(1);
    category1.setCategoryName("category");

    FoodCategory category2 = new FoodCategory();
    category2.setId(2);
    category2.setCategoryName("Desserts");

    when(foodCategoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

    List<FoodCategoryOutDto> result = foodCategoryService.viewAllCategory();

    assertEquals(2, result.size());
    verify(foodCategoryRepository, times(1)).findAll();
  }

  @Test
  void findCategoryByRestaurantIdTest() {
    FoodCategory category1 = new FoodCategory();
    category1.setId(1);
    category1.setCategoryName("category");
    category1.setRestaurantId(1);

    when(foodCategoryRepository.findByRestaurantId(1)).thenReturn(Collections.singletonList(category1));

    List<FoodCategoryOutDto> result = foodCategoryService.findCategoryByRestaurantId(1);

    assertEquals(1, result.size());
    verify(foodCategoryRepository, times(1)).findByRestaurantId(1);
  }
}
