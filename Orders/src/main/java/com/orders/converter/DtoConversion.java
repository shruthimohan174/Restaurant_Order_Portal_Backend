package com.orders.converter;

import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;
import com.orders.entities.Order;


/**
 * Utility class for converting between DTOs and entity objects.
 */
public final class DtoConversion {


  private DtoConversion() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Converts an OrderInDto to an Order entity.
   *
   * @param orderInDto DTO containing order details.
   * @return The Order entity.
   */
  public static Order convertOrderInDtoToOrder(final OrderInDto orderInDto) {
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
  public static OrderOutDto convertOrderToOrderOutDto(final Order order) {
    OrderOutDto dto = new OrderOutDto();
    dto.setId(order.getId());
    dto.setDeliveryAddressId(order.getDeliveryAddressId());
    dto.setUserId(order.getUserId());
    dto.setRestaurantId(order.getRestaurantId());
    dto.setTotalPrice(order.getTotalPrice());
    dto.setOrderStatus(order.getOrderStatus());
    dto.setOrderTime(order.getOrderTime());
    dto.setCartItems(null);
    return dto;
  }
}
