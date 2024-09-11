package com.orders.dto.outdto;

import com.orders.dto.CartOutDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CartOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    CartOutDto dto = new CartOutDto();
    dto.setId(1);
    dto.setUserId(2);
    dto.setFoodItemId(3);
    dto.setQuantity(4);
    dto.setPrice(new BigDecimal("10.50"));
    dto.setRestaurantId(5);

    assertEquals(1, dto.getId());
    assertEquals(2, dto.getUserId());
    assertEquals(3, dto.getFoodItemId());
    assertEquals(4, dto.getQuantity());
    assertEquals(new BigDecimal("10.50"), dto.getPrice());
    assertEquals(5, dto.getRestaurantId());
  }

  @Test
  public void testToString() {
    CartOutDto dto = new CartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    String expected = "CartOutDto(id=1, userId=2, foodItemId=3, quantity=4, price=10.50, restaurantId=5)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    CartOutDto dto1 = new CartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    CartOutDto dto2 = new CartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    CartOutDto dto1 = new CartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    CartOutDto dto2 = new CartOutDto(1, 2, 3, 4, new BigDecimal("10.50"), 5);
    CartOutDto dto3 = new CartOutDto(6, 7, 8, 9, new BigDecimal("20.75"), 10);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
