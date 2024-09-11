package com.orders.dto.outdto;

import com.orders.dto.FoodItemOutDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FoodItemOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    FoodItemOutDto dto = new FoodItemOutDto();
    dto.setId(1);
    dto.setRestaurantId(2);
    dto.setItemName("Pizza");
    dto.setPrice(new BigDecimal("12.99"));

    assertEquals(1, dto.getId());
    assertEquals(2, dto.getRestaurantId());
    assertEquals("Pizza", dto.getItemName());
    assertEquals(new BigDecimal("12.99"), dto.getPrice());
  }

  @Test
  public void testToString() {
    FoodItemOutDto dto = new FoodItemOutDto(1, 2, "Pizza", new BigDecimal("12.99"));
    String expected = "FoodItemOutDto(id=1, restaurantId=2, itemName=Pizza, price=12.99)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    FoodItemOutDto dto1 = new FoodItemOutDto(1, 2, "Pizza", new BigDecimal("12.99"));
    FoodItemOutDto dto2 = new FoodItemOutDto(1, 2, "Pizza", new BigDecimal("12.99"));
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    FoodItemOutDto dto1 = new FoodItemOutDto(1, 2, "Pizza", new BigDecimal("12.99"));
    FoodItemOutDto dto2 = new FoodItemOutDto(1, 2, "Pizza", new BigDecimal("12.99"));
    FoodItemOutDto dto3 = new FoodItemOutDto(3, 4, "Burger", new BigDecimal("8.99"));

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
