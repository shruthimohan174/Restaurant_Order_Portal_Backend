package com.orders.dto.indto;

import com.orders.dto.CartInDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CartInDtoTest {

  @Test
  public void testGettersAndSetters() {
    CartInDto dto = new CartInDto();

    dto.setUserId(1);
    dto.setFoodItemId(2);
    dto.setPrice(new BigDecimal("10.50"));
    dto.setRestaurantId(3);

    assertEquals(1, dto.getUserId());
    assertEquals(2, dto.getFoodItemId());
    assertEquals(new BigDecimal("10.50"), dto.getPrice());
    assertEquals(3, dto.getRestaurantId());
  }

  @Test
  public void testToString() {
    CartInDto dto = new CartInDto(1, 2, new BigDecimal("10.50"), 3);

    String expected = "CartInDto(userId=1, foodItemId=2, price=10.50, restaurantId=3)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    CartInDto dto1 = new CartInDto(1, 2, new BigDecimal("10.50"), 3);
    CartInDto dto2 = new CartInDto(1, 2, new BigDecimal("10.50"), 3);

    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    CartInDto dto1 = new CartInDto(1, 2, new BigDecimal("10.50"), 3);
    CartInDto dto2 = new CartInDto(1, 2, new BigDecimal("10.50"), 3);
    CartInDto dto3 = new CartInDto(4, 5, new BigDecimal("20.00"), 6);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
