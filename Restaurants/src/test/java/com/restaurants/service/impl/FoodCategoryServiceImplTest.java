package com.restaurants.service.impl;

import com.restaurants.dto.indto.FoodCategoryInDto;
import com.restaurants.dto.outdto.FoodCategoryOutDto;
import com.restaurants.dto.outdto.MessageOutDto;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.CategoryAlreadyExistsException;
import com.restaurants.exception.CategoryNotFoundException;
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

@ExtendWith(MockitoExtension.class)
class FoodCategoryServiceImplTest {

  @Mock
  private FoodCategoryRepository foodCategoryRepository;

  @Mock
  private RestaurantService restaurantService;

  @InjectMocks
  private FoodCategoryServiceImpl foodCategoryService;

  private FoodCategoryInDto categoryRequest;
  private Restaurant restaurant;
  private FoodCategory foodCategory;
  private FoodCategoryOutDto categoryResponse;
  private MessageOutDto messageOutDto;

  @BeforeEach
  void setUp() {
    categoryRequest = new FoodCategoryInDto();
    categoryRequest.setCategoryName("Fast Food");
    categoryRequest.setRestaurantId(1);

    restaurant = new Restaurant();
    restaurant.setId(1);

    foodCategory = new FoodCategory();
    foodCategory.setId(1);
    foodCategory.setCategoryName("Fast Food");
    foodCategory.setRestaurantId(1);

    categoryResponse = new FoodCategoryOutDto();
    categoryResponse.setCategoryName("Fast Food");

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
  void addCategory_CategoryAlreadyExistsTest() {
    when(restaurantService.findRestaurantById(1)).thenReturn(restaurant);
    when(foodCategoryRepository.existsByRestaurantIdAndCategoryNameIgnoreCase(1, "Fast Food")).thenReturn(true);

    assertThrows(CategoryAlreadyExistsException.class, () -> foodCategoryService.addCategory(categoryRequest));
    verify(restaurantService, times(1)).findRestaurantById(1);
  }

  @Test
  void findCategoryByIdNotFoundTest() {
    when(foodCategoryRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(CategoryNotFoundException.class, () -> foodCategoryService.findCategoryById(1));
    verify(foodCategoryRepository, times(1)).findById(1);
  }

  @Test
  void viewAllCategoryTest() {
    FoodCategory category1 = new FoodCategory();
    category1.setId(1);
    category1.setCategoryName("Fast Food");

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
    category1.setCategoryName("Fast Food");
    category1.setRestaurantId(1);

    when(foodCategoryRepository.findByRestaurantId(1)).thenReturn(Collections.singletonList(category1));

    List<FoodCategoryOutDto> result = foodCategoryService.findCategoryByRestaurantId(1);

    assertEquals(1, result.size());
    verify(foodCategoryRepository, times(1)).findByRestaurantId(1);
  }
}
