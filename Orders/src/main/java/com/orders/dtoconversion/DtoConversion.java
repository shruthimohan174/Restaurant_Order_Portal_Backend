package com.orders.dtoconversion;

import com.orders.dto.CartInDto;
import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * Utility class for converting between DTOs and entity objects.
 */
public final class DtoConversion {

  private static final Logger logger = LoggerFactory.getLogger(DtoConversion.class);

  private DtoConversion() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Converts a CartInDto to a Cart entity.
   *
   * @param cartInDto DTO containing cart item details.
   * @return The Cart entity.
   */
  public static Cart convertCartInDtoToCart(CartInDto cartInDto) {
    Cart cartItem = new Cart();
    cartItem.setFoodItemId(cartInDto.getFoodItemId());
//    cartItem.setQuantity(cartInDto.getQuantity());
    cartItem.setPrice(cartInDto.getPrice());
    cartItem.setRestaurantId(cartInDto.getRestaurantId());
    cartItem.setUserId(cartInDto.getUserId());
    return cartItem;
  }

  /**
   * Converts an OrderInDto to an Order entity.
   *
   * @param orderInDto DTO containing order details.
   * @return The Order entity.
   */
  public static Order convertOrderInDtoToOrder(OrderInDto orderInDto) {
    Order order = new Order();
    order.setUserId(orderInDto.getUserId());
    order.setDeliveryAddressId(orderInDto.getDeliveryAddressId());
    order.setRestaurantId(orderInDto.getRestaurantId());
    return order;
  }

  /**
   * Converts an Order entity to an OrderOutDto.
   *
   * @param order The Order entity.
   * @return The OrderOutDto containing order details.
   */
  public static OrderOutDto convertOrderToOrderOutDto(Order order) {
    OrderOutDto dto = new OrderOutDto();
    dto.setId(order.getId());
    dto.setDeliveryAddressId(order.getDeliveryAddressId());
    dto.setUserId(order.getUserId());
    dto.setRestaurantId(order.getRestaurantId());
    dto.setTotalPrice(order.getTotalPrice());
    dto.setOrderStatus(order.getOrderStatus());
    dto.setOrderTime(order.getOrderTime());
    try {
      dto.setCartItems(order.getCartItemsAsList());
    } catch (Exception e) {
      logger.error("Error converting order to OrderOutDto: {}", e.getMessage(), e);
      dto.setCartItems(Collections.emptyList());
    }    return dto;
  }
}
