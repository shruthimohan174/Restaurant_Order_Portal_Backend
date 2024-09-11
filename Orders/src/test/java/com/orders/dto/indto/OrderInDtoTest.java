package com.orders.dto.indto;

import com.orders.dto.OrderInDto;
import com.orders.entities.Cart;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderInDtoTest {

  @Test
  public void testGettersAndSetters() {
    OrderInDto dto = new OrderInDto();

    dto.setUserId(1);
    dto.setDeliveryAddressId(2);
    dto.setRestaurantId(3);
    dto.setCartItems(Collections.singletonList(new Cart()));

    assertEquals(1, dto.getUserId());
    assertEquals(2, dto.getDeliveryAddressId());
    assertEquals(3, dto.getRestaurantId());
    assertNotNull(dto.getCartItems());
  }

  @Test
  public void testToString() {
    OrderInDto dto = new OrderInDto(1, 2, 3, Collections.singletonList(new Cart()));

    String expected =
      "OrderInDto(userId=1, deliveryAddressId=2, restaurantId=3, cartItems=[Cart(id=null, userId=null," +
        " foodItemId=null, quantity=null, price=null, restaurantId=null)])";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    OrderInDto dto1 = new OrderInDto(1, 2, 3, Collections.singletonList(new Cart()));
    OrderInDto dto2 = new OrderInDto(1, 2, 3, Collections.singletonList(new Cart()));

    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    OrderInDto dto1 = new OrderInDto(1, 2, 3, Collections.singletonList(new Cart()));
    OrderInDto dto2 = new OrderInDto(1, 2, 3, Collections.singletonList(new Cart()));
    OrderInDto dto3 = new OrderInDto(4, 5, 6, Collections.singletonList(new Cart()));

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
