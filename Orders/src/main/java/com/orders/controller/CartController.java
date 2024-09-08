package com.orders.controller;

import com.orders.dto.indto.CartInDto;
import com.orders.dto.outdto.MessageOutDto;
import com.orders.entities.Cart;
import com.orders.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping("/add")
  public ResponseEntity<MessageOutDto> addItemToCart(@RequestBody CartInDto cartInDto) {
    MessageOutDto response = cartService.addItemToCart(cartInDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{cartId}")
  public ResponseEntity<MessageOutDto> updateItemQuantity(  @PathVariable Integer cartId, @RequestParam int quantityChange) {
    MessageOutDto response = cartService.updateCartItemQuantity(cartId, quantityChange);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/remove/{cartId}")
  public ResponseEntity<MessageOutDto> removeItemFromCart(@PathVariable Integer cartId) {
    MessageOutDto response = cartService.removeItemFromCart(cartId);
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/clear/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<MessageOutDto> clearCartAfterOrderPlaced(@PathVariable Integer userId,@PathVariable Integer restaurantId) {
    MessageOutDto response = cartService.clearCartAfterOrderPlaced(userId,restaurantId);
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable Integer userId) {
    List<Cart> cartItems = cartService.getCartByUserId(userId);
    return new ResponseEntity<>(cartItems,HttpStatus.OK);
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<Cart> getCartById(@PathVariable Integer cartId) {
    Cart cart = cartService.getCartById(cartId);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/restaurant/{restaurantId}")
  public ResponseEntity<List<Cart>> getCartByUserIdAndRestaurantId(@PathVariable Integer userId, @PathVariable Integer restaurantId) {
    List<Cart> cartItems = cartService.getCartItemsByUserIdAndRestaurantId(userId,restaurantId);
    return new ResponseEntity<>(cartItems,HttpStatus.OK);
  }

}
