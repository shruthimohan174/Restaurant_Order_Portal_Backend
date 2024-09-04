package com.restaurants.dto.indto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodItemUpdateInDtoTest {

  private FoodItemUpdateInDto foodItemUpdateInDto1;
  private FoodItemUpdateInDto foodItemUpdateInDto2;

  @BeforeEach
  void setUp() {
    foodItemUpdateInDto1 = new FoodItemUpdateInDto();
    foodItemUpdateInDto1.setItemName("Pizza");
    foodItemUpdateInDto1.setDescription("Cheese Pizza");
    foodItemUpdateInDto1.setPrice(new BigDecimal("10.00"));

    foodItemUpdateInDto2 = new FoodItemUpdateInDto();
    foodItemUpdateInDto2.setItemName("Pizza");
    foodItemUpdateInDto2.setDescription("Cheese Pizza");
    foodItemUpdateInDto2.setPrice(new BigDecimal("10.00"));
  }

  @Test
  void testGettersSetters() {
    FoodItemUpdateInDto dto = new FoodItemUpdateInDto();
    dto.setItemName("Burger");
    dto.setDescription("Beef Burger");
    dto.setPrice(new BigDecimal("5.00"));

    assertEquals("Burger", dto.getItemName());
    assertEquals("Beef Burger", dto.getDescription());
    assertEquals(new BigDecimal("5.00"), dto.getPrice());
  }

  @Test
  void testToString() {
    String expected = "FoodItemUpdateInDto(itemName=Pizza, description=Cheese Pizza, price=10.00)";
    assertEquals(expected, foodItemUpdateInDto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(foodItemUpdateInDto1, foodItemUpdateInDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(foodItemUpdateInDto1.hashCode(), foodItemUpdateInDto2.hashCode());
  }
}
