package com.orders.service.impl;

import com.orders.constants.OrderConstants;
import com.orders.dto.indto.CartInDto;
import com.orders.dto.outdto.MessageOutDto;
import com.orders.dto.outdto.UserOutDto;
import com.orders.entities.Cart;
import com.orders.exception.CustomerNotFoundException;
import com.orders.repositories.CartRepository;
import com.orders.service.CartService;
import com.orders.exception.CartNotFoundException;
import com.orders.service.UserFeignClient;
import com.orders.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private UserFeignClient userClient;

  @Autowired
  private CartRepository cartRepository;

  @Override
  public MessageOutDto addItemToCart(CartInDto cartInDto) {
    UserOutDto user = userClient.getUserById(cartInDto.getUserId());
    if (user.getUserRole() != UserRole.CUSTOMER) {
      throw new CustomerNotFoundException(OrderConstants.CUSTOMER_NOT_FOUND);
    }
    Optional<Cart> existingCart = cartRepository.findByUserIdAndFoodItemIdAndRestaurantId(
      cartInDto.getUserId(),
      cartInDto.getFoodItemId(),
      cartInDto.getRestaurantId()
    );

    if (existingCart.isPresent()) {
      return updateCartItemQuantity(existingCart.get().getId(), 1);
    } else {
      return addNewCartItem(cartInDto);
    } 
  }

  private MessageOutDto addNewCartItem(CartInDto cartInDto) {
    Cart newCart = new Cart();
    newCart.setUserId(cartInDto.getUserId());
    newCart.setFoodItemId(cartInDto.getFoodItemId());
    newCart.setRestaurantId(cartInDto.getRestaurantId());
    newCart.setQuantity(1);
    newCart.setPrice(cartInDto.getPrice());

    cartRepository.save(newCart);
    return new MessageOutDto(OrderConstants.CART_ADDED_SUCCESSFULLY);

  }

  @Override
  public MessageOutDto updateCartItemQuantity(Integer cartId, int quantityChange) {
    Cart cart = getCartById(cartId);
    BigDecimal unitPrice = cart.getPrice().divide(BigDecimal.valueOf(cart.getQuantity()), BigDecimal.ROUND_HALF_EVEN);

    int newQuantity = Math.max(0, cart.getQuantity() + quantityChange);

    if (newQuantity == 0) {
      cartRepository.deleteById(cartId);
      return new MessageOutDto(OrderConstants.ITEM_REMOVED_SUCCESSFULLY);
    }

    BigDecimal newPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity));

    cart.setQuantity(newQuantity);
    cart.setPrice(newPrice);
    cartRepository.save(cart);
    return new MessageOutDto(OrderConstants.CART_UPDATED_SUCCESSFULLY);  }


  @Override
  public MessageOutDto removeItemFromCart(Integer cartId) {
    Cart cart=getCartById(cartId);
    cartRepository.deleteById(cartId);
    return new MessageOutDto(OrderConstants.ITEM_REMOVED_SUCCESSFULLY);
  }

  @Override
  public MessageOutDto clearCartAfterOrderPlaced(Integer userId, Integer restaurantId) {
    List<Cart> cartItems = cartRepository.findByUserIdAndRestaurantId(userId, restaurantId);
    if (!cartItems.isEmpty()) {
      cartRepository.deleteAll(cartItems);
      return new MessageOutDto(OrderConstants.CART_DELETED_SUCCESSFULLY);
    } else {
      return new MessageOutDto(OrderConstants.CART_ALREADY_EMPTY);
    }
  }

  @Override
  public List<Cart> getCartItemsByUserIdAndRestaurantId(Integer userId, Integer restaurantId) {
    return cartRepository.findByUserIdAndRestaurantId(userId, restaurantId);
  }

  @Override
  public List<Cart> getCartByUserId(Integer userId) {
    return cartRepository.findByUserId(userId);
  }

  @Override
  public Cart getCartById(Integer cartId){
   return cartRepository.findById(cartId)
      .orElseThrow(() -> {
        return new CartNotFoundException(OrderConstants.CART_NOT_FOUND);
      });
  }
}
