package com.orders.service;

import com.orders.dto.indto.CartInDto;
import com.orders.dto.outdto.MessageOutDto;
import com.orders.entities.Cart;

import java.util.List;

public interface CartService {

  MessageOutDto addItemToCart(CartInDto cartInDto);

   MessageOutDto updateCartItemQuantity(Integer cartId, int quantityChange);

  MessageOutDto removeItemFromCart(Integer cartId);

   MessageOutDto clearCartAfterOrderPlaced(Integer userId, Integer restaurantId);

  List<Cart> getCartByUserId(Integer userId);

  List<Cart> getCartItemsByUserIdAndRestaurantId(Integer userId, Integer restaurantId);

  Cart getCartById(Integer userId);

}
