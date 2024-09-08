
package com.orders.service.impl;
  import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.constants.OrderConstants;
import com.orders.conversion.DtoConversion;
import com.orders.dto.indto.OrderInDto;
  import com.orders.dto.outdto.AddressOutDto;
  import com.orders.dto.outdto.MessageOutDto;
import com.orders.dto.outdto.OrderOutDto;
  import com.orders.dto.outdto.UserOutDto;
  import com.orders.entities.Cart;
import com.orders.entities.Order;
  import com.orders.exception.AddressNotFoundException;
  import com.orders.exception.CartNotFoundException;
  import com.orders.exception.CustomerNotFoundException;
  import com.orders.repositories.OrderRepository;
  import com.orders.service.AddressFeignClient;
  import com.orders.service.CartService;
import com.orders.service.OrderService;
import com.orders.exception.OrderNotFoundException;
import com.orders.exception.OrderUpdateException;
  import com.orders.service.UserFeignClient;
  import com.orders.utils.OrderStatus;
  import com.orders.utils.UserRole;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
  import java.util.List;
  import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CartService cartService;

  @Autowired
  private UserFeignClient userClient;

  @Autowired
  private AddressFeignClient addressClient;

  @Override
  public MessageOutDto placeOrder(OrderInDto orderInDto) {

    UserOutDto user = userClient.getUserById(orderInDto.getUserId());
    if (user.getUserRole() != UserRole.CUSTOMER) {
      throw new CustomerNotFoundException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
//    List<AddressOutDto> addresses = addressClient.getAddressesByUserId(orderInDto.getUserId());
//    if (addresses.isEmpty()) {
//      throw new AddressNotFoundException(OrderConstants.ADDRESS_NOT_FOUND);
//    }
//    boolean validAddress = addresses.stream()
//      .anyMatch(address -> address.getId().equals(orderInDto.getDeliveryAddressId()));
//    if (!validAddress) {
//      throw new AddressNotFoundException(OrderConstants.ADDRESS_NOT_FOUND);
//    }

    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(orderInDto.getUserId(), orderInDto.getRestaurantId());

    if (cartItems.isEmpty()) {
      throw new CartNotFoundException("No items found in the cart for user " + orderInDto.getUserId() + " and restaurant " + orderInDto.getRestaurantId());
    }

    List<Cart> validCartItems = new ArrayList<>();
    List<Cart> invalidCartItems = new ArrayList<>();

    for (Cart cartItemDto : orderInDto.getCartItems()) {
      boolean itemExists = cartItems.stream()
        .anyMatch(cartItem -> cartItem.getFoodItemId().equals(cartItemDto.getFoodItemId())
          && cartItem.getQuantity().equals(cartItemDto.getQuantity())
          && cartItem.getPrice().compareTo(cartItemDto.getPrice()) == 0);

      if (itemExists) {
        Cart cart = new Cart();
        cart.setUserId(cartItemDto.getUserId());
        cart.setFoodItemId(cartItemDto.getFoodItemId());
        cart.setQuantity(cartItemDto.getQuantity());
        cart.setPrice(cartItemDto.getPrice());
        cart.setRestaurantId(cartItemDto.getRestaurantId());

        validCartItems.add(cart);
      } else {
        invalidCartItems.add(cartItemDto);
      }
    }

    if (!invalidCartItems.isEmpty()) {
      return new MessageOutDto("Some items are not valid in the cart: " + invalidCartItems.stream()
        .map(dto -> String.format("foodItemId=%d, quantity=%d, price=%s", dto.getFoodItemId(), dto.getQuantity(), dto.getPrice()))
        .collect(Collectors.joining(", ")));
    }

    BigDecimal totalPrice = validCartItems.stream()
      .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    userClient.updateWalletBalance(orderInDto.getUserId(), totalPrice.negate());

    Order order = DtoConversion.convertOrderInDtoToOrder(orderInDto);
    order.setRestaurantId(orderInDto.getRestaurantId());
    order.setOrderStatus(OrderStatus.PLACED);
    order.setOrderTime(LocalDateTime.now());
    try {
      order.setCartItemsFromList(validCartItems);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to process cart items", e);
    }

    order.setTotalPrice(totalPrice);
    Order savedOrder = orderRepository.save(order);

    cartService.clearCartAfterOrderPlaced(orderInDto.getUserId(), orderInDto.getRestaurantId());

    return new MessageOutDto(OrderConstants.ORDER_PLACED_SUCCESSFULLY);
  }



  @Override
public MessageOutDto cancelOrder(Integer orderId) {
  Order order = orderRepository.findById(orderId)
    .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));

  if (order.getOrderTime().plusSeconds(30).isBefore(LocalDateTime.now())) {
    throw new OrderUpdateException("Order cannot be cancelled after 30 seconds.");
  }

    userClient.updateWalletBalance(order.getUserId(), order.getTotalPrice());

  order.setOrderStatus(OrderStatus.CANCELLED);
  orderRepository.save(order);
  return new MessageOutDto(OrderConstants.ORDER_CANCELLED_SUCCESSFULLY);
}

@Override
public List<OrderOutDto> getOrdersByUserId(Integer userId) {
  List<Order> orders = orderRepository.findByUserId(userId);
  List<OrderOutDto> orderOutDtoList=new ArrayList<>();
  for(Order order:orders){
    orderOutDtoList.add(DtoConversion.convertOrderToOrderOutDto(order));
  }
  return orderOutDtoList;
}

@Override
public List<OrderOutDto> getOrdersByRestaurantId(Integer restaurantId) {
  List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
  List<OrderOutDto> orderOutDtoList=new ArrayList<>();
  for(Order order:orders){
    orderOutDtoList.add(DtoConversion.convertOrderToOrderOutDto(order));
  }
  return orderOutDtoList;
}

@Override
public void markOrderAsCompleted(Integer orderId) {
  Order order = orderRepository.findById(orderId)
    .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));

  order.setOrderStatus(OrderStatus.COMPLETED);
  orderRepository.save(order);
  MessageOutDto messge= new MessageOutDto(OrderConstants.ORDER_COMPLETED_SUCCESSFULLY);
}

}
