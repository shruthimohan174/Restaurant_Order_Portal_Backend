package com.orders.dto.outdto;

import com.orders.dto.OrderOutDto;
import com.orders.entities.Cart;
import com.orders.utils.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for OrderOutDto.
 */
public class OrderOutDtoTest {

  @Test
  public void testGettersAndSetters() {
    OrderOutDto dto = new OrderOutDto();

    dto.setId(1);
    dto.setUserId(2);
    dto.setDeliveryAddressId(3);
    dto.setOrderStatus(OrderStatus.PLACED);
    dto.setCartItems(Collections.singletonList(new Cart()));
    dto.setOrderTime(LocalDateTime.now());
    dto.setTotalPrice(BigDecimal.valueOf(150.0));
    dto.setRestaurantId(4);

    assertEquals(1, dto.getId());
    assertEquals(2, dto.getUserId());
    assertEquals(3, dto.getDeliveryAddressId());
    assertEquals(OrderStatus.PLACED, dto.getOrderStatus());
    assertNotNull(dto.getCartItems());
    assertNotNull(dto.getOrderTime());
    assertEquals(BigDecimal.valueOf(150.0), dto.getTotalPrice());
    assertEquals(4, dto.getRestaurantId());
  }

  @Test
  public void testToString() {
    LocalDateTime now = LocalDateTime.now();
    OrderOutDto dto =
      new OrderOutDto(1, 2, 3, OrderStatus.PLACED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(150.0), 4);

    String expected =
      "OrderOutDto(id=1, userId=2, deliveryAddressId=3, orderStatus=PENDING," +
        " cartItems=[Cart(id=null, userId=null, foodItemId=null, quantity=null, " +
        "price=null, restaurantId=null)], orderTime=" +
        now + ", totalPrice=150.0, restaurantId=4)";
    assertEquals(expected, dto.toString());
  }

  @Test
  public void testHashCode() {
    LocalDateTime now = LocalDateTime.now();
    OrderOutDto dto1 =
      new OrderOutDto(1, 2, 3, OrderStatus.PLACED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(150.0), 4);
    OrderOutDto dto2 =
      new OrderOutDto(1, 2, 3, OrderStatus.PLACED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(150.0), 4);

    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  @Test
  public void testEquals() {
    LocalDateTime now = LocalDateTime.now();
    OrderOutDto dto1 =
      new OrderOutDto(1, 2, 3, OrderStatus.PLACED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(150.0), 4);
    OrderOutDto dto2 =
      new OrderOutDto(1, 2, 3, OrderStatus.PLACED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(150.0), 4);
    OrderOutDto dto3 =
      new OrderOutDto(5, 6, 7, OrderStatus.COMPLETED, Collections.singletonList(new Cart()), now, BigDecimal.valueOf(200.0), 8);

    assertEquals(dto1, dto2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1, null);
    assertNotEquals(dto1, new Object());
  }
}
