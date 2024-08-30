package com.restaurants.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FoodItemInDtoTest {

  private FoodItemInDto foodItemInDto1;
  private FoodItemInDto foodItemInDto2;

  @BeforeEach
  void setUp() {
    foodItemInDto1 = new FoodItemInDto();
    foodItemInDto1.setCategoryId(1);
    foodItemInDto1.setRestaurantId(1);
    foodItemInDto1.setItemName("Pasta");
    foodItemInDto1.setDescription("Delicious pasta with cheese");
    foodItemInDto1.setIsVeg(true);
    foodItemInDto1.setPrice(BigDecimal.valueOf(250.5));
    foodItemInDto1.setImage(null);

    foodItemInDto2 = new FoodItemInDto();
    foodItemInDto2.setCategoryId(1);
    foodItemInDto2.setRestaurantId(1);
    foodItemInDto2.setItemName("Pasta");
    foodItemInDto2.setDescription("Delicious pasta with cheese");
    foodItemInDto2.setIsVeg(true);
    foodItemInDto2.setPrice(BigDecimal.valueOf(250.5));
    foodItemInDto2.setImage(null);
  }
  @Test
  void testGettersSetters() {
    assertEquals(1, foodItemInDto1.getCategoryId());
    assertEquals(1, foodItemInDto1.getRestaurantId());
    assertEquals("Pasta", foodItemInDto1.getItemName());
    assertEquals("Delicious pasta with cheese", foodItemInDto1.getDescription());
    assertEquals(true, foodItemInDto1.getIsVeg());
    assertEquals(new BigDecimal("250.5"), foodItemInDto1.getPrice());
    assertEquals(null, foodItemInDto1.getImage());
  }
  @Test
  void testToString() {
    String expected = "FoodItemInDto(categoryId=1, restaurantId=1, itemName=Pasta, description=Delicious pasta with cheese, isVeg=true, price=250.5, image=null)";
    assertEquals(expected, foodItemInDto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(foodItemInDto1, foodItemInDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(foodItemInDto1.hashCode(), foodItemInDto2.hashCode());
  }
}
