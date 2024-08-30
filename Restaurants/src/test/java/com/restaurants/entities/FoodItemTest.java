package com.restaurants.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class FoodItemTest {

  private FoodItem item1;
  private FoodItem item2;

  @BeforeEach
  void setUp() {
    item1 = new FoodItem();
    item1.setId(1);
    item1.setCategoryId(1);
    item1.setRestaurantId(1);
    item1.setItemName("Pasta");
    item1.setDescription("Delicious pasta with cheese");
    item1.setIsVeg(true);
    item1.setPrice(BigDecimal.valueOf(250.5));
    item1.setImageData(null);

    item2 = new FoodItem();
    item2.setId(1);
    item2.setCategoryId(1);
    item2.setRestaurantId(1);
    item2.setItemName("Pasta");
    item2.setDescription("Delicious pasta with cheese");
    item2.setIsVeg(true);
    item2.setPrice(BigDecimal.valueOf(250.5));
    item2.setImageData(null);
  }

  @Test
  void testToString() {
    String expected = "FoodItem(id=1, categoryId=1, restaurantId=1, itemName=Pasta, description=Delicious pasta with cheese, isVeg=true, price=250.5, imageData=null)";
    assertEquals(expected, item1.toString());
  }

  @Test
  void testEquals() {
    assertEquals(item1, item2);
  }

  @Test
  void testHashCode() {
    assertEquals(item1.hashCode(), item2.hashCode());
  }

  @Test
  void testGettersSetters() {
    assertEquals(1, item1.getId());
    assertEquals(1, item1.getCategoryId());
    assertEquals(1, item1.getRestaurantId());
    assertEquals("Pasta", item1.getItemName());
    assertEquals("Delicious pasta with cheese", item1.getDescription());
    assertTrue(item1.getIsVeg());
    assertEquals(BigDecimal.valueOf(250.5), item1.getPrice());
    assertEquals(null, item1.getImageData());
  }
}
