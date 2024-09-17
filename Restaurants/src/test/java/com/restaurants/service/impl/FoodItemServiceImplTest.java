package com.restaurants.service.impl;

import com.restaurants.constants.RestaurantConstants;
import com.restaurants.dto.FoodItemInDto;
import com.restaurants.dto.FoodItemOutDto;
import com.restaurants.dto.FoodItemUpdateInDto;
import com.restaurants.dto.MessageOutDto;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.FoodItem;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.repositories.FoodItemRepository;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link FoodItemServiceImpl} class.
 * This class tests the functionality of the FoodItemServiceImpl service,
 * including interaction with mocked dependencies and handling of {@link FoodItem} operations.
 */
@ExtendWith(MockitoExtension.class)
public class FoodItemServiceImplTest {

  /**
   * The service under test.
   * This instance is injected with mocked dependencies using {@link InjectMocks}.
   */
  @InjectMocks
  private FoodItemServiceImpl foodItemService;

  /**
   * Mocked repository for performing CRUD operations on {@link FoodItem} entities.
   * This mock is used to verify interactions with the repository and to provide predefined responses.
   */
  @Mock
  private FoodItemRepository foodItemRepository;

  /**
   * Mocked service for handling operations related to restaurants.
   * This mock is used to verify interactions with the restaurant service and to provide predefined responses.
   */
  @Mock
  private RestaurantService restaurantService;

  /**
   * Mocked service for handling operations related to food categories.
   * This mock is used to verify interactions with the food category service and to provide predefined responses.
   */
  @Mock
  private FoodCategoryService foodCategoryService;

  /**
   * Mocked {@link MultipartFile} for handling file uploads related to food items.
   * This mock is used to verify interactions with file uploads and to provide predefined responses.
   */
  @Mock
  private MultipartFile image;

  /**
   * Data transfer object for input operations related to food items.
   */
  private FoodItemInDto request;

  /**
   * Food item instance used in tests.
   */
  private FoodItem foodItem;

  /**
   * Sets up the test environment by initializing mock objects and test data.
   * This method is called before each test method is executed.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    request = new FoodItemInDto();
    request.setCategoryId(1);
    request.setRestaurantId(1);
    request.setItemName("dummy");
    request.setDescription("dummy description");
    request.setIsVeg(true);
    request.setPrice(BigDecimal.valueOf(9.99));

    foodItem = new FoodItem();
    foodItem.setCategoryId(1);
    foodItem.setRestaurantId(1);
    foodItem.setItemName("dummy");
    foodItem.setDescription("dummy description");
    foodItem.setIsVeg(true);
    foodItem.setPrice(BigDecimal.valueOf(9.99));
  }

  @Test
  void testValidateImageFileInvalidFileTypeException() {
    when(image.getContentType()).thenReturn("application/pdf");

    assertThrows(InvalidRequestException.class, () -> {
      foodItemService.validateImageFile(image);
    });
  }

  @Test
  void testGetFoodItemImageSuccess() {
    int foodItemId = 1;
    FoodItem foodItem = new FoodItem();
    byte[] imageData = "imageData".getBytes();
    foodItem.setImageData(imageData);

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.of(foodItem));
    byte[] result = foodItemService.getFoodItemImage(foodItemId);

    assertEquals(imageData, result);
  }

  @Test
  void testGetFoodItemImageFoodItemNotFoundException() {
    int foodItemId = 1;
    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(
      ResourceNotFoundException.class,
      () -> foodItemService.getFoodItemImage(foodItemId)
    );

    assertEquals(RestaurantConstants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
  }

  @Test
  void testUpdateFoodItems() throws IOException {
    FoodItemUpdateInDto foodItemUpdateInDto = new FoodItemUpdateInDto();
    foodItemUpdateInDto.setItemName("dummy");
    foodItemUpdateInDto.setDescription("dummy description");
    foodItemUpdateInDto.setPrice(BigDecimal.valueOf(9.99));
    foodItemUpdateInDto.setIsVeg(true);

    FoodItem existingFoodItem = new FoodItem();
    existingFoodItem.setId(1);
    existingFoodItem.setItemName("Old dummy");
    existingFoodItem.setDescription("Old Description");
    existingFoodItem.setPrice(BigDecimal.valueOf(8.99));
    existingFoodItem.setRestaurantId(1);

    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());

    when(foodItemRepository.findById(1)).thenReturn(Optional.of(existingFoodItem));
    when(foodItemRepository.save(any(FoodItem.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(foodItemRepository.existsByRestaurantIdAndItemNameIgnoreCase(1, "dummy")).thenReturn(false);

    MessageOutDto response = foodItemService.updateFoodItems(foodItemUpdateInDto, 1, image);

    assertNotNull(response);
    assertEquals(RestaurantConstants.FOOD_ITEM_UPDATED_SUCCESSFULLY, response.getMessage());

    verify(foodItemRepository, times(1)).findById(1);
    verify(foodItemRepository, times(1)).save(any(FoodItem.class));

    assertNotNull(existingFoodItem.getImageData());
  }
  /**
   * Tests the {@link FoodItemServiceImpl#getAllFoodItems()} method.
   * <p>
   * Verifies that the method returns a list of all food items with correct data.
   * </p>
   */
  @Test
  public void testGetAllFoodItems() {
    FoodItem foodItem1 = new FoodItem();
    foodItem1.setId(1);
    foodItem1.setItemName("dummy");

    FoodItem foodItem2 = new FoodItem();
    foodItem2.setId(2);
    foodItem2.setItemName("Burger");

    List<FoodItem> foodItems = Arrays.asList(foodItem1, foodItem2);

    when(foodItemRepository.findAll()).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllFoodItems();

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals("dummy", response.get(0).getItemName());
  }

  /**
   * Tests the {@link FoodItemServiceImpl#findFoodItemsById(Integer)} method when the food item is not found.
   * <p>
   * Verifies that the method throws a {@link ResourceNotFoundException} with the correct message.
   * </p>
   */
  @Test
  public void testFindFoodItemByIdThrowsException() {
    Integer foodItemId = 99;

    when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(
      ResourceNotFoundException.class,
      () -> foodItemService.findFoodItemsById(foodItemId)
    );

    assertEquals(RestaurantConstants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
    verify(foodItemRepository).findById(foodItemId);
  }

  @Test
  void testAddFoodItemsSuccess() throws IOException {
    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());

    when(restaurantService.findRestaurantById(1)).thenReturn(new Restaurant());
    when(foodCategoryService.findCategoryById(1)).thenReturn(new FoodCategory());
    when(foodItemRepository.save(any(FoodItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

    MessageOutDto response = foodItemService.addFoodItems(request, image);

    assertNotNull(response);
    assertEquals(RestaurantConstants.FOOD_ITEM_ADDED_SUCCESSFULLY, response.getMessage());

    verify(foodItemRepository, times(1)).save(any(FoodItem.class));
  }

  /**
   * Tests the {@link FoodItemServiceImpl#(int)} method.
   * <p>
   * Verifies that the method returns a list of food items associated with a given restaurant ID.
   * The test sets up a mock food item with a specific restaurant ID and checks that the returned
   * list contains the expected food item.
   * </p>
   */
  @Test
  public void testGetAllByRestaurantId() {
    FoodItem foodItem = new FoodItem();
    foodItem.setId(1);
    foodItem.setItemName("dummy");

    List<FoodItem> foodItems = Collections.singletonList(foodItem);

    when(foodItemRepository.findByRestaurantId(1)).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllByRestaurantId(1);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("dummy", response.get(0).getItemName());
  }

  /**
   * Tests the {@link FoodItemServiceImpl#(int)} method.
   * <p>
   * Verifies that the method returns a list of food items associated with a given category ID.
   * The test sets up a mock food item with a specific category ID and checks that the returned
   * list contains the expected food item.
   * </p>
   */
  @Test
  public void testGetAllByCategoryId() {
    FoodItem foodItem = new FoodItem();
    foodItem.setId(1);
    foodItem.setItemName("dummy");

    List<FoodItem> foodItems = Collections.singletonList(foodItem);

    when(foodItemRepository.findByCategoryId(1)).thenReturn(foodItems);

    List<FoodItemOutDto> response = foodItemService.getAllByCategoryId(1);

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals("dummy", response.get(0).getItemName());
  }
}

