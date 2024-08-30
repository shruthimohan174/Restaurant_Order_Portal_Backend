package com.restaurants.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodCategoryInDtoTest {

  private FoodCategoryInDto foodCategoryInDto1;
  private FoodCategoryInDto foodCategoryInDto2;

  @BeforeEach
  void setUp() {
    foodCategoryInDto1 = new FoodCategoryInDto();
    foodCategoryInDto1.setRestaurantId(1);
    foodCategoryInDto1.setCategoryName("Appetizers");

    foodCategoryInDto2 = new FoodCategoryInDto();
    foodCategoryInDto2.setRestaurantId(1);
    foodCategoryInDto2.setCategoryName("Appetizers");
  }
  @Test
  void testGettersSetters() {
    FoodCategoryInDto dto = new FoodCategoryInDto();
    dto.setRestaurantId(1);
    dto.setCategoryName("Beverages");

    assertEquals(1, dto.getRestaurantId());
    assertEquals("Beverages", dto.getCategoryName());
  }
  @Test
  void testToString() {
    String expected = "FoodCategoryInDto(restaurantId=1, categoryName=Appetizers)";
    assertEquals(expected, foodCategoryInDto1.toString());
  }

  @Test
  void testEquals() {

    assertEquals(foodCategoryInDto1, foodCategoryInDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(foodCategoryInDto1.hashCode(), foodCategoryInDto2.hashCode());
  }
}
