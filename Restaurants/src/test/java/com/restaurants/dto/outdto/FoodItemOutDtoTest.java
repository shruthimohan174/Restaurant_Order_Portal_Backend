package com.restaurants.dto.outdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FoodItemOutDtoTest {

  private FoodItemOutDto dto1;
  private FoodItemOutDto dto2;

  @BeforeEach
  void setUp() {
    dto1 = new FoodItemOutDto();
    dto1.setId(1);
    dto1.setCategoryId(1);
    dto1.setRestaurantId(1);
    dto1.setItemName("Pasta");
    dto1.setDescription("Delicious pasta with cheese");
    dto1.setIsVeg(true);
    dto1.setPrice(BigDecimal.valueOf(250.5));
    dto1.setImageData(null);

    dto2 = new FoodItemOutDto();
    dto2.setId(1);
    dto2.setCategoryId(1);
    dto2.setRestaurantId(1);
    dto2.setItemName("Pasta");
    dto2.setDescription("Delicious pasta with cheese");
    dto2.setIsVeg(true);
    dto2.setPrice(BigDecimal.valueOf(250.5));
    dto2.setImageData(null);

  }
  @Test
  void testGettersSetters() {
    assertEquals(1, dto1.getId());
    assertEquals(1, dto1.getCategoryId());
    assertEquals(1, dto1.getRestaurantId());
    assertEquals("Pasta", dto1.getItemName());
    assertEquals("Delicious pasta with cheese", dto1.getDescription());
    assertTrue(dto1.getIsVeg());
    assertEquals(BigDecimal.valueOf(250.5), dto1.getPrice());
  }

  @Test
  void testToString() {
    String expected = "FoodItemOutDto(id=1, categoryId=1, restaurantId=1, itemName=Pasta, description=Delicious pasta with cheese, isVeg=true, price=250.5, imageData=null)";
    assertEquals(expected, dto1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(dto1, dto2);
  }

  @Test
  void testHashCode() {
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }
}
