package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodCategoryTest {

  private FoodCategory entity1;
  private FoodCategory entity2;

  @BeforeEach
  void setUp() {
    entity1 = new FoodCategory();
    entity1.setId(1);
    entity1.setRestaurantId(1);
    entity1.setCategoryName("Appetizers");

    entity2 = new FoodCategory();
    entity2.setId(1);
    entity2.setRestaurantId(1);
    entity2.setCategoryName("Appetizers");
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, entity1.getId());
    assertEquals(1, entity1.getRestaurantId());
    assertEquals("Appetizers", entity1.getCategoryName());
  }

  @Test
  void testToString() {
    String expected = "FoodCategory(id=1, restaurantId=1, categoryName=Appetizers)";
    assertEquals(expected, entity1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(entity1, entity2);
  }

  @Test
  void testHashCode() {
    assertEquals(entity1.hashCode(), entity2.hashCode());
  }
}
