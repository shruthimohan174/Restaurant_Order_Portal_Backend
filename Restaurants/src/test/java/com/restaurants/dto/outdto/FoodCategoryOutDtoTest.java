package com.restaurants.dto.outdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodCategoryOutDtoTest {

  private FoodCategoryOutDto dto1;
  private FoodCategoryOutDto dto2;

  @BeforeEach
  void setUp() {
    dto1 = new FoodCategoryOutDto();
    dto1.setId(1);
    dto1.setRestaurantId(1);
    dto1.setCategoryName("Appetizers");

    dto2 = new FoodCategoryOutDto();
    dto2.setId(1);
    dto2.setRestaurantId(1);
    dto2.setCategoryName("Appetizers");
  }
  @Test
  void testGettersSetters() {
    assertEquals(1, dto1.getId());
    assertEquals(1, dto1.getRestaurantId());
    assertEquals("Appetizers", dto1.getCategoryName());
  }

  @Test
  void testToString() {
    String expected = "FoodCategoryOutDto(id=1, restaurantId=1, categoryName=Appetizers)";
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
