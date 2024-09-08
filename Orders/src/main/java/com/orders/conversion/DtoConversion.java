package com.orders.conversion;

import com.orders.dto.indto.CartInDto;
import com.orders.dto.indto.OrderInDto;
import com.orders.dto.outdto.CartOutDto;
import com.orders.dto.outdto.OrderOutDto;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public final class DtoConversion {

  private DtoConversion(){
    throw new UnsupportedOperationException("Utility class");
  }

  public static Cart convertCartInDtoToCart(CartInDto cartInDto){
    Cart cartItem = new Cart();
    cartItem.setFoodItemId(cartInDto.getFoodItemId());
    cartItem.setQuantity(cartInDto.getQuantity());
    cartItem.setPrice(cartInDto.getPrice());
    cartItem.setRestaurantId(cartInDto.getRestaurantId());
    cartItem.setUserId(cartInDto.getUserId());
    return cartItem;
  }

//  public static CartOutDto convertCartToCartOutDto(Cart cart){
//    CartOutDto cartOutDto = new CartOutDto();
//    cartOutDto.setId(cart.getId());
//    cartOutDto.setFoodItemId(cart.getFoodItemId());
//    cartOutDto.setQuantity(cart.getQuantity());
//    cartOutDto.setPrice(cart.getPrice());
//    cartOutDto.setRestaurantId(cart.getRestaurantId());
//    cartOutDto.setUserId(cart.getUserId());
//    return cartOutDto;
//  }

  public static Order convertOrderInDtoToOrder(OrderInDto orderInDto){
    Order order = new Order();
      order.setUserId(orderInDto.getUserId());
      order.setDeliveryAddressId(orderInDto.getDeliveryAddressId());
      order.setRestaurantId(orderInDto.getRestaurantId());
    return order;
  }

  public static OrderOutDto convertOrderToOrderOutDto(Order order){
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
      dto.setCartItems(Collections.emptyList());
    }    return dto;
  }
}
