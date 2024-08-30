package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.indto.FoodItemInDto;
import com.restaurants.dto.outdto.FoodItemOutDto;
import com.restaurants.entities.FoodItem;
import com.restaurants.exception.FoodItemNotFoundException;
import com.restaurants.repositories.FoodItemRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodItemServiceImplTest {

  @InjectMocks
  private FoodItemServiceImpl foodItemService;

  @Mock
  private FoodItemRepository foodItemRepository;

  @Mock
  private RestaurantService restaurantService;

  @Mock
  private FoodCategoryService foodCategoryService;
  @Mock
  private MultipartFile image;

  private FoodItemInDto request;
  private FoodItem foodItem;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    request = new FoodItemInDto();
    request.setCategoryId(1);
    request.setRestaurantId(1);
    request.setItemName("Pizza");
    request.setDescription("Delicious cheese pizza");
    request.setIsVeg(true);
    request.setPrice(BigDecimal.valueOf(9.99));

    foodItem = new FoodItem();
    foodItem.setCategoryId(1);
    foodItem.setRestaurantId(1);
    foodItem.setItemName("Pizza");
    foodItem.setDescription("Delicious cheese pizza");
    foodItem.setIsVeg(true);
    foodItem.setPrice(BigDecimal.valueOf(9.99));
  }

  @Test
  public void testAddFoodItems() throws IOException {
    when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);

    FoodItemOutDto result = foodItemService.addFoodItems(request, null);

    assertNotNull(result);
    assertEquals(1, result.getCategoryId());
    assertEquals(1, result.getRestaurantId());
    assertEquals("Pizza", result.getItemName());
    assertEquals("Delicious cheese pizza", result.getDescription());
    assertEquals(true, result.getIsVeg());
    assertEquals(0, BigDecimal.valueOf(9.99).compareTo(result.getPrice()));
    verify(foodItemRepository, times(1)).save(any(FoodItem.class));
  }

  @Test
  public void testUpdateFoodItems() {
    when(foodItemRepository.findById(1)).thenReturn(Optional.of(foodItem));
    when(foodItemRepository.save(any(FoodItem.class))).thenAnswer(invocation -> {
      FoodItem savedFoodItem = invocation.getArgument(0);
      savedFoodItem.setId(1);
      return savedFoodItem;
    });

    FoodItemOutDto response = foodItemService.updateFoodItems(request, 1);

    assertNotNull(response);
    assertEquals(1, response.getId());
    assertEquals("Pizza", response.getItemName());
  }

  @Test
  public void testGetAllFoodItems() {
    FoodItem foodItem1 = new FoodItem();
    foodItem1.setId(1);
    foodItem1.setItemName("Pizza");

    FoodItem foodItem2 = new FoodItem();
    foodItem2.setId(2);
    foodItem2.setItemName("Burger");

    List<FoodItem> foodItems = Arrays.asList(foodItem1, foodItem2);

    when(foodItemRepository.findAll()).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllFoodItems();

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals("Pizza", response.get(0).getItemName());
  }
  @Test
  public void testFindFoodItemByIdThrowsException() {
    Integer foodItemId = 99;

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

    FoodItemNotFoundException exception = Assertions.assertThrows(
      FoodItemNotFoundException.class,
      () -> foodItemService.findFoodItemsById(foodItemId)
    );

    assertEquals(RestaurantConstants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
    verify(foodItemRepository).findById(foodItemId);
  }
  @Test
  public void testGetAllByRestaurantId() {
    FoodItem foodItem = new FoodItem();
    foodItem.setId(1);
    foodItem.setItemName("Pizza");

    List<FoodItem> foodItems = Collections.singletonList(foodItem);

    when(foodItemRepository.findByRestaurantId(1)).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllByRestaurantId(1);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Pizza", response.get(0).getItemName());
  }

  @Test
  public void testGetAllByCategoryId() {
    FoodItem foodItem = new FoodItem();
    foodItem.setId(1);
    foodItem.setItemName("Pizza");

    List<FoodItem> foodItems = Collections.singletonList(foodItem);

    when(foodItemRepository.findByCategoryId(1)).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllByCategoryId(1);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("Pizza", response.get(0).getItemName());
  }
}

